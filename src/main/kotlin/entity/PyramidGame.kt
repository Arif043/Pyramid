package entity

import tools.aqua.bgw.util.Stack

/**
 * PyramidGame the root entity class.
 * consists the other enitiy objects
 */
data class PyramidGame(val drawStack: Stack<Card>,
                       val reserveStack: Stack<Card>,
                       val pyramid: HashMap<Int, ArrayList<Card>>,
                       val player1: Player,
                       val player2: Player,
                       val currentPlayer: Player)