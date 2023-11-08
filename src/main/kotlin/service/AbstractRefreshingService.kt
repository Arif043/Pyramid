package service

import view.Refreshable

abstract class AbstractRefreshingService : Refreshable {

    private val refreshable = ArrayList<Refreshable>()

    var gameEnded = false //Just added for testing
    fun addRefreshable(newRefreshable: Refreshable) {
        refreshable.add(newRefreshable)
    }

    fun onAllRefreshable(method: Refreshable.() -> Unit) {
        refreshable.forEach(method)
    }

    override fun refreshAfterEndGame() {
        gameEnded = true
    }
}