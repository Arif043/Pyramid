package service

import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull

/**
 * Test startNewGame()
 */
class TestStartNewGame {

    /**
     * Tests if currentGame will be initialized.
     */
    @Test
    fun test() {
        val rootService = RootService()
        assertFailsWith<UninitializedPropertyAccessException> { rootService.currentGame }
        rootService.gameService.startNewGame("Alice", "Bob")
        assertNotNull(rootService.currentGame)
    }
}