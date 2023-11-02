package service

import entity.Card
import entity.CardSuit
import entity.CardValue
import kotlin.test.Test
import kotlin.test.assertEquals

class TestRemovePair {

    lateinit var root: RootService
    lateinit var card1: Card
    lateinit var card2: Card

    @Test
    fun testWithoutAce() {
        root = RootService()
        root.gameService.startNewGame("Alice", "Bob")
        setup(6, 0, Card(CardSuit.HEARTS, CardValue.EIGHT), 6, 6, Card(CardSuit.HEARTS, CardValue.SEVEN))

        val currentPlayer = root.currentGame.currentPlayer
        val rowSize = root.currentGame.pyramid[6]!!.size
        val scoreBefore = currentPlayer.score

        root.playerActionService.removePair(card1, card2)

        assertEquals(scoreBefore + 2, currentPlayer.score)
        assertEquals(rowSize - 2, root.currentGame.pyramid[6]?.size)
    }

    @Test
    fun testWithAce() {
        root = RootService()
        root.gameService.startNewGame("Alice", "Bob")
        setup(6, 0, Card(CardSuit.SPADES, CardValue.ACE), 6, 6, Card(CardSuit.CLUBS, CardValue.FIVE))

        val currentPlayer = root.currentGame.currentPlayer
        val rowSize = root.currentGame.pyramid[6]!!.size
        val scoreBefore = currentPlayer.score

        root.playerActionService.removePair(card1, card2)

        assertEquals(scoreBefore + 1, currentPlayer.score)
    }

    @Test
    fun testOnlyAces() {
        root = RootService()
        root.gameService.startNewGame("Alice", "Bob")
        setup(6, 0, Card(CardSuit.SPADES, CardValue.ACE), 6, 6, Card(CardSuit.CLUBS, CardValue.ACE))

        val currentPlayer = root.currentGame.currentPlayer
        val rowSize = root.currentGame.pyramid[6]!!.size
        val scoreBefore = currentPlayer.score

        root.playerActionService.removePair(card1, card2)

        assertEquals(scoreBefore, currentPlayer.score)
        assertEquals(rowSize, root.currentGame.pyramid[6]?.size)
    }

    fun findPair(): Array<Card> {
        for (firstRow in root.currentGame.pyramid)
            for (firstCard in firstRow.value)
                for (secondRow in root.currentGame.pyramid)
                    for (secondCard in secondRow.value)
                        if (root.playerActionService.areCardsValid(firstCard, secondCard))
                            return arrayOf(firstCard, secondCard)
        return arrayOf()
    }

    fun setup(
        firstCardRow: Int,
        firstCardListIndex: Int,
        pCard1: Card,
        secondCardRow: Int,
        secondCardListIndex: Int,
        pCard2: Card
    ) {
        this.card1 = pCard1
        this.card2 = pCard2
        root.currentGame.pyramid[firstCardRow]?.set(firstCardListIndex, card1)
        root.currentGame.pyramid[secondCardRow]?.set(secondCardListIndex, card2)
        card1.row = firstCardRow
        card2.row = secondCardRow
    }
}