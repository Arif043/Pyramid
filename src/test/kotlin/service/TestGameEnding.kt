package service

import kotlin.test.Test
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

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
}