package co.yap.widgets.luhn

import java.util.*

data class Card(var cardName: String, var possibleLengths: ArrayList<Int>, var drawable: Int) {
    fun getMaxLength(): Int {
        return possibleLengths[possibleLengths.size - 1]
    }
}