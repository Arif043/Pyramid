package entity

/**
 * Player class represents a player in the game
 *
 * @property playerName the name of a player which can be set
 * @property score holds the current score of the player
 * @property hasPressed is true when player has passed, otherwise false
 */
data class Player(var playerName :String) {
    var score: Int = 0
    var hasPressed: Boolean = false

    /**
     * converts the player into a string
     *
     * the players name is used for the string representation
     *
     * @return the name of the player
     */
    override fun toString() = playerName
}