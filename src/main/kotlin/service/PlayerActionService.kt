package service

import entity.Card
import entity.CardValue

class PlayerActionService(private val rootService: RootService) : AbstractRefreshingService() {

    fun removePair(card1: Card, card2: Card) {
        if (!areCardsValid(card1, card2))
            return //refreshAfterRemovePair(false)

        val game = rootService.currentGame
        if (card1.value == CardValue.ACE || card2.value == CardValue.ACE)
            game.currentPlayer.score += 1
        else
            game.currentPlayer.score += 2

        if (game.pyramid.values.isEmpty())
            rootService.gameService.endGame()

        revealCard(card1)
        revealCard(card2)

        game.pyramid[card1.row]?.remove(card1)
        game.pyramid[card2.row]?.remove(card2)

        //refreshAfterRemovePair(true)
    }

    fun pass() {
        val game = rootService.currentGame
        game.currentPlayer.hasPressed = true
        val otherPlayer = if (game.currentPlayer == game.player1) game.player2 else game.player1
        if (otherPlayer.hasPressed)
            rootService.gameService.endGame()
        else
            switchCurrentPlayer()
    }

    fun drawCard() {
        val game = rootService.currentGame
        if (game.drawStack.isNotEmpty())
            game.reserveStack.push(game.drawStack.pop())
        else
            throw IllegalStateException("Drawstack is already empty")
    }

    fun switchCurrentPlayer() {
        val game = rootService.currentGame
        game.currentPlayer = if (game.currentPlayer == game.player1) game.player2 else game.player1
    }

    fun revealCard(card: Card) {
        val pyramid = rootService.currentGame.pyramid
        val index = pyramid[card.row]?.indexOf(card)
        val neighbourIndex = when (index) {
            0 -> 1
            pyramid[card.row]?.lastIndex -> pyramid[card.row]?.lastIndex?.minus(1)
            else -> throw IllegalArgumentException("Card is not on border")

        }
        if (neighbourIndex != null) {
            pyramid[card.row]?.get(neighbourIndex)?.revealed = true
        }
    }

    fun areCardsValid(card1: Card, card2: Card): Boolean {
        val isCard1Ace = card1.value == CardValue.ACE
        val isCard2Ace = card2.value == CardValue.ACE
        return !(isCard1Ace && isCard2Ace) && (isCard1Ace.xor(isCard2Ace) || card1.value() + card2.value() == 15)
    }
    // fun areCardsValid(card1: Card, card2: Card): Boolean =
    //   (!(card1.value == CardValue.ACE && card2.value == CardValue.ACE || card1.value() + card2.value() != 15))

    private fun Card.value() = CardValue.values().indexOf(this.value) + 2
}