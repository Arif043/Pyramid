package service

import entity.Card
import kotlin.test.*

class TestRevealCard {

    var root = RootService()
    var pyramid: MutableMap<Int, ArrayList<Card>>

    init {
        root.gameService.startNewGame("Alice", "Bob")
        pyramid = root.currentGame.pyramid
    }

    @Test
    fun testSomeRowLeading() {
        val isNeighbourRevealedBefore = pyramid[6]!![1].revealed
        root.playerActionService.revealCard(pyramid[6]!![0])

        assertTrue(pyramid[6]!![1].revealed)
        assertNotEquals(isNeighbourRevealedBefore, pyramid[6]!![1].revealed)
    }

    @Test
    fun testSomeRowTailing() {
        val isNeighbourRevealedBefore = pyramid[6]!![pyramid.size - 2].revealed
        root.playerActionService.revealCard(pyramid[6]!![pyramid.size - 1])

        assertTrue(pyramid[6]!![pyramid.size - 2].revealed)
        assertNotEquals(isNeighbourRevealedBefore, pyramid[6]!![pyramid.size - 2].revealed)
    }

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