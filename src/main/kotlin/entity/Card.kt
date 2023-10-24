package entity

import tools.aqua.bgw.components.container.CardStack

data class Card(val suit: CardSuit, val value :CardValue) {
    var revealed :Boolean = false
    var isReserveCard :Boolean = false
    val row: Int = 0
}
