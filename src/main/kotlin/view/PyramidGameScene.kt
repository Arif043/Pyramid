package view

import entity.Card
import service.CardImageLoader
import service.RootService
import tools.aqua.bgw.components.container.CardStack
import tools.aqua.bgw.components.gamecomponentviews.CardView
import tools.aqua.bgw.core.BoardGameScene
import tools.aqua.bgw.visual.ColorVisual
import tools.aqua.bgw.visual.ImageVisual

class PyramidGameScene(private val rootService: RootService) : BoardGameScene(background = ColorVisual.ORANGE),
    Refreshable {

    val c = CardStack<CardView>(10, 10)

    init {
        buildDrawStack()
        //val cc = CardView(front = ImageVisual(getCardViewFrom(rootService.currentGame.drawStack.peek())))

        addComponents(c)
        print(c.)
    }

    private fun buildDrawStack() {
        val drawStackCards = rootService.currentGame.drawStack.peekAll()
        for (i in 0..drawStackCards.lastIndex) {
            c.add(getCardViewFrom(drawStackCards[i]), i)
        }

    }

    private fun getCardViewFrom(card: Card) : CardView {
        val loader = CardImageLoader()
        return CardView(front = ImageVisual(loader.frontImageFor(card.suit, card.value)))
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