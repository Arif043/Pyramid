package service

import entity.*
import tools.aqua.bgw.util.Stack

class GameService(private val rootService: RootService) : AbstractRefreshingService() {

    fun startNewGame(player1Name: String, player2Name: String) {
        val allCards = defaultRandomCardList()
        val drawStack = Stack<Card>()
        val pyramid = HashMap<Int, ArrayList<Card>>()
        val player1 = Player(player1Name)
        val player2 = Player(player2Name)

        initCards(allCards, drawStack, pyramid)
        val game = PyramidGame(
            drawStack,
            Stack(),
            pyramid,
            player1,
            player2,
            if ((0..1).random() == 0) player1 else player2
        )
        rootService.currentGame = game
        onAllRefreshable { /*refreshAfterStartGame()*/ }
    }

    fun endGame() {
        val game = rootService.currentGame
        val winner = if (game.player1.score > game.player2.score) game.player1 else game.player2
        refreshAfterEndGame()
    }

    private fun initCards(pAllCards: List<Card>, drawStack: Stack<Card>, pyramid: HashMap<Int, ArrayList<Card>>) {
        var allCards = pAllCards
        for (i in 0..6) {
            pyramid[i] = ArrayList()
            repeat(i + 1) {
                if (it == 0 || it == i)
                    allCards[allCards.lastIndex].revealed = true
                allCards[allCards.lastIndex].row = i
                pyramid[i]?.add(allCards[allCards.lastIndex])
                allCards = allCards.subList(0, allCards.lastIndex)
            }
        }
        allCards.forEach { it.isReserveCard = true }
        drawStack.pushAll(allCards)
    }

    private fun defaultRandomCardList() = List(52) { index ->
        Card(
            CardSuit.values()[index / 13],
            CardValue.values()[index % 13]
        )
    }.shuffled()
}