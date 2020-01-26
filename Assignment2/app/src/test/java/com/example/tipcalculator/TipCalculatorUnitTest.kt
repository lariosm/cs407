package com.example.tipcalculator

import org.junit.Test

import org.junit.Assert.*


class TipCalculatorUnitTest {
    @Test
    fun expenseCruncher_CheckThatTripleDoubleIsNotDouble() {
        val calculated = TipCalculator().expenseCruncher(71.89, 5, 9)

        assertNotSame(Double, calculated)
    }

    @Test
    fun expenseCruncher_CheckThatTipAmountIsCorrect() {
        val calculated = TipCalculator().expenseCruncher(54.99, 15, 2)
        val tipAmount = calculated.first

        assertEquals(8.25, tipAmount, 0.01)
    }

    @Test
    fun expenseCruncher_CheckThatTotalDueIsCorrect() {
        val calculated = TipCalculator().expenseCruncher(54.99, 15, 2)
        val totalDue = calculated.second

        assertEquals(63.24, totalDue, 0.01)
    }

    @Test
    fun expenseCruncher_CheckThatTotalPerPersonIsCorrect() {
        val calculated = TipCalculator().expenseCruncher(54.99, 15, 2)
        val totalPerPerson = calculated.third

        assertEquals(31.62, totalPerPerson, 0.01)
    }

    @Test
    fun expenseCruncher_CheckThatInvalidValuesReturnZeros() {
        val (tipAmount, totalDue, totalPerPerson) = TipCalculator().expenseCruncher(-2.00, -4, 0)

        assertEquals(0.00, tipAmount, 0.01)
        assertEquals(0.00, totalDue, 0.01)
        assertEquals(0.00, totalPerPerson, 0.01)

    }
}
