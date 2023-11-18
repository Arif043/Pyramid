package service

import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull

class TestStartNewGame {

    @Test
    fun test() {
        val rootService = RootService()
        assertFailsWith<UninitializedPropertyAccessException> { rootService.currentGame }
        rootService.gameService.startNewGame("Alice", "Bob")
        assertNotNull(rootService.currentGame)
    }
}