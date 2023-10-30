package entity

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals

class TestCard {

    @Test
    fun defaultValues() {
        val card = Card(CardSuit.HEARTS, CardValue.SIX)
        assertFalse(card.isReserveCard, "Karte ist auf dem Nachziehstapel")
        assertFalse(card.revealed, "Karte ist aufgedeckt")
        assertEquals(0, card.row)
    }

    @Test
    fun testToString() {
        val card = Card(CardSuit.HEARTS, CardValue.SIX)
        assertEquals("♥:6", card.toString(), "dadad")
        print(card)
    }

    @Test
    fun testEquals() {
        val card1 = Card(CardSuit.HEARTS, CardValue.SIX)
        val card2 = Card(CardSuit.HEARTS, CardValue.SIX)
        val card3 = Card(CardSuit.SPADES, CardValue.KING)
        assertEquals(card1, card2)
        assertNotEquals(card1, card3)
    }

    @Test
    fun testHashCode() {
        val card1 = Card(CardSuit.HEARTS, CardValue.SIX)
        val card2 = Card(CardSuit.HEARTS, CardValue.SIX)
        val card3 = Card(CardSuit.SPADES, CardValue.KING)
        assertEquals(card1.hashCode(), card2.hashCode())
        assertNotEquals(card1.hashCode(), card3.hashCode())
    }
}