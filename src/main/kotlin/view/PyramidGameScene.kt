package view

import tools.aqua.bgw.core.BoardGameScene
import tools.aqua.bgw.visual.ColorVisual

class PyramidGameScene : BoardGameScene(background = ColorVisual.ORANGE), Refreshable {
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