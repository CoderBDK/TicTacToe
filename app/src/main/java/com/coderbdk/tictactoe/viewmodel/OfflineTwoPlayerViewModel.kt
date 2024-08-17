package com.coderbdk.tictactoe.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.coderbdk.tictactoe.engine.GameEngine
import com.coderbdk.tictactoe.model.UiState

class OfflineTwoPlayerViewModel : ViewModel() {

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