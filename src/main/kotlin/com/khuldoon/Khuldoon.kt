package com.khuldoon

import org.http4k.core.HttpHandler
import org.http4k.core.Method.GET
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.Netty
import org.http4k.server.asServer


val app: HttpHandler = routes(
    "/ping" bind GET to {
        Response(OK).body("pong")
    },

    "/metrics" bind GET to {
        Response(OK).body("Exposed metrics of khuldoon")
    },

    "/contract/" bind GET to {
        Response(OK).body("Exposed contract of Khuldoon")
    },
)

fun main() {
    val server = app.asServer(Netty(9000)).start()

    println("Server started on " + server.port())
}
