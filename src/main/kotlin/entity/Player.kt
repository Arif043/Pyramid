package entity

data class Player(val selectedCards: Array<Card>) {
    var score: Int = 0
    var hasPressed: Boolean = false
    var playerName: String = ""

    override fun toString() = playerName
    override fun hashCode() = playerName.hashCode()
    override fun equals(other: Any?): Boolean {
        return other is Player && playerName == other.playerName
    }
}