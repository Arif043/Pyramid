package entity

import kotlin.test.*

class TestPlayer {

    val player = Player("User")
    val player2 = Player("User")
    val player3 = Player("Other User")

    @Test
    fun defaultValues() {
        assertEquals(player.playerName, "User")
        assertEquals(player.score, 0)
        assertFalse(player.hasPressed, "Spieler hat gepasst")
    }

    @Test
    fun passed() {
        val otherPlayer = Player("User")
        otherPlayer.hasPressed = true
        assertTrue(otherPlayer.hasPressed)
    }

    @Test
    fun  testToString() {
        assertEquals(player.toString(), player.playerName)
    }

    @Test
    fun testHashCode() {
        assertEquals(player.hashCode(), player2.hashCode())
        assertNotEquals(player.hashCode(), player3.hashCode())
    }

    @Test
    fun testEquals() {
        assertEquals(player, player2)
        assertNotEquals(player, player3)
    }
}