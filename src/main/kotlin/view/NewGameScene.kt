package view

import service.RootService
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.components.uicomponents.TextField
import tools.aqua.bgw.core.Alignment
import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual
import java.awt.Color

class NewGameScene(private val rootService: RootService) : MenuScene(270, 500), Refreshable {

    private val fontSize = 13

    val player1NameLabel = Label(
        text = "Player 1",
        posY = 10,
        font = Font(fontSize)
    )
    val player1NameField = TextField(
        posY = player1NameLabel.posY + player1NameLabel.height,
        font = Font(fontSize),
    )
    val player2NameLabel = Label(
        text = "Player 2",
        posY = player1NameField.posY + player1NameField.height + 15,
        font = Font(fontSize)
    )
    val player2NameField = TextField(
        posY = player2NameLabel.posY + player2NameLabel.height,
        font = Font(fontSize)
    )
    val errorLabel = Label(
        posY = player2NameField.posY + player2NameField.height + 50,
        width = width * 2,
        alignment = Alignment.TOP_LEFT,
        text = "Empty names are not allowed",
        font = Font(fontSize, Color.RED)
    )
    val quitButton = Button(
        text = "Quit",
        visual = ColorVisual.RED,
        posY = errorLabel.posY + 140,
        font = Font(fontSize)
    )
    val startButton = Button(
        text = "Start",
        visual = ColorVisual.GREEN,
        posY = quitButton.posY,
        posX = quitButton.width + 30,
        font = Font(fontSize)
    )

    init {
        player1NameField.width *= 1.9
        player2NameField.width *= 1.9
        addComponents(
            player1NameLabel,
            player1NameField,
            player2NameLabel,
            player2NameField,
            quitButton,
            startButton
        )
    }

    override fun refreshAfterEndGame() {

    }

    override fun refreshAfterStartNewGame() {

    }

    override fun refreshAfterRemovePair(cardsAreValid: Boolean) {

    }

    override fun refreshAfterDrawCard(stackNoptEmpty: Boolean) {

    }

    override fun refreshAfterPass() {

    }

    override fun refreshAfterSwitchPlayer() {

    }
}