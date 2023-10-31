package service

import view.Refreshable

abstract class AbstractRefreshingService {
    fun addRefreshable(newRefreshable: Refreshable) {

    }

    fun onAllRefreshable(method: Refreshable.() -> Unit) {

    }
}