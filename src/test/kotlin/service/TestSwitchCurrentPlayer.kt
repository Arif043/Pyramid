package service

import kotlin.test.Test
import kotlin.test.assertNotEquals

class TestSwitchCurrentPlayer {

    @Test
    fun test() {
        val root = RootService()
        root.gameService.startNewGame("Alice", "Bob")
        val currentPlayerNameBefore = root.currentGame.currentPlayer.playerName
        root.playerActionService.switchCurrentPlayer()
        assertNotEquals(currentPlayerNameBefore, root.currentGame.currentPlayer.playerName)
    }
}