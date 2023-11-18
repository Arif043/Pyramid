package service

import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Tests the endGame function in GameService
 */
class TestGameEnding {

    /**
     * Tests game ending through passing
     */
    @Test
    fun testEndGame() {
        val rootService = RootService()
        rootService.gameService.startNewGame("Alice", "Bob")
        rootService.playerActionService.pass()
        rootService.playerActionService.pass()
        assertEquals(rootService.currentGame.player1.hasPressed, rootService.currentGame.player2.hasPressed)
    }
}