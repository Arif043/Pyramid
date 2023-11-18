package service

import view.Refreshable
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Tests addRefreshable
 */
class TestAddRefreshable {

    /**
     * Tests if collection contains one element
     */
    @Test
    fun test() {
        val rootService = RootService()
        val playerActionServiceRefreshablesSizeBefore = rootService.playerActionService.refreshable.size
        val gameServiceRefreshablesSizeBefore = rootService.gameService.refreshable.size
        rootService.addRefreshable(object : Refreshable {})
        assertEquals(playerActionServiceRefreshablesSizeBefore + 1, rootService.playerActionService.refreshable.size)
        assertEquals(gameServiceRefreshablesSizeBefore + 1, rootService.gameService.refreshable.size)
    }
}