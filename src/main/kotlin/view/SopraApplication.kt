package view

import service.RootService
import tools.aqua.bgw.core.BoardGameApplication
import kotlin.system.exitProcess

class SopraApplication : BoardGameApplication("SoPra Game"), Refreshable {

    private val rootService = RootService()
    private var gameScene: PyramidGameScene
    private var newGameScene: NewGameScene
    private var gameEndMenu: GameFinishedMenuScene

   init {
       //Just for style
       rootService.gameService.startNewGame("Alice", "Bob")

       gameScene = PyramidGameScene(rootService)
       newGameScene = NewGameScene(rootService)
       gameEndMenu = GameFinishedMenuScene(rootService)

       rootService.addRefreshables(
           gameEndMenu,
           gameScene,
           newGameScene,
           this
       )
       registerMenuEvents()


       showGameScene(gameScene)
       showMenuScene(gameEndMenu)
       //showMenuScene(newGameScene)
   }

    private fun registerMenuEvents() {
        newGameScene.quitButton.onMouseClicked = {
            exitProcess(0)
        }
        newGameScene.startButton.onMouseClicked = {
            if (newGameScene.player1NameField.text.isBlank() || newGameScene.player2NameField.text.isBlank()) {
                newGameScene.removeComponents(newGameScene.errorLabel)
                newGameScene.addComponents(newGameScene.errorLabel)
            } else {
                rootService.gameService.startNewGame(
                    newGameScene.player1NameField.text.trim(),
                    newGameScene.player2NameField.text.trim()
                )
                hideMenuScene()
            }
        }
        gameEndMenu.restartButton.onMouseClicked = {
            hideMenuScene()
            showMenuScene(newGameScene)
        }
        gameEndMenu.quitButton.onMouseClicked = {
            exitProcess(0)
        }
    }

    override fun refreshAfterEndGame() {
        hideMenuScene()
        showMenuScene(gameEndMenu)
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