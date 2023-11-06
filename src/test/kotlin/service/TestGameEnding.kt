package service

import kotlin.test.Test
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

/**
 * Tests the endGame function in GameService
 */
class TestGameEnding {

    @Test
    fun testEndGame() {
        val root = RootService()
        root.gameService.startNewGame("Alice", "Bob")
        val gameEndedBefore = root.gameService.gameEnded
        root.gameService.endGame()
        assertNotEquals(gameEndedBefore, root.gameService.gameEnded)
        assertTrue(root.gameService.gameEnded)
    }

    /**
     * Tests if game ends when both player passed directly
     *
     * When both player pass directly so the game must end
     */
    @Test
    fun testEndGameDuringPassing() {
        val root = RootService()
        root.gameService.startNewGame("Alice", "Bob")
        val gameEndedBefore = root.gameService.gameEnded
        root.playerActionService.pass()
        root.playerActionService.pass()
        assertNotEquals(gameEndedBefore, root.gameService.gameEnded)
        assertTrue(root.gameService.gameEnded)
    }
}