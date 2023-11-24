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
 * @property drawStackAnimationIsRunning true if a drawn card is still animating, false otherwise
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
    val selectedCards = ArrayList<Pair<CardView, Card>>()
    val loader = CardImageLoader()
    private val util = PyramidGameUtil(this)
    val pyramidX = 500.0
    val pyramidY = 10.0
    val scaleFactor = 0.5
    private var drawStackAnimationIsRunning = false

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
            if (drawStack.isNotEmpty() && !drawStackAnimationIsRunning) {
                drawStackAnimationIsRunning = true
                rootService.playerActionService.drawCard()
            }
        }
        //For every card in the pyramid
        for (entry in pyramid)
            for (cardView in entry.value) {
                cardView.onMouseClicked = {
                    val index = entry.value.indexOf(cardView)
                    val card = rootService.currentGame.pyramid[entry.key]?.get(index)
                    checkNotNull(card)
                    //If the card is on border
                    if ((index == 0 || index == pyramid[entry.key]?.lastIndex)) {
                        //Select the card and add it to the list
                        util.highlightSelectedCard(cardView, -20)
                        selectedCards.add(Pair(cardView, card))
                        util.confirmSelectedPair()
                    }
                }
            }
        reserveStack.onMouseClicked = {
                //If reserveStack contains at least a game card then select the top card
            if (reserveStack.numberOfComponents() > 1) {
                    selectedCards += Pair(reserveStack.peek(), rootService.currentGame.reserveStack.peek())
                    util.confirmSelectedPair()
            }
        }
        passButton.onMouseClicked = {
            rootService.playerActionService.pass()
        }
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
        if (cardsAreValid) {
            for (pair in selectedCards) {
                //If the selected card is in the pyramid, remove it.
                if (!pair.second.isReserveCard) {
                    removePyramidCard(pair)
                } else {
                    //The selected card must be on the reserveStack, pop it.
                    reserveStack.pop()
                }
                //Reset the pass button
                passButton.visual = ColorVisual.CYAN
            }
        } else
            util.clearSelectedPair()
    }

    /**
     * A private help function that removes the selected cards and reveals the neighbours.
     * @param pair the selected card in a triple object
     */
    private fun removePyramidCard(pair: Pair<CardView, Card>) {
        removeComponents(pair.first)
        //Determines the neighbours index
        val neighbourIndex = when (pyramid[pair.second.row]?.indexOf(pair.first)) {
            0 -> 1
            else -> pyramid[pair.second.row]?.lastIndex?.minus(1)
        }
        checkNotNull(neighbourIndex)
        //If the row is not empty then the neighbour musts exist and gets revealed
        if (pyramid[pair.second.row]?.size != 1) {
            pyramid[pair.second.row]?.get(neighbourIndex)?.showFront()
        }
        //Remove the selected card
        pyramid[pair.second.row]?.remove(pair.first)
    }

    /**
     * Refreshes the components after the player has drawn a card.
     */
    override fun refreshAfterDrawCard(stackNoptEmpty: Boolean) {
        if (stackNoptEmpty) {
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
                        drawStackAnimationIsRunning = false
                    }
                }
            ))
            //Resets the pass button
            passButton.visual = ColorVisual.CYAN
            util.clearSelectedPair()
        }
    }

    /**
     * Refreshes the components after the player has passed.
     */
    override fun refreshAfterPass() {
        passButton.visual = ColorVisual.RED
        util.clearSelectedPair()
    }

    /**
     * Refreshes the components after the current player has switched.
     */
    override fun refreshAfterSwitchPlayer() {
        currentPlayerLabel.text = "Current Player: ${rootService.currentGame.currentPlayer.playerName}"
    }
}