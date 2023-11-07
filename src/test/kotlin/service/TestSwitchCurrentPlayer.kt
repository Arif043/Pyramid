package service

import kotlin.test.Test
import kotlin.test.assertNotEquals

/**
 * Tests the switchCurrentPlayer function in PlayerActionService
 */
class TestSwitchCurrentPlayer {

    /**
     * Tests if the currentPlayer switches to the other player
     */
    @Test
    fun test() {
        val root = RootService()
        root.gameService.startNewGame("Alice", "Bob")
        val currentPlayerNameBefore = root.currentGame.currentPlayer.playerName
        root.playerActionService.switchCurrentPlayer()
        assertNotEquals(currentPlayerNameBefore, root.currentGame.currentPlayer.playerName)
    }
}