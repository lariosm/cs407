package com.example.tipcalculator

const val HUNDRED_PERCENT = 100.00

class TipCalculator {

    fun expenseCruncher(amountDue : Double, percentTip: Int, numPeople : Int) : Triple<Double, Double, Double> {
        if(amountDue < 0.00 || percentTip < 0 || numPeople < 1) {
            return Triple(0.00,0.00,0.00)
        }
        val amtToTip = amountDue * (percentTip / HUNDRED_PERCENT)
        val totalExpense  = amtToTip + amountDue
        val perPersonTotal = totalExpense / numPeople

        return Triple(amtToTip, totalExpense, perPersonTotal)
    }
}