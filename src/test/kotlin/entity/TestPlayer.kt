package entity

import kotlin.test.*

/**
 * Test player states
 * @property player random player
 * @property player2 second random player who is equal to first player
 * @property player3 third random player who is different to others
 */
class TestPlayer {

    val player = Player("User")
    val player2 = Player("User")
    val player3 = Player("Other User")

    /**
     * Tests default values after creation
     */
    @Test
    fun defaultValues() {
        assertEquals(player.playerName, "User")
        assertEquals(player.score, 0)
        assertFalse(player.hasPressed, "Spieler hat gepasst")
    }

    /**
     * Tests pass()
     */
    @Test
    fun passed() {
        val otherPlayer = Player("User")
        otherPlayer.hasPressed = true
        assertTrue(otherPlayer.hasPressed)
    }

    /**
     * Tests toString()
     */
    @Test
    fun  testToString() {
        assertEquals(player.toString(), player.playerName)
    }

    /**
     * Tests hashCode()
     */
    @Test
    fun testHashCode() {
        assertEquals(player.hashCode(), player2.hashCode())
        assertNotEquals(player.hashCode(), player3.hashCode())
    }

    /**
     * Tests equals()
     */
    @Test
    fun testEquals() {
        assertEquals(player, player2)
        assertNotEquals(player, player3)
    }
}