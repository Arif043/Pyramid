package service

import kotlin.test.*

class TestPassing {

    @Test
    fun testFirstPlayerIsPassing() {
        val root = RootService()
        root.gameService.startNewGame("Alice", "Bob")
        val game = root.currentGame

        val firstPlayer = game.currentPlayer
        val secondPlayer = if (game.currentPlayer == game.player1) game.player2 else game.player1
        val hasFirstPassedBefore = firstPlayer.hasPressed
        val hasSecondPassedBefore = secondPlayer.hasPressed

        root.playerActionService.pass()

        assertNotEquals(hasFirstPassedBefore, firstPlayer.hasPressed)
        assertEquals(hasSecondPassedBefore, secondPlayer.hasPressed)
        assertTrue(firstPlayer.hasPressed)
        assertFalse(secondPlayer.hasPressed)
        assertEquals(secondPlayer, game.currentPlayer)
    }

    @Test
    fun testSecondPlayerIsPassing() {
        val root = RootService()
        root.gameService.startNewGame("Alice", "Bob")
        val game = root.currentGame

        val firstPlayer = game.currentPlayer
        val secondPlayer = if (game.currentPlayer == game.player1) game.player2 else game.player1
        val hasFirstPassedBefore = firstPlayer.hasPressed
        val hasSecondPassedBefore = secondPlayer.hasPressed

        root.playerActionService.switchCurrentPlayer()
        root.playerActionService.pass()

        assertNotEquals(hasSecondPassedBefore, secondPlayer.hasPressed)
        assertEquals(hasFirstPassedBefore, firstPlayer.hasPressed)
        assertTrue(secondPlayer.hasPressed)
        assertFalse(firstPlayer.hasPressed)
        assertEquals(firstPlayer, game.currentPlayer)
    }
}