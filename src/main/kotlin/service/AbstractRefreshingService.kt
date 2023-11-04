package service

import view.Refreshable

abstract class AbstractRefreshingService : Refreshable {

    val refreshable = ArrayList<Refreshable>()

    var gameEnded = false //Just added for testing
    fun addRefreshable(newRefreshable: Refreshable) {
        refreshable.add(newRefreshable)
    }

    fun onAllRefreshable(method: Refreshable.() -> Unit) {

    }

    override fun refreshAfterEndGame() {
        gameEnded = true
    }
}