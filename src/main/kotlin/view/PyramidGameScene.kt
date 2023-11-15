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

    val drawStack = CardStack<CardView>(100, 100)
    val reserveStack = CardStack<CardView>(500, 100)

    init {
        buildDrawStack()
        //buildReserveStack()
        onKeyPressed = {
            drawStack.pop()
        }
        //val cc = CardView(front = ImageVisual(getCardViewFrom(rootService.currentGame.drawStack.peek())))

        addComponents(
            drawStack,
            reserveStack)
    }

    private fun buildDrawStack() {
        val drawStackCards = rootService.currentGame.drawStack.peekAll()
        for (i in 0..drawStackCards.lastIndex) {
            drawStack.add(getCardViewFrom(drawStackCards[i]), i)
        }
    }

    /*private fun buildReserveStack() {
        val reserveStackCards = rootService.currentGame.reserveStack.peekAll()
        for (i in 0..reserveStackCards.lastIndex) {
            reserveStack.add(getCardViewFrom(reserveStackCards[i]), i)
        }
    }*/

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