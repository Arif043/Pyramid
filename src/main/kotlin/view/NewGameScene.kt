package view

import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.components.uicomponents.TextField
import tools.aqua.bgw.core.Alignment
import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual
import java.awt.Color
import kotlin.system.exitProcess

class NewGameScene : MenuScene(270, 500), Refreshable {

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
        registerHandler()

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

    private fun registerHandler() {
        quitButton.onMouseClicked = {
            exitProcess(0)
        }
        startButton.onMouseClicked = {
            if (player1NameField.text.isBlank() || player2NameField.text.isBlank()) {
                removeComponents(errorLabel)
                addComponents(errorLabel)
            }
            else {

            }
        }
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