package com.coderbdk.tictactoe

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.coderbdk.tictactoe.engine.GameEngine


class MainViewModel : ViewModel() {

    private val gameEngine = GameEngine()

    var uiState by mutableStateOf(
        UiState(
            gameState = GameEngine.GameState.UNKNOWN,
            isCurrentPlayerTurn = false,
            states = gameEngine.gameStatesInit,
            gameWonIndex = gameEngine.gameWonIndexInit
        )
    )
        private set


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


    fun checkGameState(i: Int, j: Int) {
        if (!uiState.states[i][j].first) {
            uiState.states[i][j] = Pair(true, !uiState.isCurrentPlayerTurn)

            gameEngine.checkGameState(uiState.states) { state, gameWonIdx ->
                uiState = uiState.copy(
                    gameState = state,
                    isCurrentPlayerTurn = !uiState.isCurrentPlayerTurn,
                    states = uiState.states,
                    gameWonIndex = gameWonIdx
                )
            }
        }

    }

    fun resetGame() {
        uiState = uiState.copy(
            gameState = GameEngine.GameState.UNKNOWN,
            states = gameEngine.gameStatesInit
        )
    }
}