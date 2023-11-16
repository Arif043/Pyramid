package view

import service.RootService
import tools.aqua.bgw.core.BoardGameApplication
import kotlin.system.exitProcess

class SopraApplication : BoardGameApplication("SoPra Game"), Refreshable {

    private val rootService = RootService()
    private var gameScene: PyramidGameScene
    private var newGameScene: NewGameScene

   init {
       //Just for style
       rootService.gameService.startNewGame("Alice", "Bob")

       gameScene = PyramidGameScene(rootService)
       newGameScene = NewGameScene(rootService)

       rootService.addRefreshables(
           this,
           gameScene,
           newGameScene
       )
       registerMenuEvents()


       showGameScene(gameScene)
       //showMenuScene(newGameScene) Just for developing
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