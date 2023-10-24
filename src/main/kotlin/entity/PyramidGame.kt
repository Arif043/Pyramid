package entity

import tools.aqua.bgw.util.Stack

data class PyramidGame(val drawStack: Stack<Card>, val reserveStack: Stack<Card>, val pyramid: HashMap<Int, Card>,
                        val player1: Player, val player2: Player, val currentPlayer: Player)