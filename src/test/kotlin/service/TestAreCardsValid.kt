package service

import entity.Card
import entity.CardSuit
import entity.CardValue
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class TestAreCardsValid {

    private val root = RootService()

    @Test
    fun testSumIsEqualTo15() {
        val card1 = Card(CardSuit.CLUBS, CardValue.THREE)
        val card2 = Card(CardSuit.HEARTS, CardValue.QUEEN)
        assertTrue(root.playerActionService.areCardsValid(card1, card2))
    }

    @Test
    fun testSumIsNotEqualTo15() {
        val card1 = Card(CardSuit.CLUBS, CardValue.FIVE)
        val card2 = Card(CardSuit.HEARTS, CardValue.QUEEN)
        assertFalse(root.playerActionService.areCardsValid(card1, card2))
    }

    @Test
    fun testOnlyAces() {
        val card1 = Card(CardSuit.CLUBS, CardValue.ACE)
        val card2 = Card(CardSuit.HEARTS, CardValue.ACE)
        assertFalse(root.playerActionService.areCardsValid(card1, card2))
    }

    @Test
    fun testWtihOneAce() {
        val card1 = Card(CardSuit.CLUBS, CardValue.ACE)
        val card2 = Card(CardSuit.HEARTS, CardValue.QUEEN)
        assertTrue(root.playerActionService.areCardsValid(card1, card2))
    }
}