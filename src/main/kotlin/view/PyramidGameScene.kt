package view

import entity.Card
import service.CardImageLoader
import service.RootService
import tools.aqua.bgw.animation.FlipAnimation
import tools.aqua.bgw.animation.MovementAnimation
import tools.aqua.bgw.animation.ParallelAnimation
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
        Button(800, 900, text = "Pass", font = Font(fontWeight = Font.FontWeight.BOLD))
    private val selectedCards = ArrayList<Triple<CardView, Card, Int>>()
    private val loader = CardImageLoader()
    private val pyramidX = 500.0
    private val pyramidY = 10.0
    private val scaleFactor = 0.5

    init {
        buildUI()
    }

    private fun buildUI() {
        pyramid.clear()
        reserveStack.clear()
        drawStack.clear()

        buildDrawStack()
        buildPyramid()
        buildReserveStack()

        currentPlayerLabel.text = "Current Player: ${rootService.currentGame.currentPlayer.playerName}"
        passButton.visual = ColorVisual.CYAN

        clearComponents()
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
        for (i in drawStackCards.lastIndex downTo 0) {
            drawStack.add(getCardViewFrom(drawStackCards[i]))
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
                if (entry.value.indexOf(card) == 0 || entry.value.indexOf(card) == entry.value.lastIndex)
                    c.showFront()

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
        val frontVisual = ImageVisual(loader.frontImageFor(card.suit, card.value))
        val backVisual = ImageVisual(loader.backImage)
        val cardView = CardView(front = frontVisual, back = backVisual)
        cardView.scale(scaleFactor)
        return cardView
    }

    private fun registerGameEvents() {
        drawStack.onMouseClicked = {
            if (drawStack.isNotEmpty())
                rootService.playerActionService.drawCard()
        }
        for (entry in pyramid)
            for (cardView in entry.value) {
                cardView.onMouseClicked = {
                    val index = entry.value.indexOf(cardView)
                    if (index == 0 || index == pyramid[entry.key]?.lastIndex) {

                        highlightSelectedCard(cardView, -20)
                        val card = rootService.currentGame.pyramid[entry.key]!![index]
                        selectedCards += Triple(cardView, card, entry.key)
                        confirmSelectedPair()
                    }
                }
            }
        reserveStack.onMouseClicked = {
            if (reserveStack.isNotEmpty()) {
                val topCard = reserveStack.pop()
                val topCardIsNotBlank = reserveStack.isNotEmpty()
                reserveStack.push(topCard)
                if (topCardIsNotBlank) {
                    selectedCards += Triple(reserveStack.peek(), rootService.currentGame.reserveStack.peek(), 7)
                    confirmSelectedPair()
                }
            }
        }
        passButton.onMouseClicked = {
            rootService.playerActionService.pass()
        }
    }

    private fun confirmSelectedPair() {
        if (selectedCards.size == 2) {
            rootService.playerActionService.removePair(selectedCards[0].second, selectedCards[1].second)
            selectedCards.clear()
        }
    }

    private fun highlightSelectedCard(cardView: CardView, y: Int) {
        playAnimation(MovementAnimation(
            componentView = cardView,
            byY = y,
            duration = 250
        ).apply {
            cardView.posY += y
        })
    }


    override fun refreshAfterEndGame() {
        println("end")
    }

    override fun refreshAfterStartNewGame() {
        buildUI()
    }

    override fun refreshAfterRemovePair(cardsAreValid: Boolean) {
        for (cardTriple in selectedCards) {
            if (cardsAreValid) {
                if (!cardTriple.second.isReserveCard) {
                    removeComponents(cardTriple.first)
                    val neighbourIndex = when (pyramid[cardTriple.third]?.indexOf(cardTriple.first)) {
                        0 -> 1
                        else -> pyramid[cardTriple.third]?.lastIndex?.minus(1)
                    }
                    if (neighbourIndex != null && pyramid[cardTriple.third]?.size != 1) {
                        pyramid[cardTriple.third]?.get(neighbourIndex)?.showFront()
                    }

                    pyramid[cardTriple.third]?.remove(cardTriple.first)
                } else {
                    reserveStack.pop()
                }
                passButton.visual = ColorVisual.CYAN
            } else {
                if (!cardTriple.second.isReserveCard)
                    highlightSelectedCard(cardTriple.first, 20)
            }
        }
    }

    override fun refreshAfterDrawCard(stackNoptEmpty: Boolean) {
        if (stackNoptEmpty) {
            //without animation
//            val cardView = drawStack.pop()
//            cardView.showFront()
//            reserveStack.push(cardView)
            val card = drawStack.peek()
            playAnimation(ParallelAnimation(
                MovementAnimation.toComponentView(
                    componentView = card,
                    toComponentViewPosition = reserveStack,
                    scene = this,
                ).apply {
                    onFinished = {
                        card.removeFromParent()
                        reserveStack.add(card)
                    }
                },
                FlipAnimation(
                    gameComponentView = card,
                    fromVisual = card.backVisual,
                    toVisual = card.frontVisual,
                    duration = 1000
                ).apply {
                    onFinished = {
                        card.showFront()
                    }
                }
            ))
            passButton.visual = ColorVisual.CYAN
        }
    }

    override fun refreshAfterPass() {
        passButton.visual = ColorVisual.RED
    }

    override fun refreshAfterSwitchPlayer() {
        currentPlayerLabel.text = "Current Player: ${rootService.currentGame.currentPlayer.playerName}"
    }
}