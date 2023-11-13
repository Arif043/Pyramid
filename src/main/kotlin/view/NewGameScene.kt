package view

import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.components.uicomponents.TextField
import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.util.Font

class NewGameScene : MenuScene(), Refreshable {

    val player1NameLabel = Label(
        text = "Player 1",
        posX = 10,
        posY = 10,
        font = Font(26)
    )
    val player1NameField = TextField(
        posY = player1NameLabel.posY + player1NameLabel.height + 15,
        font = Font(26)
    )

    init {
        addComponents(
            player1NameLabel,
            player1NameField
        )
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