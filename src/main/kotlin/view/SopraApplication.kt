package view

import service.RootService
import tools.aqua.bgw.core.BoardGameApplication

class SopraApplication : BoardGameApplication("SoPra Game"), Refreshable {

    private val rootService = RootService()
    private val gameScene = PyramidGameScene()
    private val newGameScene = NewGameScene()

   init {
       rootService.addRefreshables(
           this,
           gameScene,
           newGameScene
       )

       rootService.gameService.startNewGame("Alice", "Bob")

       showGameScene(gameScene)
       showMenuScene(newGameScene, 0)
       Thread.sleep(3000)
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