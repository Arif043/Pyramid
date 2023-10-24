package entity

data class Player(val selectedCards: Array<Card>) {
    var score: Int = 0
    var hasPressed: Boolean = false
    var playerName: String = ""
}