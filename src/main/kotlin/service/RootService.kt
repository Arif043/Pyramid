package service

import entity.PyramidGame

/**
 * the central root service
 *
 * @property currentGame holds the current game
 * @property gameService holds the game service
 * @property playerActionService holds the player action service
 */
class RootService {
    lateinit var currentGame: PyramidGame
    val gameService: GameService = GameService(this)
    val playerActionService: PlayerActionService = PlayerActionService(this)
}