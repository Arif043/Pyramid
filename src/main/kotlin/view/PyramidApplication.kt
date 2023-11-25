package view

import service.RootService
import tools.aqua.bgw.core.BoardGameApplication
import kotlin.system.exitProcess

/**
 * The game class represents the whole game.
 *
 * @property rootService the root service
 * @property gameScene the main menu scene
 * @property newGameScene represents the start menu
 * @property gameScene represents the end game
 */
class PyramidApplication : BoardGameApplication("Pyramid Game"), Refreshable {

    private val rootService = RootService()
    private var gameScene: PyramidGameScene
    private var newGameScene: NewGameScene
    private var gameEndMenu: GameFinishedMenuScene

   init {
       //Just for style
       rootService.gameService.startNewGame("Alice", "Bob")

       //Creates the scenes
       gameScene = PyramidGameScene(rootService)
       newGameScene = NewGameScene()
       gameEndMenu = GameFinishedMenuScene(rootService)

       rootService.addRefreshables(
           gameEndMenu,
           gameScene,
           newGameScene,
           this
       )
       registerMenuEvents()


       showGameScene(gameScene)
       showMenuScene(newGameScene)
   }

    /**
     * Regists the event handler for the menu events.
     */
    private fun registerMenuEvents() {
        newGameScene.quitButton.onMouseClicked = {
            exitProcess(0)
        }
        newGameScene.startButton.onMouseClicked = {
            //If the names have not been entered then shows an error message
            if (newGameScene.player1NameField.text.isBlank() || newGameScene.player2NameField.text.isBlank()) {
                newGameScene.removeComponents(newGameScene.errorLabel)
                newGameScene.addComponents(newGameScene.errorLabel)
            } else {
                newGameScene.removeComponents(newGameScene.errorLabel)
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

    /**
     * Switch to the end menu.
     */
    override fun refreshAfterEndGame() {
        hideMenuScene()
        showMenuScene(gameEndMenu)
    }
}