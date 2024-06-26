package view

import tools.aqua.bgw.animation.MovementAnimation
import tools.aqua.bgw.components.gamecomponentviews.CardView
import tools.aqua.bgw.visual.ImageVisual

/**
 * A help class for the main game scene.
 * Provides ui help functions.
 */
class PyramidGameUtil(private val gameScene: PyramidGameScene) {

    /**
     * Builds the ui stack and projects the drawStack in the entity layer.
     */
    fun buildDrawStack() {
        val drawStackCards = gameScene.rootService.currentGame.drawStack.peekAll()
        for (i in drawStackCards.lastIndex downTo 0) {
            gameScene.drawStack.add(gameScene.getCardViewFrom(drawStackCards[i]))
        }
    }

    /**
     * Builds the ui pyramid and projects the pyramid in the entity layer.
     */
    fun buildPyramid() {
        //Stores the relative additional values for the cards
        var deltaPyramidX = gameScene.pyramidX
        var deltaPyramidY = gameScene.pyramidY
        val cardWidth = gameScene.drawStack.peek().actualWidth
        val cardHeight = gameScene.drawStack.peek().actualHeight

        //Loops through the pyramid
        for (entry in gameScene.rootService.currentGame.pyramid) {
            gameScene.pyramid[entry.key] = ArrayList()
            for (card in entry.value) {
                val cardView = gameScene.getCardViewFrom(card)
                //If the card is on border then flip it
                if (entry.value.indexOf(card) == 0 || entry.value.indexOf(card) == entry.value.lastIndex)
                    cardView.showFront()

                //Set the relative positon
                cardView.posX = deltaPyramidX
                cardView.posY = deltaPyramidY

                gameScene.pyramid[entry.key]?.add(cardView)

                //Set the new x position for next card in this row
                deltaPyramidX += 2 * cardView.actualWidth
            }
            //Set the first x position for the next row
            deltaPyramidX = gameScene.pyramidX - (entry.key + 1) * cardWidth
            //Set the y position for the next row
            deltaPyramidY += cardHeight
        }
    }

    /**
     * Builds the ui stack and projects the reserveStack in the entity layer.
     */
    fun buildReserveStack() {
        //Create an empty blank card
        val blankCardView = CardView(front = ImageVisual(gameScene.loader.blankImage))
        blankCardView.scale(gameScene.scaleFactor)
        gameScene.reserveStack.push(blankCardView)
    }

    /**
     * Triggers the removing of the selected pair.
     */
    fun confirmSelectedPair() {
        if (gameScene.selectedCards.size == 2) {
            gameScene.rootService.playerActionService.removePair(
                gameScene.selectedCards[0].second,
                gameScene.selectedCards[1].second
            )
            gameScene.selectedCards.clear()
        }
    }

    /**
     * Resets the selected card back
     */
    fun clearSelectedPair() {
        for (pair in gameScene.selectedCards) {
            if (!pair.second.isReserveCard) {
                highlightSelectedCard(pair.first, 20)
            }
        }
        gameScene.selectedCards.clear()
    }

    /**
     * Highlights the selected card with an animation
     * @param cardView the selected card
     * @param y the relative y position. On every animation follows an inverse animation and the y parameter determines
     * the direction on the y-axes.
     */
    fun highlightSelectedCard(cardView: CardView, y: Int) {
        gameScene.playAnimation(
            MovementAnimation(
                componentView = cardView,
                byY = y,
                duration = 250
            ).apply {
                cardView.posY += y
            })
    }
}