package service

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

/**
 * Tests the drawCard function in PlayerActionService
 */
class TestDrawCard {

    /**
     * Tests if a card get drawn from the drawStack and get pushed into the reserveStack
     *
     * If the drawStack is not empty, the top get drawn and pushed onto the reserveStack.
     * The size of drawStack decreases by 1 and the size of reserveStack increases by 1
     */
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

    /**
     * Tests if an IllegalStateException is thrown when we try to draw on an empty stack
     *
     * If the drawStack is empty so we can not draw a card because there is no any card on the stack
     * The function throws instead an IllegalStateException.
     */
    @Test
    fun testEmptyStack() {
        val root = RootService()
        root.gameService.startNewGame("Alice", "Bob")
        while (root.currentGame.drawStack.isNotEmpty())
            root.currentGame.drawStack.pop()
        assertFailsWith<IllegalStateException> { root.playerActionService.drawCard() }
    }
}