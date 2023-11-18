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

/**
 * Gamescene for representing the game
 *
 * Shows the pyramid, passbutton, the drawstack and the reservestack
 *
 * @property rootService the root service
 * @property drawStack an ui stack that projects the drawStack in the entity layer
 * @property reserveStack an ui stack that projects the reserveStack in the entity layer
 * @property pyramid a map that projects the pyramid in the entity layer
 * @property currentPlayerLabel label which shows the current player
 * @property passButton a button that the current player can press and the player passes as a result
 * @property selectedCards a list that contains the selected cards
 * @property loader the CardImageLoader
 * @property pyramidX the pivot x value for the cards in the pyramid
 * @property pyramidY the pivot y value for the cards in the pyramid
 * @property scaleFactor the standard scale factor for the card images to make them smaller
 */
class PyramidGameScene(val rootService: RootService) : BoardGameScene(background = ColorVisual.ORANGE),
    Refreshable {

    val drawStack = CardStack<CardView>(100, 100)
    val reserveStack = CardStack<CardView>(900, 100)
    val pyramid = HashMap<Int, ArrayList<CardView>>()
    private val currentPlayerLabel =
        Label(300, 900, width = 3 * width, alignment = Alignment.TOP_LEFT, font = Font(26), text = "Current Player")
    private val passButton =
        Button(800, 900, text = "Pass", font = Font(fontWeight = Font.FontWeight.BOLD))
    private val selectedCards = ArrayList<Triple<CardView, Card, Int>>()
    val loader = CardImageLoader()
    private val util = PyramidGameUtil(this)
    val pyramidX = 500.0
    val pyramidY = 10.0
    val scaleFactor = 0.5

    init {
        buildUI()
    }

    /**
     * Builds the ui components after a new game has started.
     */
    private fun buildUI() {
        //Remove cards in previous game
        pyramid.clear()
        reserveStack.clear()
        drawStack.clear()

        util.buildDrawStack()
        util.buildPyramid()
        util.buildReserveStack()

        currentPlayerLabel.text = "Current Player: ${rootService.currentGame.currentPlayer.playerName}"
        passButton.visual = ColorVisual.CYAN

        clearComponents()
        addComponents(
            drawStack,
            reserveStack,
            currentPlayerLabel,
            passButton
        )
        //Adds the cards from the pyramid
        pyramid.forEach { it.value.forEach { addComponents(it) } }
        registerGameEvents()
    }

    /**
     * Creates a CardView for a given Card object.
     * @param card the given card from the entity layer
     */
    fun getCardViewFrom(card: Card): CardView {
        val frontVisual = ImageVisual(loader.frontImageFor(card.suit, card.value))
        val backVisual = ImageVisual(loader.backImage)
        val cardView = CardView(front = frontVisual, back = backVisual)
        cardView.scale(scaleFactor)
        return cardView
    }

    /**
     * Regists the event handler of the ui components.
     */
    private fun registerGameEvents() {
        drawStack.onMouseClicked = {
            if (drawStack.isNotEmpty())
                rootService.playerActionService.drawCard()
        }
        //For every card in the pyramid
        for (entry in pyramid)
            for (cardView in entry.value) {
                cardView.onMouseClicked = {
                    val index = entry.value.indexOf(cardView)
                    val card = rootService.currentGame.pyramid[entry.key]?.get(index)
                    //If the card is on border
                    if ((index == 0 || index == pyramid[entry.key]?.lastIndex) && card != null) {
                        //Select the card and add it to the list
                        highlightSelectedCard(cardView, -20)
                        //Just for null check
                        selectedCards.add(Triple(cardView, card, entry.key))
                        confirmSelectedPair()
                    }
                }
            }
        reserveStack.onMouseClicked = {
            if (reserveStack.isNotEmpty()) {
                //Because the ui stack has no any size method, proof if the card on the top is not blank.
                //If the top card is blank then the reserveStack does not contain any game card
                val topCard = reserveStack.pop()
                val topCardIsNotBlank = reserveStack.isNotEmpty()
                reserveStack.push(topCard)
                //If reserveStack contains at least a game card then select the top card
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

    /**
     * Triggers the removing of the selected pair.
     */
    private fun confirmSelectedPair() {
        if (selectedCards.size == 2) {
            rootService.playerActionService.removePair(selectedCards[0].second, selectedCards[1].second)
            selectedCards.clear()
        }
    }

    /**
     * Highlights the selected card with an animation
     * @param cardView the selected card
     * @param y the relative y position. On every animation follows an inverse animation and the y parameter determines
     * the direction on the y-axes.
     */
    private fun highlightSelectedCard(cardView: CardView, y: Int) {
        playAnimation(MovementAnimation(
            componentView = cardView,
            byY = y,
            duration = 250
        ).apply {
            cardView.posY += y
        })
    }

    /**
     * Refreshes the components after a new game has started.
     */
    override fun refreshAfterStartNewGame() {
        buildUI()
    }

    /**
     * Refreshes the components after a pair has been removed.
     */
    override fun refreshAfterRemovePair(cardsAreValid: Boolean) {
        for (cardTriple in selectedCards) {
            if (cardsAreValid) {
                //If the selected card is in the pyramid, remove it.
                if (!cardTriple.second.isReserveCard) {
                    removePyramidCard(cardTriple)
                } else {
                    //The selected card must be on the reserveStack, pop it.
                    reserveStack.pop()
                }
                //Reset the pass button
                passButton.visual = ColorVisual.CYAN
            } else {
                //If the first card is selected, highlight it.
                if (!cardTriple.second.isReserveCard)
                    highlightSelectedCard(cardTriple.first, 20)
            }
        }
    }

    /**
     * A private help function that removes the selected cards and reveals the neighbours.
     * @param cardTriple the selected card in a triple object
     */
    private fun removePyramidCard(cardTriple: Triple<CardView, Card, Int>) {
        removeComponents(cardTriple.first)
        //Determines the neighbours index
        val neighbourIndex = when (pyramid[cardTriple.third]?.indexOf(cardTriple.first)) {
            0 -> 1
            else -> pyramid[cardTriple.third]?.lastIndex?.minus(1)
        }
        //If the row is not empty then the neighbour musts exist and gets revealed
        if (neighbourIndex != null && pyramid[cardTriple.third]?.size != 1) {
            pyramid[cardTriple.third]?.get(neighbourIndex)?.showFront()
        }
        //Remove the selected card
        pyramid[cardTriple.third]?.remove(cardTriple.first)
    }

    /**
     * Refreshes the components after the player has drawn a card.
     */
    override fun refreshAfterDrawCard(stackNoptEmpty: Boolean) {
        if (stackNoptEmpty) {
            //without animation
//            val cardView = drawStack.pop()
//            cardView.showFront()
//            reserveStack.push(cardView)
            val card = drawStack.peek()
            //Play the animation
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
            //Resets the pass button
            passButton.visual = ColorVisual.CYAN
        }
    }

    /**
     * Refreshes the components after the player has passed.
     */
    override fun refreshAfterPass() {
        passButton.visual = ColorVisual.RED
    }

    /**
     * Refreshes the components after the current player has switched.
     */
    override fun refreshAfterSwitchPlayer() {
        currentPlayerLabel.text = "Current Player: ${rootService.currentGame.currentPlayer.playerName}"
    }
}