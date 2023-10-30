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

    init {
        if (playerName == "")
            throw IllegalArgumentException("empty name")
    }

    /**
     * converts the player into a string
     *
     * the players name is used for the string representation
     *
     * @return the name of the player
     */
    override fun toString() = playerName

    /**
     * computes the hashcode of a player
     *
     * @return hashcode - see [String.hashCode]
     */
    override fun hashCode() = playerName.hashCode()

    /**
     * checks if a given object is equal. Two player are equal when both have the same name.
     *
     * Both objects are equal when other is a player and has the same name as this
     *
     * @param other a reference to an object which we compare with this object
     * @return true if both objects are equal, false otherwise
     */
    override fun equals(other: Any?): Boolean {
        return other is Player && playerName == other.playerName
    }
}