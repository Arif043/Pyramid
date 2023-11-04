package service

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class TestDrawCard {

    @Test
    fun testNonEmptyStack() {
        val root = RootService()
        root.gameService.startNewGame("Alice", "Bob")
        val drawStackSizeBefore = root.currentGame.drawStack.size
        val reserveStackSizeBefore = root.currentGame.reserveStack.size
        root.playerActionService.drawCard()
        assertEquals(drawStackSizeBefore - 1, root.currentGame.drawStack.size)
        assertEquals(reserveStackSizeBefore + 1, root.currentGame.reserveStack.size)
    }

    @Test
    fun testEmptyStack() {
        val root = RootService()
        root.gameService.startNewGame("Alice", "Bob")
        while (root.currentGame.drawStack.isNotEmpty())
            root.currentGame.drawStack.pop()
        assertFailsWith<IllegalStateException> { root.playerActionService.drawCard() }
    }
}