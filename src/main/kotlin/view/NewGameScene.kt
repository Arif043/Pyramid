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

/**
 * Menuscene for representing the start menu.
 *
 * Displays two textfields expecting the names of the player and
 * two buttons for closing and starting the game
 *
 * @property rootService the root service
 * @property fontSize the standard font size for all text components in this scene
 * @property player1NameLabel indicates that the name of the first player should be entered
 * @property player2NameLabel indicates that the name of the second player should be entered
 * @property player1NameField textfield that is expecting the name of the first player
 * @property player2NameField textfield that is expecting the name of the second player
 * @property errorLabel label which shows an error message when one of the textfields is empty
 * @property quitButton button that closes the application
 * @property startButton button that starts the game
 */
class NewGameScene(private val rootService: RootService) : MenuScene(270, 500), Refreshable {

    private val fontSize = 13
    private val player1NameLabel = Label(
        text = "Player 1",
        posY = 10,
        font = Font(fontSize)
    )
    val player1NameField = TextField(
        posY = player1NameLabel.posY + player1NameLabel.height,
        font = Font(fontSize),
    )
    private val player2NameLabel = Label(
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
        rootService.gameService //Just for detekt
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
}