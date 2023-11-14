package view

import entity.Card
import service.CardImageLoader
import service.RootService
import tools.aqua.bgw.components.container.CardStack
import tools.aqua.bgw.components.gamecomponentviews.CardView
import tools.aqua.bgw.core.BoardGameScene
import tools.aqua.bgw.visual.ColorVisual
import tools.aqua.bgw.visual.ImageVisual
import java.awt.image.BufferedImage

class PyramidGameScene(private val rootService: RootService) : BoardGameScene(background = ColorVisual.ORANGE),
    Refreshable {

    val c = CardStack<CardView>()

    init {
        val loader = CardImageLoader()
        val cc = CardView(front = ImageVisual(getCardViewFrom(rootService.currentGame.drawStack.peek())))
        addComponents(cc)
    }

    private fun getCardViewFrom(card: Card): BufferedImage {
        val loader = CardImageLoader()
        return loader.frontImageFor(card.suit, card.value)
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