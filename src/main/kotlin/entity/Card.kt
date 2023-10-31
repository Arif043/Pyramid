package entity

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
}