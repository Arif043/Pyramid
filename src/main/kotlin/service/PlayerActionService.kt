package service

import entity.Card
import entity.CardValue

/**
 * The central service for handling player action
 *
 * @property rootService the root service
 */
class PlayerActionService(private val rootService: RootService) : AbstractRefreshingService() {

    /**
     * Removes a pair of two cards if the pair is valid
     *
     * @param card1 the first card of the pair
     * @param card2 the second card of the pair
     */
    fun removePair(card1: Card, card2: Card) {
        if (!areCardsValid(card1, card2)) {
            onAllRefreshable { refreshAfterRemovePair(false) }
            return
        }

        val game = rootService.currentGame
        //one card is an ace
        if (card1.value == CardValue.ACE || card2.value == CardValue.ACE)
            game.currentPlayer.score += 1
        else
            game.currentPlayer.score += 2

        revealCard(card1)
        revealCard(card2)

        game.pyramid[card1.row]?.remove(card1)
        game.pyramid[card2.row]?.remove(card2)

        //counts the cards in pyramid
        var cardCount = 0
        game.pyramid.values.forEach { cardCount += it.size }
        if (cardCount == 0)
            onAllRefreshable { refreshAfterEndGame() }

        onAllRefreshable { refreshAfterRemovePair(true) }

        switchCurrentPlayer()
    }

    /**
     * the current player passes
     *
     * the current player has passed. The refrence switches to other player
     */
    fun pass() {
        val game = rootService.currentGame
        game.currentPlayer.hasPressed = true
        val otherPlayer = if (game.currentPlayer == game.player1) game.player2 else game.player1
        onAllRefreshable { refreshAfterPass() }
        if (otherPlayer.hasPressed)
            onAllRefreshable { refreshAfterEndGame() }
        else
            switchCurrentPlayer()
    }

    /**
     * draws a card from the drawstack
     *
     * @throws IllegalStateException if drawstack is empty an IllegalStateException will be thrown
     */
    fun drawCard() {
        val game = rootService.currentGame
        if (game.drawStack.isNotEmpty()) {
            val card = game.drawStack.pop()
            card.revealed = true
            game.reserveStack.push(card)
            onAllRefreshable { refreshAfterDrawCard(true) }
        } else {
            onAllRefreshable { refreshAfterDrawCard(false) }
            throw IllegalStateException("Drawstack is already empty")
        }
        switchCurrentPlayer()
    }

    /**
     * switches the current player
     *
     * The other player will be the new current player after this function has been executed
     */
    fun switchCurrentPlayer() {
        val game = rootService.currentGame
        game.currentPlayer = if (game.currentPlayer == game.player1) game.player2 else game.player1
        onAllRefreshable { refreshAfterSwitchPlayer() }
    }

    /**
     * reveals the neighbours of the card to be removed
     *
     * @throws IllegalArgumentException the card to be removed has to be on the border,
     * otherwise this exception will be thrown
     */
    fun revealCard(card: Card) {
        if (!card.isReserveCard) {
            val pyramid = rootService.currentGame.pyramid
            val index = pyramid[card.row]?.indexOf(card)
            //determines the neighbours index
            val neighbourIndex = when (index) {
                0 -> 1
                pyramid[card.row]?.lastIndex -> pyramid[card.row]?.lastIndex?.minus(1)
                else -> throw IllegalArgumentException("Card is not on border")
            }
            checkNotNull(neighbourIndex)
            if (pyramid[card.row]?.size != 1) {
                pyramid[card.row]?.get(neighbourIndex)?.revealed = true
            }
        }
    }

    /**
     * checks the validity of a pair
     *
     * if both are aces or the sum isnt 15 then the pair is not valid
     */
    fun areCardsValid(card1: Card, card2: Card): Boolean {
        val isCard1Ace = card1.value == CardValue.ACE
        val isCard2Ace = card2.value == CardValue.ACE
        return /*!(isCard1Ace && isCard2Ace) &&*/ (isCard1Ace.xor(isCard2Ace) || card1.value() + card2.value() == 15)
    }

    /**
     * determines the value of cardvalue
     */
    private fun Card.value() = CardValue.values().indexOf(this.value) + 2
}