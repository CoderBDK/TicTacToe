package com.coderbdk.tictactoe.engine


class GameEngine {
    enum class GameState {
        UNKNOWN, TIE, WON_PLAYER1, WON_PLAYER2
    }

    val gameStatesInit
        get() = Array(3) {
            Array(3) {
                Pair(false, false)
            }
        }

    val gameWonIndexInit
        get() = Array(3) {
            Array(2) { 0 }
        }


    fun checkGameState(
        states: Array<Array<Pair<Boolean, Boolean>>>,
        onGameStateChange: (GameState, Array<Array<Int>>) -> Unit
    ) {
        // check horizontal
        for (i in 0 until 3) {
            val isChecked = states[i][0].first && states[i][1].first && states[i][2].first
            if (isChecked) {
                val gameStateWonIndex = arrayOf(
                    arrayOf(i, 0), arrayOf(i, 1), arrayOf(i, 2)
                )
                if (states[i][0].second && states[i][1].second && states[i][2].second) {
                    onGameStateChange(GameState.WON_PLAYER1, gameStateWonIndex)
                    return
                } else if (!states[i][0].second && !states[i][1].second && !states[i][2].second) {
                    onGameStateChange(GameState.WON_PLAYER2, gameStateWonIndex)
                    return
                }
            }
        }
        // check vertical
        for (i in 0 until 3) {
            val isChecked = states[0][i].first && states[1][i].first && states[2][i].first
            if (isChecked) {
                val gameStateWonIndex = arrayOf(
                    arrayOf(0, i), arrayOf(1, i), arrayOf(2, i)
                )
                if (states[0][i].second && states[1][i].second && states[2][i].second) {
                    onGameStateChange(GameState.WON_PLAYER1, gameStateWonIndex)
                    return
                } else if (!states[0][i].second && !states[1][i].second && !states[2][i].second) {
                    onGameStateChange(GameState.WON_PLAYER2, gameStateWonIndex)
                    return
                }
            }
        }
        // check diagonal
        val isDiagonalCheckFirst = (states[0][0].first && states[1][1].first && states[2][2].first)
        if (isDiagonalCheckFirst) {
            val gameStateWonIndex = arrayOf(
                arrayOf(0, 0), arrayOf(1, 1), arrayOf(2, 2)
            )
            if (states[0][0].second && states[1][1].second && states[2][2].second) {
                onGameStateChange(GameState.WON_PLAYER1, gameStateWonIndex)
                return
            } else if (!states[0][0].second && !states[1][1].second && !states[2][2].second) {
                onGameStateChange(GameState.WON_PLAYER2, gameStateWonIndex)
                return
            }
        }

        val isDiagonalCheckSecond = states[0][2].first && states[1][1].first && states[2][0].first
        if (isDiagonalCheckSecond) {
            val gameStateWonIndex = arrayOf(
                arrayOf(0, 2), arrayOf(1, 1), arrayOf(2, 0)
            )
            if (states[0][2].second && states[1][1].second && states[2][0].second) {
                onGameStateChange(GameState.WON_PLAYER1, gameStateWonIndex)
                return
            } else if (!states[0][2].second && !states[1][1].second && !states[2][0].second) {
                onGameStateChange(GameState.WON_PLAYER2, gameStateWonIndex)
                return
            }
        }
        // check tie
        if (states[0][0].first && states[0][1].first && states[0][2].first &&
            states[1][0].first && states[1][1].first && states[1][2].first &&
            states[2][0].first && states[2][1].first && states[2][2].first
        ) {
            onGameStateChange(
                GameState.TIE,
                gameWonIndexInit
            )
            return
        }
        onGameStateChange(
            GameState.UNKNOWN,
            gameWonIndexInit
        )
    }

}