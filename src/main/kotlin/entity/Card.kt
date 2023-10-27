package entity

import java.util.Objects

data class Card(val suit: CardSuit, val value :CardValue) {
    var revealed :Boolean = false
    var isReserveCard :Boolean = false
    val row: Int = 0

    override fun toString() = "$suit:$value"
    override fun hashCode() = Objects.hash(suit, value)

    override fun equals(other: Any?): Boolean {
        if (other is Card) {
            return suit == other.suit && value == other.value
        }
        return false
    }
}
