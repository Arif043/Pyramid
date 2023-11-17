package view

import service.RootService
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual

class GameFinishedMenuScene(private val rootService: RootService) : MenuScene(270, 500), Refreshable {

    private val fontSize = 13
    private val winnerLabel = Label(
        posX = 70,
        posY = 10,
        text = "Winner",
        font = Font(fontSize)
    )
    private val player1Name = Label(
        posX = 20,
        posY = 100,
        text = rootService.currentGame.player1.playerName,
        font = Font(fontSize)
    )
    private val player2Name = Label(
        posX = 120,
        posY = 100,
        text = rootService.currentGame.player2.playerName,
        font = Font(fontSize)
    )
    private val player1Score = Label(
        posX = 20,
        posY = 150,
        text = rootService.currentGame.player1.score.toString(),
        font = Font(fontSize)
    )
    private val player2Score = Label(
        posX = 120,
        posY = 150,
        text = rootService.currentGame.player2.score.toString(),
        font = Font(fontSize)
    )
    val quitButton = Button(
        text = "Quit",
        visual = ColorVisual.RED,
        font = Font(fontSize),
        posY = 200
    )
    val restartButton = Button(
        text = "Restart",
        visual = ColorVisual.GREEN,
        font = Font(fontSize),
        posX = quitButton.width + 30,
        posY = 200
    )

    init {
        addComponents(
            winnerLabel,
            player1Name,
            player2Name,
            player1Score,
            player2Score,
            quitButton,
            restartButton
        )
    }

    override fun refreshAfterEndGame() {
        player1Name.text = rootService.currentGame.player1.playerName
        player2Name.text = rootService.currentGame.player2.playerName
        player1Score.text = rootService.currentGame.player1.score.toString()
        player2Score.text = rootService.currentGame.player2.score.toString()
        winnerLabel.text = "Winner: " +
                if (rootService.currentGame.player1.score > rootService.currentGame.player2.score)
                    player1Name.text
                else if (rootService.currentGame.player2.score > rootService.currentGame.player1.score)
                    player2Name.text
                else
                    "Nobody"
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