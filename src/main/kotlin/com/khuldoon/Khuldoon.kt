package com.khuldoon

import com.khuldoon.formats.kotlinXMessage
import com.khuldoon.formats.kotlinXMessageLens
import com.khuldoon.routes.ExampleContractRoute
import dev.failsafe.CircuitBreaker as FSCircuitBreaker
import dev.failsafe.Failsafe
import io.micrometer.core.instrument.simple.SimpleMeterRegistry
import java.util.ArrayDeque
import org.http4k.contract.bind
import org.http4k.contract.contract
import org.http4k.contract.openapi.ApiInfo
import org.http4k.contract.openapi.v3.OpenApi3
import org.http4k.contract.security.ApiKeySecurity
import org.http4k.core.HttpHandler
import org.http4k.core.Method.GET
import org.http4k.core.Response
import org.http4k.core.Status.Companion.INTERNAL_SERVER_ERROR
import org.http4k.core.Status.Companion.OK
import org.http4k.core.then
import org.http4k.core.with
import org.http4k.filter.DebuggingFilters.PrintRequest
import org.http4k.filter.MicrometerMetrics
import org.http4k.filter.OpenTelemetryMetrics
import org.http4k.filter.OpenTelemetryTracing
import org.http4k.filter.ServerFilters
import org.http4k.lens.Query
import org.http4k.lens.int
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.Netty
import org.http4k.server.asServer

// this is a micrometer registry used mostly for testing - substitute the correct implementation.
val registry = SimpleMeterRegistry()

val executor = Failsafe.with(
    FSCircuitBreaker.builder<Response>()
        .withFailureThreshold(1)
        .handleResultIf { !it.status.successful }
        .build()
)

val fsCircuitBreakerEndpointResponses = ArrayDeque<Response>().apply {
    add(Response(OK))
    add(Response(OK))
    add(Response(INTERNAL_SERVER_ERROR))
}
val app: HttpHandler = routes(
    "/ping" bind GET to {
        Response(OK).body("pong")
    },

    "/formats/json/kotlinx" bind GET to {
        Response(OK).with(kotlinXMessageLens of kotlinXMessage)
    },

    "/testing/kotest" bind GET to {request ->
        Response(OK).body("Echo '${request.bodyString()}'")
    },

    "/contract/api/v1" bind contract {
        renderer = OpenApi3(ApiInfo("Khuldoon API", "v1.0"))
    
        // Return Swagger API definition under /contract/api/v1/swagger.json
        descriptionPath = "/swagger.json"
    
        // You can use security filter tio protect routes
        security = ApiKeySecurity(Query.int().required("api"), { it == 42 }) // Allow only requests with &api=42
    
        // Add contract routes
        routes += ExampleContractRoute()
    },

    "/metrics" bind GET to {
        Response(OK).body("Example metrics route for Khuldoon")
    },

    "/opentelemetrymetrics" bind GET to {
        Response(OK).body("Example metrics route for Khuldoon")
    },

    "/failsafe" bind GET to {
        fsCircuitBreakerEndpointResponses.pop()
    }
)

fun main() {
    val printingApp: HttpHandler = PrintRequest()
            .then(ServerFilters.MicrometerMetrics.RequestCounter(registry))
            .then(ServerFilters.MicrometerMetrics.RequestTimer(registry))
            .then(ServerFilters.OpenTelemetryTracing())
            .then(ServerFilters.OpenTelemetryMetrics.RequestCounter())
            .then(ServerFilters.OpenTelemetryMetrics.RequestTimer()).then(app)

    val server = printingApp.asServer(Netty(9000)).start()

    println("Server started on " + server.port())
}
