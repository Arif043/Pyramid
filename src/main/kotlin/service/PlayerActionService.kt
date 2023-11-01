package service

import entity.Card
import entity.CardValue

class PlayerActionService(private val rootService: RootService) : AbstractRefreshingService() {

    fun removePair(card1: Card, card2: Card) {
        if (!areCardsValid(card1, card2))
        ;//refreshAfterRemovePair(false)

        val game = rootService.currentGame
        if (card1.value == CardValue.ACE || card2.value == CardValue.ACE)
            game.currentPlayer.score += 1
        else
            game.currentPlayer.score += 2

        if (game.pyramid.values.isEmpty())
            rootService.gameService.endGame()

        revealCard(card1)
        revealCard(card2)

        game.currentPlayer.hasPressed = false
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
    }

    private fun switchCurrentPlayer() {
        val game = rootService.currentGame
        game.currentPlayer = if (game.currentPlayer == game.player1) game.player2 else game.player1
    }

    private fun revealCard(card: Card) {
        val pyramid = rootService.currentGame.pyramid
        val index = pyramid[card.row]?.indexOf(card)
        val neighbourIndex = if (index == 0) 1 else pyramid[card.row]?.lastIndex?.minus(1)
        if (neighbourIndex != null) {
            pyramid[card.row]?.get(neighbourIndex)?.revealed = true
        }
        pyramid[card.row]?.remove(card)
    }

    private fun areCardsValid(card1: Card, card2: Card): Boolean =
        (!(card1.value == CardValue.ACE && card2.value == CardValue.ACE || card1.value() + card2.value() != 15))

    private fun Card.value() = CardValue.values().indexOf(this.value) + 2
}