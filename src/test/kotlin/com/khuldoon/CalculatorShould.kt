package com.khuldoon

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

/*
* Input should be from the file to the Calculator
* Output should be produced to the file from the calculator
*
*
* Tip: How can I test behaviour independently
*      Dependencies
* */


// Fakes vs Stub
// Test driven development


//TODO: Calculator should also produce outout to the file

class FakeFileReaderForAddition : Reader {
    override fun read(): List<Int> {
        return listOf(2,3)
    }
}

class FakeFileReaderForSubstraction : Reader {
    override fun read(): List<Int> {
       return listOf(3,2)
    }
}

class FakeFileReaderForMultipliccation : Reader {
    override fun read(): List<Int> {
        return listOf(5,4)
    }
}

class FakeFileReaderForDivision : Reader {
    override fun read(): List<Int> {
        return listOf(20,5)
    }
}

class FakeDatabaseReaderForAddition : Reader {
    override fun read(): List<Int> {
        TODO("Not yet implemented")
    }
}




class CalculatorShould {

    @Test
    fun `add numbers`() {
        val fakeFileReader = FakeFileReaderForAddition()
        val calculator = Calculator(fakeFileReader)
        val result = calculator.add()
        Assertions.assertEquals(5,result)
    }

    @Test
    fun `subtract numbers`() {
        val fakeFileReader= FakeFileReaderForSubstraction()
        val calculator = Calculator(fakeFileReader)
        val result = calculator.substract()
        Assertions.assertEquals(1, result)
    }

    @Test
    fun `multiply numbers`() {
        val fakeFileReader = FakeFileReaderForMultipliccation()
        val calculator: Calculator = Calculator(fakeFileReader)
        val result: Int = calculator.multiply()
        Assertions.assertEquals(20, result)
    }

    @Test
    fun `divide numbers`() {
        val fakeFileReaderForDivision = FakeFileReaderForDivision()
        val calculator = Calculator(fakeFileReaderForDivision)
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