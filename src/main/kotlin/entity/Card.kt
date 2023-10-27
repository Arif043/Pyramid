package entity

import java.util.*

/**
 * Card represents a card in the pyramid game. A card includes a suit and a value
 *
 * @property suit one suit between the four possible suits in a french-suited card game: clubs, spades, hearts, or diamonds
 * @property value a value in [2:10]
 * @property revealed true if card is revealed, false otherwise
 * @property isReserveCard true if reservestack contains this card, false otherwise
 * @property row the row in which the card is located
 */
data class Card(val suit: CardSuit, val value :CardValue) {
    var revealed :Boolean = false
    var isReserveCard :Boolean = false
    val row: Int = 0

    /**
     * converts the card into a string
     *
     * the suit and value of the card is used for the string representation
     *
     * @return suit and value as string
     */
    override fun toString() = "$suit:$value"

    /**
     * computes the hashcode of a card
     *
     * @return hashcode - see [Objects.hash]
     */
    override fun hashCode() = Objects.hash(suit, value)

    /**
     * checks if a given object is equal.
     *
     * Two cards are equal when the corresponding suits and values are identical.
     *
     * @param other a reference to an object which we compare with this object
     * @return true if both objects are equal, false otherwise
     */
    override fun equals(other: Any?): Boolean {
        if (other is Card) {
            return suit == other.suit && value == other.value
        }
        return false
    }
}
