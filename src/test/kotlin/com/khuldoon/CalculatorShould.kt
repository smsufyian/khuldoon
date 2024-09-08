package com.khuldoon

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test


class CalculatorShould {

    @Test
    fun `add numbers`() {
        val calculator = Calculator(object:Reader {
            override fun read(): List<Int> {
                return listOf(2, 3)
            }
        })
        val result = calculator.add()
        Assertions.assertEquals(5,result)
    }

    @Test
    fun `subtract numbers`() {
        val calculator = Calculator(object:Reader{
            override fun read(): List<Int> {
                return listOf(3, 2)
            }
        })
        val result = calculator.substract()
        Assertions.assertEquals(1, result)
    }

    @Test
    fun `multiply numbers`() {
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
        val calculator = Calculator(object:Reader{
            override fun read(): List<Int> {
                return listOf(20, 5)
            }
        })
        val result: Int = calculator.divide()
        Assertions.assertEquals(4, result)
    }

}



interface Reader {
    fun read():  List<Int>
}


class FileReader : Reader {
    override fun read(): List<Int> {
        //Some code which can read from the file
        // and ond produce output
        return emptyList()
    }
}

class DatabaseReader: Reader {
    override fun read(): List<Int> {
        TODO("Not yet implemented")
    }
}


class InternetReader: Reader {
    override fun read(): List<Int> {
        TODO("Not yet implemented")
    }
}


class Calculator(val reader: Reader) {

    // Learn about List data structure
    fun add() : Int{
        val numbers = reader.read()
        return numbers.sum()
    }

    // Learn about Arrays Datastructure
    fun substract(): Int {
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
    fcalculator.substract()
    fcalculator.multiply()
    fcalculator.divide()


    val databaseReader = DatabaseReader()
    val dcalculator = Calculator(databaseReader)
    dcalculator.add()
    dcalculator.substract()
    dcalculator.multiply()
    dcalculator.divide()

    val IReader= InternetReader()
    val iCalculator = Calculator(IReader)
    iCalculator.divide()
    iCalculator.multiply()
    iCalculator.add()
    iCalculator.substract()





}