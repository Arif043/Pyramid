package service

import view.Refreshable
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Tests addRefreshables
 */
class TestAddRefreshables {

    /**
     * Tests if collection contains two elements
     */
    @Test
    fun test() {
        val rootService = RootService()
        val playerActionServiceRefreshablesSizeBefore = rootService.playerActionService.refreshable.size
        val gameServiceRefreshablesSizeBefore = rootService.gameService.refreshable.size
        rootService.addRefreshables(object : Refreshable {}, object : Refreshable {})
        assertEquals(playerActionServiceRefreshablesSizeBefore + 2, rootService.playerActionService.refreshable.size)
        assertEquals(gameServiceRefreshablesSizeBefore + 2, rootService.gameService.refreshable.size)
    }
}