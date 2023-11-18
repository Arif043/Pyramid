package service

import view.Refreshable

/**
 * Abstract refreshing service
 *
 * connects to the view layer
 * @property refreshable a list for the refreshables to be stored
 */
abstract class AbstractRefreshingService {

    val refreshable = mutableListOf<Refreshable>()

    /**
     * Adds a refreshable into the list
     *
     * @param newRefreshable The refreshable to be added
     */
    fun addRefreshable(newRefreshable: Refreshable) {
        refreshable += newRefreshable
    }

    /**
     * Runs a method on the refreshables
     *
     * @param method The overgivern function that is called on the refreshables
     */
    fun onAllRefreshable(method: Refreshable.() -> Unit) {
        refreshable.forEach(method)
    }
}