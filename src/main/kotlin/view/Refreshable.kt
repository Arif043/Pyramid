package view

interface Refreshable {
    fun refreshAfterEndGame() {}
    fun refreshAfterStartNewGame() {}
    fun refreshAfterRemovePair(cardsAreValid: Boolean) {}
    fun refreshAfterDrawCard(stackNoptEmpty: Boolean) {}
    fun refreshAfterPass() {}
    fun refreshAfterSwitchPlayer() {}
}