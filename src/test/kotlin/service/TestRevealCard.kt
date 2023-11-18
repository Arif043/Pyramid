package service

import entity.Card
import kotlin.test.*

/**
 * Tests the revealCard function PlayerActionService
 * @property root the root service
 * @property pyramid the pyramid
 */
class TestRevealCard {

    private var root = RootService()
    private var pyramid: MutableMap<Int, ArrayList<Card>>

    init {
        root.gameService.startNewGame("Alice", "Bob")
        pyramid = root.currentGame.pyramid
    }

    /**
     * Tests if the neighbour of the first card gets revealed
     *
     */
    @Test
    fun testSomeRowLeading() {
        val isNeighbourRevealedBefore = pyramid[6]?.get(1)?.revealed
        pyramid[6]?.let { root.playerActionService.revealCard(it[0]) }

        assertTrue(pyramid[6]?.get(1)?.revealed ?: false)
        assertNotEquals(isNeighbourRevealedBefore, pyramid[6]?.get(1)?.revealed)
    }

    /**
     * Tests if the neighbour of the last card gets revealed
     */
    @Test
    fun testSomeRowTailing() {
        val isNeighbourRevealedBefore = pyramid[6]?.get(pyramid.size - 2)?.revealed
        pyramid[6]?.get(pyramid.size - 1)?.let { root.playerActionService.revealCard(it) }

        assertTrue(pyramid[6]?.get(pyramid.size - 2)?.revealed ?: false)
        assertNotEquals(isNeighbourRevealedBefore, pyramid[6]?.get(pyramid.size - 2)?.revealed)
    }

    /**
     * Tests if the neighbour of any card get not revealed
     */
    @Test
    fun testInvalidRevealing() {
        val isLeftNeighbourRevealedBefore = pyramid[6]?.get(2)?.revealed
        val isRightNeighbourRevealedBefore = pyramid[6]?.get(4)?.revealed
        assertFailsWith<IllegalArgumentException>("Card is not on border") {
            if (pyramid.isNotEmpty())
                pyramid[6]?.let { root.playerActionService.revealCard(it.get(3)) }
        }

        assertFalse(pyramid[6]?.get(2)?.revealed ?: true)
        assertFalse(pyramid[6]?.get(4)?.revealed ?: true)
        assertEquals(isLeftNeighbourRevealedBefore, pyramid[6]?.get(2)?.revealed)
        assertEquals(isRightNeighbourRevealedBefore, pyramid[6]?.get(4)?.revealed)
    }

    /**
     * Tests if a revealCard call crashes on a reserve card.
     */
    @Test
    fun testIfReserveCardGetsRevealed() {
        root.playerActionService.drawCard()
        root.playerActionService.revealCard(root.currentGame.reserveStack.peek())
    }
}