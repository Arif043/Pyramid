package service

import kotlin.test.*

/**
 * Tests the pass function in PlayerActionService
 */
class TestPassing {

    /**
     * Tests if pass is working correctly when the first player passes
     *
     * When the first player passes so the current player switches to the second player
     */
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

    /**
     * Tests if pass is working correctly when the second player passes
     *
     * When the second player passes so the current player switches to the first player
     */
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