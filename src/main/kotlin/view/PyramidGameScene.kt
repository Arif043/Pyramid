package view

import entity.Card
import service.CardImageLoader
import service.RootService
import tools.aqua.bgw.components.container.CardStack
import tools.aqua.bgw.components.gamecomponentviews.CardView
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.core.Alignment
import tools.aqua.bgw.core.BoardGameScene
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual
import tools.aqua.bgw.visual.ImageVisual

class PyramidGameScene(private val rootService: RootService) : BoardGameScene(background = ColorVisual.ORANGE),
    Refreshable {

    private val drawStack = CardStack<CardView>(100, 100)
    private val reserveStack = CardStack<CardView>(900, 100)
    private val pyramid = HashMap<Int, ArrayList<CardView>>()
    private val currentPlayerLabel =
        Label(300, 900, width = 3 * width, alignment = Alignment.TOP_LEFT, font = Font(26), text = "Current Player")
    private val passButton =
        Button(800, 900, text = "Pass", visual = ColorVisual.CYAN, font = Font(fontWeight = Font.FontWeight.BOLD))
    private val pyramidX = 500.0
    private val pyramidY = 10.0
    private val scaleFactor = 0.5

    init {
        buildDrawStack()
        buildPyramid()
        buildReserveStack()

        currentPlayerLabel.text += ": ${rootService.currentGame.currentPlayer.playerName}"

        addComponents(
            drawStack,
            reserveStack,
            currentPlayerLabel,
            passButton
        )
        pyramid.forEach { it.value.forEach { addComponents(it) } }
        registerGameEvents()
    }

    private fun buildDrawStack() {
        val drawStackCards = rootService.currentGame.drawStack.peekAll()
        for (i in 0..drawStackCards.lastIndex) {
            drawStack.add(getCardViewFrom(drawStackCards[i]), i)
        }
    }

    private fun buildPyramid() {
        var deltaPyramidX = pyramidX
        var deltaPyramidY = pyramidY
        val cardWidth = drawStack.peek().actualWidth
        val cardHeight = drawStack.peek().actualHeight

        for (entry in rootService.currentGame.pyramid) {
            pyramid[entry.key] = ArrayList()
            for (card in entry.value) {
                val c = getCardViewFrom(card)
                c.posX = deltaPyramidX
                c.posY = deltaPyramidY

                pyramid[entry.key]?.add(c)

                deltaPyramidX += 2 * c.actualWidth
            }
            deltaPyramidX = pyramidX - (entry.key + 1) * cardWidth
            deltaPyramidY += cardHeight
        }
    }

    private fun buildReserveStack() {
        val loader = CardImageLoader()
        val blankCardView = CardView(front = ImageVisual(loader.blankImage))
        blankCardView.scale(scaleFactor)
        reserveStack.push(blankCardView)
    }

    private fun getCardViewFrom(card: Card) : CardView {
        val loader = CardImageLoader()
        val cardView = CardView(front = ImageVisual(loader.frontImageFor(card.suit, card.value)))
        cardView.scale(scaleFactor)
        return cardView
    }

    private fun registerGameEvents() {
        drawStack.onMouseClicked = {
            rootService.playerActionService.drawCard()
        }
        for (entry in pyramid)
            for (cardView in entry.value) {
                cardView.onMouseClicked = {

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
        reserveStack.push(drawStack.pop())
    }

    override fun refreshAfterPass() {
    }

    override fun refreshAfterSwitchPlayer() {
    }
}