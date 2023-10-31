package entity

import tools.aqua.bgw.util.Stack

/**
 * PyramidGame the root entity class.
 * consists the other entity objects
 *
 * @property drawStack a stack which provides cards for the player
 * @property reserveStack a stack which contains drawn cards from the drawStack
 * @property pyramid a hashmap which represents the pyramid in the game.
 *           The key is the row number. Each of the rows have an own list.
 * @property player1 the first player
 * @property player2 the second player
 * @property currentPlayer a reference that holds the currentplayer
 */
data class PyramidGame(val drawStack: Stack<Card>,
                       val reserveStack: Stack<Card>,
                       val pyramid: MutableMap<Int, ArrayList<Card>>,
                       val player1: Player,
                       val player2: Player
) {
    lateinit var currentPlayer: Player
}