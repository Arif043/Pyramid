package service

import entity.Card
import kotlin.test.*

/**
 * Tests the revealCard function PlayerActionService
 * @property root the root service
 * @property pyramid the pyramid
 */
class TestRevealCard {

    var root = RootService()
    var pyramid: MutableMap<Int, ArrayList<Card>>

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
        val isNeighbourRevealedBefore = pyramid[6]!![1].revealed
        root.playerActionService.revealCard(pyramid[6]!![0])

        assertTrue(pyramid[6]!![1].revealed)
        assertNotEquals(isNeighbourRevealedBefore, pyramid[6]!![1].revealed)
    }

    /**
     * Tests if the neighbour of the last card gets revealed
     */
    @Test
    fun testSomeRowTailing() {
        val isNeighbourRevealedBefore = pyramid[6]!![pyramid.size - 2].revealed
        root.playerActionService.revealCard(pyramid[6]!![pyramid.size - 1])

        assertTrue(pyramid[6]!![pyramid.size - 2].revealed)
        assertNotEquals(isNeighbourRevealedBefore, pyramid[6]!![pyramid.size - 2].revealed)
    }

    /**
     * Tests if the neighbour of any card get not revealed
     */
    @Test
    fun testInvalidRevealing() {
        val isLeftNeighbourRevealedBefore = pyramid[6]!![2].revealed
        val isRightNeighbourRevealedBefore = pyramid[6]!![4].revealed
        assertFailsWith<IllegalArgumentException>("Card is not on border") { root.playerActionService.revealCard(pyramid[6]!![3]) }

        assertFalse(pyramid[6]!![2].revealed)
        assertFalse(pyramid[6]!![4].revealed)
        assertEquals(isLeftNeighbourRevealedBefore, pyramid[6]!![2].revealed)
        assertEquals(isRightNeighbourRevealedBefore, pyramid[6]!![4].revealed)
    }
}