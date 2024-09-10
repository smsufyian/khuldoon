package com.khuldoon

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test


/***
 * You should always think of behaviour when you are writing test.
 * What is the behaviour which I am trying to validate and derive your
 * code from the tests. If you evolve your code through this approach
 * then you will also be getting the feedback about your design at the same
 * time.
 *
 * This is referred to as Test driven development.
 *
 */
class CalculatorShould {

    @Test
    fun `add numbers`() {
        /**
         * The fake implementation of the dependency of the Calculator is
         * passed by implementing the interface in an inline fashion.
         *
         * The other approach could be by creating non-anonymous classes
         * with the fake implementation of the dependencies and passing
         * that to the calculator.
         */
        val calculator = Calculator(object:Reader {
            override fun read(): List<Int> {
                return listOf(2, 3)
            }
        })
        val result = calculator.add()
        Assertions.assertEquals(5,result)
    }

    //TODO
    @Test
    fun `add one positive number`() {
        
    } 

    @Test
    fun `add more then two positive numbers` () {
        
    }

    @Test
    fun `add positive and negative numbers`() {
    
    }

    @Test
    fun `subtract numbers`() {
        /**
         * Look at the comment above for the explanation
         */
        val calculator = Calculator(object:Reader{
            override fun read(): List<Int> {
                return listOf(3, 2)
            }
        })
        val result = calculator.subtract()
        Assertions.assertEquals(1, result)
    }

    @Test
    fun `multiply numbers`() {
        /**
         * Look at the comment above for the explanation
         */
        val calculator: Calculator = Calculator(object:Reader{
            override fun read(): List<Int> {
                return listOf(5, 4)
            }
        })
        val result: Int = calculator.multiply()
        Assertions.assertEquals(20, result)
    }

    @Test
    fun `divide numbers`() {
        /**
         * Look at the comment above for the explanation
         */
        val calculator = Calculator(object:Reader{
            override fun read(): List<Int> {
                return listOf(20, 5)
            }
        })
        val result: Int = calculator.divide()
        Assertions.assertEquals(4, result)
    }


}


/***
 * The interfaces are subject to what is needed. What you want to provide as an abstraction.
 * There are two schools , one is the role interface and the other is Header Interface.
 * The practice are followed by different programming language communities.
 *
 * Read the article below to learn more about Role Interfaces.
 * <href>https://www.martinfowler.com/bliki/RoleInterface.html</href>
 *
 * Read the article below to learn about Header Interfaces.
 * <href>https://www.martinfowler.com/bliki/HeaderInterface.html</href>
 *
 */
interface Reader {
    fun read():  List<Int>
}

/**
 * TODO: Write code to take input from the file
 */
class FileReader : Reader {
    override fun read(): List<Int> {
        //code which can read from the file
        // and ond produce output
        return emptyList()
    }
}

/***
 * TODO: Write code to take input from any Database
 */
class DatabaseReader: Reader {
    override fun read(): List<Int> {
        TODO("Not yet implemented")
    }
}

/***
 * TODO: Write code to take input from the api
 */
class RestAPIReader: Reader {
    override fun read(): List<Int> {
        TODO("Not yet implemented")
    }
}

/***
 * Calculator is not depending on the implementation detail
 * of the Reader but rather an interface which allows the calculator
 * to change irrespective of the changes about where the input data is coming
 * from.
 *
 * Coding to an interface also allows to write testable code. Now you can test
 * calculator by writing fake implementations of the dependencies of the calculator.
 *
 */
class Calculator(val reader: Reader) {

    fun add() : Int{
        val numbers = reader.read()
        return numbers.sum()
    }

    fun subtract(): Int {
        val numbers= reader.read()
        return numbers[0]-numbers[1]
    }

    fun multiply(): Int {
        val numbers = reader.read()
        return numbers[0]*numbers[1]
    }

    fun divide(): Int {
        val numbers =  reader.read()
        return numbers[0]/numbers[1]
    }

}

fun main() {
    val fileReader = FileReader()
    val fcalculator = Calculator(fileReader)
    fcalculator.add()
    fcalculator.subtract()
    fcalculator.multiply()
    fcalculator.divide()


    val databaseReader = DatabaseReader()
    val dcalculator = Calculator(databaseReader)
    dcalculator.add()
    dcalculator.subtract()
    dcalculator.multiply()
    dcalculator.divide()

    val IReader= RestAPIReader()
    val iCalculator = Calculator(IReader)
    iCalculator.divide()
    iCalculator.multiply()
    iCalculator.add()
    iCalculator.subtract()





}
