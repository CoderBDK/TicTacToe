package com.coderbdk.tictactoe.viewmodel


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.coderbdk.tictactoe.data.repository.TicTacToeRepository
import com.coderbdk.tictactoe.engine.GameEngine
import com.coderbdk.tictactoe.model.UiState
import org.json.JSONObject

class OnlinePlayerViewModel() : ViewModel() {

    private val repository = TicTacToeRepository("ws://192.168.0.108:8080/")
    private val _gameState = MutableLiveData<String>()
    val gameState: LiveData<String> = _gameState

    private val _isConnected = MutableLiveData<Boolean>()
    val isConnected: LiveData<Boolean> = _isConnected

    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable> = _error

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


    init {
        connect()
    }


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
        }
    }

    private fun connect() {
        repository.connect(
            onMessageReceived = { json ->
                handleWebSocketMessage(json)
            },
            onConnectionOpened = {
                _isConnected.postValue(true)
            },
            onFailure = { throwable ->
                _error.postValue(throwable)
            }
        )
    }

    private fun handleWebSocketMessage(json: JSONObject) {
        when (json.getString("action")) {
            "roomCreated" -> {
                // Handle room creation
                _gameState.postValue("Room Created")
            }
            "startGame" -> {
                // Handle game start
                _gameState.postValue("Game Started")
            }
            "updateGameState" -> {
                // Handle game state update
                _gameState.postValue("Game State Updated")
                if(json.optBoolean("move")) {
                    updateGameState(json)
                }
            }
            "roomClosed" -> {
                // Handle room closure
                _gameState.postValue("Room Closed")
            }
        }
    }

    private fun updateGameState(json: JSONObject) {
        val i = json.optInt("i")
        val j = json.optInt("j")
        uiState.states[i][j] = Pair(true, false)
        gameEngine.checkGameState(uiState.states) { state, gameWonIdx ->
            uiState = uiState.copy(
                gameState = state,
                isCurrentPlayerTurn = true,
                states = uiState.states,
                gameWonIndex = gameWonIdx
            )
        }
    }

    fun createRoom() {
        repository.createRoom()
    }

    fun joinRoom(roomId: String) {
        repository.joinRoom(roomId)
    }

    fun makeMove(move: String) {
        repository.makeMove(move)
    }

    fun closeConnection() {
        repository.close()
    }

    fun resetGame() {
        uiState = uiState.copy(
            gameState = GameEngine.GameState.UNKNOWN,
            isCurrentPlayerTurn = true, // Player starts first again
            states = gameEngine.gameStatesInit
        )
    }
}