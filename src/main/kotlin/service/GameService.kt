package service

import entity.*
import tools.aqua.bgw.util.Stack

/**
 * Central service for handling game's logic
 *
 * @property rootService the root service
 */
class GameService(private val rootService: RootService) : AbstractRefreshingService() {
    /**
     * starts the new game
     *
     * @param player1Name name of player 1
     * @param player2Name name of player 2
     */
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

    /**
     * Ends up the game
     *
     * determines which player has won and shows the end screen
     */
    fun endGame() {
        val game = rootService.currentGame
        val winner = if (game.player1.score > game.player2.score) game.player1 else game.player2
        refreshAfterEndGame()
    }

    /**
     * A private help function for the initialization
     *
     * @param pAllCards a list of all 52 cards
     * @param drawStack the drawstack which is empty
     * @param pyramid a map which represents the pyramid
     */
    private fun initCards(pAllCards: List<Card>, drawStack: Stack<Card>, pyramid: HashMap<Int, ArrayList<Card>>) {
        var allCards = pAllCards
        for (i in 0..6) {
            pyramid[i] = ArrayList()
            repeat(i + 1) {
                //Card is on border
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

    /**
     * generates a randomized deck of 52 cards
     */
    private fun defaultRandomCardList() = List(52) { index ->
        Card(
            CardSuit.values()[index / 13],
            CardValue.values()[index % 13]
        )
    }.shuffled()
}