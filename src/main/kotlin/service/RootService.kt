package service

import entity.PyramidGame

class RootService(var currentGame: PyramidGame) {
    val gameService: GameService = GameService(this)
    val playerActionService: PlayerActionService = PlayerActionService(this)
}