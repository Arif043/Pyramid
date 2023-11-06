package service

import entity.Card
import entity.CardSuit
import entity.CardValue
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * Tests the areCardsValid function in PlayerActionService
 *
 * @property root the root service used in all tests
 */
class TestAreCardsValid {

    private val root = RootService()

    /**
     * Tests if cards get removed when sum is 15
     *
     * If the sum of both cards is 15, without Ace, the pair is valid
     */
    @Test
    fun testSumIsEqualTo15() {
        val card1 = Card(CardSuit.CLUBS, CardValue.THREE)
        val card2 = Card(CardSuit.HEARTS, CardValue.QUEEN)
        assertTrue(root.playerActionService.areCardsValid(card1, card2))
    }

    /**
     * Tests if cards get not removed when sum isnt 15
     *
     * If the sum of both cards isnt 15, without, ace, the pair is not valid
     */
    @Test
    fun testSumIsNotEqualTo15() {
        val card1 = Card(CardSuit.CLUBS, CardValue.FIVE)
        val card2 = Card(CardSuit.HEARTS, CardValue.QUEEN)
        assertFalse(root.playerActionService.areCardsValid(card1, card2))
    }

    /**
     * Tests if cards get not removed when both cards are aces
     *
     * If both cards are aces, the pair is invalid
     */

    /**
     *
     */
    @Test
    fun testOnlyAces() {
        val card1 = Card(CardSuit.CLUBS, CardValue.ACE)
        val card2 = Card(CardSuit.HEARTS, CardValue.ACE)
        assertFalse(root.playerActionService.areCardsValid(card1, card2))
    }

    /**
     * Tests if cards get removed when only one card is an ace
     *
     * If one card is an ace, the pair is valid
     */
    @Test
    fun testWtihOneAce() {
        val card1 = Card(CardSuit.CLUBS, CardValue.ACE)
        val card2 = Card(CardSuit.HEARTS, CardValue.QUEEN)
        assertTrue(root.playerActionService.areCardsValid(card1, card2))
    }
}