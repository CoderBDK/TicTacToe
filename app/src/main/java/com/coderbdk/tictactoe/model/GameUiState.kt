package com.coderbdk.tictactoe.model

import com.coderbdk.tictactoe.engine.GameEngine

data class UiState(
    val gameState: GameEngine.GameState,
    val isCurrentPlayerTurn: Boolean,
    val states: Array<Array<Pair<Boolean, Boolean>>>,
    val gameWonIndex: Array<Array<Int>>,

    ) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UiState

        if (gameState != other.gameState) return false
        if (isCurrentPlayerTurn != other.isCurrentPlayerTurn) return false
        if (!states.contentDeepEquals(other.states)) return false
        if (!gameWonIndex.contentDeepEquals(other.gameWonIndex)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = gameState.hashCode()
        result = 31 * result + isCurrentPlayerTurn.hashCode()
        result = 31 * result + states.contentDeepHashCode()
        result = 31 * result + gameWonIndex.contentDeepHashCode()
        return result
    }
}
