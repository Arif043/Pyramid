package service

import entity.PyramidGame
import view.Refreshable

/**
 * The central root service
 *
 * @property currentGame holds the current game
 * @property gameService holds the game service
 * @property playerActionService holds the player action service
 */
class RootService {
    lateinit var currentGame: PyramidGame
    val gameService: GameService = GameService(this)
    val playerActionService: PlayerActionService = PlayerActionService(this)

    /**
     * Adds the provided [newRefreshable] to all services connected
     * to this root service
     */
    fun addRefreshable(newRefreshable: Refreshable) {
        gameService.addRefreshable(newRefreshable)
        playerActionService.addRefreshable(newRefreshable)
    }

    /**
     * Adds each of the provided [newRefreshables] to all services
     * connected to this root service
     */
    fun addRefreshables(vararg newRefreshables: Refreshable) {
        newRefreshables.forEach { addRefreshable(it) }
    }
}