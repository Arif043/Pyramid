package view

import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.core.MenuScene

class NewGameScene : MenuScene(1, 1), Refreshable {

    val player1NameLabel = Label(
        text = "Player 1",
        posX = 10,
        posY = 10
    )

    init {
        addComponents(player1NameLabel)
    }

    override fun refreshAfterEndGame() {
        TODO("Not yet implemented")
    }

    override fun refreshAfterStartNewGame() {
        TODO("Not yet implemented")
    }

    override fun refreshAfterRemovePair(cardsAreValid: Boolean) {
        TODO("Not yet implemented")
    }

    override fun refreshAfterDrawCard(stackNoptEmpty: Boolean) {
        TODO("Not yet implemented")
    }

    override fun refreshAfterPass() {
        TODO("Not yet implemented")
    }

    override fun refreshAfterSwitchPlayer() {
        TODO("Not yet implemented")
    }
}