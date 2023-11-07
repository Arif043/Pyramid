package service

import entity.Card
import entity.CardSuit
import entity.CardValue
import tools.aqua.bgw.util.Stack
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Tests the removePair function PlayerActionService
 * @property root the root service
 */
class TestRemovePair {

    lateinit var root: RootService

    /**
     * Tests with only one ace and looks up how many points we get and if the row decreases by two
     *
     * If there is no ace in the pair, so we get two points and the both cards in the pyramid get remove.
     * The row decreases by 2
     */
    @Test
    fun testWithoutAce() {
        root = RootService()
        root.gameService.startNewGame("Alice", "Bob")

        val card1 = Card(CardSuit.SPADES, CardValue.SEVEN)
        val card2 = Card(CardSuit.CLUBS, CardValue.EIGHT)
        card1.revealed = true
        card1.row = 6
        card2.revealed = true
        card2.row = 6
        swapCards(card1, 6, 0)
        swapCards(card2, 6, 6)

        val currentPlayer = root.currentGame.currentPlayer
        val rowSize = root.currentGame.pyramid[6]!!.size
        val scoreBefore = currentPlayer.score

        root.playerActionService.removePair(card1, card2)

        assertEquals(scoreBefore + 2, currentPlayer.score)
        assertEquals(rowSize - 2, root.currentGame.pyramid[6]?.size)
    }

    /**
     * Tests with only one ace and looks up how many points we get and if the row decreases by two
     *
     * If one card is an ace, so we get one point and the both cards in the pyramid get remove.
     * The row decreases by 2
     */
    @Test
    fun testWithAce() {
        root = RootService()
        root.gameService.startNewGame("Alice", "Bob")
        val card1 = Card(CardSuit.SPADES, CardValue.SEVEN)
        val card2 = Card(CardSuit.CLUBS, CardValue.ACE)
        card1.revealed = true
        card1.row = 6
        card2.revealed = true
        card2.row = 6
        swapCards(card1, 6, 0)
        swapCards(card2, 6, 6)

        val currentPlayer = root.currentGame.currentPlayer
        val rowSize = root.currentGame.pyramid[6]!!.size
        val scoreBefore = currentPlayer.score

        root.playerActionService.removePair(card1, card2)

        assertEquals(scoreBefore + 1, currentPlayer.score)
        assertEquals(rowSize - 2, root.currentGame.pyramid[6]?.size)
    }

    /**
     * Tests with only aces and looks up how many points we get and if the row decreases by two
     *
     * If there is no ace in the pair, so we get zero points and both cards in the pyramid get not removed.
     * The row does not decrease
     */
    @Test
    fun testOnlyAces() {
        root = RootService()
        root.gameService.startNewGame("Alice", "Bob")
        val card1 = Card(CardSuit.SPADES, CardValue.ACE)
        val card2 = Card(CardSuit.CLUBS, CardValue.ACE)
        card1.revealed = true
        card1.row = 6
        card2.revealed = true
        card2.row = 6
        swapCards(card1, 6, 0)
        swapCards(card2, 6, 6)

        val currentPlayer = root.currentGame.currentPlayer
        val rowSize = root.currentGame.pyramid[6]!!.size
        val scoreBefore = currentPlayer.score

        root.playerActionService.removePair(card1, card2)

        assertEquals(scoreBefore, currentPlayer.score)
        assertEquals(rowSize, root.currentGame.pyramid[6]?.size)
    }

    /**
     * A useful private function. swap two given cards in the game
     *
     * @param pivotCard A card with the characteristics that we want to swap
     * @param card2Row The row in the pyramid that contains the second card
     * @param card2ListIndex The index of the second card in the row we want to swap
     *
     * Firstly we look up in the drawStack to find the first card with the given characteristics
     * If we dont find it then we search it in pyramid. The cards get swapped with their position and properties
     */
    private fun swapCards(pivotCard: Card, card2Row: Int, card2ListIndex: Int) {
        val game = root.currentGame
        var card1 = pivotCard //Just for initialization
        val card2 = game.pyramid[card2Row]?.get(card2ListIndex)
        //Search pivot card in drawstack
        val drawStackIndex = game.drawStack.indexOf(pivotCard)
        if (drawStackIndex != -1) {
            val tempStack = Stack<Card>()
            while (game.drawStack.peek() != pivotCard)
                tempStack.push(game.drawStack.pop())
            card1 = game.drawStack.pop()
            //Push card2 into the drawstack
            game.drawStack.push(card2!!)
            //Push the other cards back
            while (tempStack.isNotEmpty())
                game.drawStack.push(tempStack.pop())
        }
        //Search pivot card in pyramid
        else {
            var listIndexOfCard1 = -1
            loop@ for (row in game.pyramid)
                for (pyramidCard in row.value)
                    if (pyramidCard == pivotCard) {
                        card1 = pyramidCard
                        listIndexOfCard1 = row.value.indexOf(pyramidCard)
                        break@loop
                    }
            //Move card2 to the place of card1
            game.pyramid[card1.row]?.set(listIndexOfCard1, card2!!)
        }
        //Move card1 to the place of card2
        game.pyramid[card2Row]?.set(card2ListIndex, card1)
        //Swap properties
        val tmpRow = card1.row
        val tmpIsReservecard = card1.isReserveCard
        val tmpReveleaded = card1.revealed
        card1.row = card2!!.row
        card1.isReserveCard = card2.isReserveCard
        card1.revealed = card2.revealed
        card2.row = tmpRow
        card2.isReserveCard = tmpIsReservecard
        card2.revealed = tmpReveleaded
    }
}