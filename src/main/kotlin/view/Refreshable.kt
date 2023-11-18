package view

/**
 * The interface implemented by all scenes.
 */
interface Refreshable {

    /**
     * Refreshes the scene after the game has ended.
     */
    fun refreshAfterEndGame() {}

    /**
     * Refreshes the scene after a new game has started.
     */
    fun refreshAfterStartNewGame() {}

    /**
     * Refreshes the scene after a selected pair has removed.
     */
    fun refreshAfterRemovePair(cardsAreValid: Boolean) {}

    /**
     * Refreshes the scene after the current player has drawn.
     */
    fun refreshAfterDrawCard(stackNoptEmpty: Boolean) {}

    /**
     * Refreshes the scene after the current player has passed.
     */
    fun refreshAfterPass() {}

    /**
     * Refreshes the scene after the current player switched.
     */
    fun refreshAfterSwitchPlayer() {}
}