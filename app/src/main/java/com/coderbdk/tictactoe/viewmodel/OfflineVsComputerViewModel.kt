package com.coderbdk.tictactoe.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.coderbdk.tictactoe.engine.GameEngine
import com.coderbdk.tictactoe.model.UiState

class OfflineVsComputerViewModel : ViewModel() {

    private val gameEngine = GameEngine()

    var uiState by mutableStateOf(
        UiState(
            gameState = GameEngine.GameState.UNKNOWN,
            isCurrentPlayerTurn = true, // Player starts first
            states = gameEngine.gameStatesInit,
            gameWonIndex = gameEngine.gameWonIndexInit
        )
    )
        private set

    fun checkGameState(i: Int, j: Int) {
        if (!uiState.states[i][j].first) {
            // Player move
            uiState.states[i][j] = Pair(true, true)
            gameEngine.checkGameState(uiState.states) { state, gameWonIdx ->
                uiState = uiState.copy(
                    gameState = state,
                    isCurrentPlayerTurn = false,
                    states = uiState.states,
                    gameWonIndex = gameWonIdx
                )
            }

            // If game is still ongoing, make a move for the computer
            if (uiState.gameState == GameEngine.GameState.UNKNOWN) {
                val computerMove = gameEngine.generateComputerMove(uiState.states)
                computerMove?.let {
                    uiState.states[it.first][it.second] = Pair(true, false)
                    gameEngine.checkGameState(uiState.states) { state, gameWonIdx ->
                        uiState = uiState.copy(
                            gameState = state,
                            isCurrentPlayerTurn = true,
                            states = uiState.states,
                            gameWonIndex = gameWonIdx
                        )
                    }
                }
            }
        }
    }

    fun resetGame() {
        uiState = uiState.copy(
            gameState = GameEngine.GameState.UNKNOWN,
            isCurrentPlayerTurn = true, // Player starts first again
            states = gameEngine.gameStatesInit
        )
    }
}
