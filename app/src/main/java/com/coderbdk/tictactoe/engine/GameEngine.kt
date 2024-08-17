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
    ): GameState {
        // check horizontal
        for (i in 0 until 3) {
            val isChecked = states[i][0].first && states[i][1].first && states[i][2].first
            if (isChecked) {
                val gameStateWonIndex = arrayOf(
                    arrayOf(i, 0), arrayOf(i, 1), arrayOf(i, 2)
                )
                if (states[i][0].second && states[i][1].second && states[i][2].second) {
                    onGameStateChange(GameState.WON_PLAYER1, gameStateWonIndex)
                    return GameState.WON_PLAYER1
                } else if (!states[i][0].second && !states[i][1].second && !states[i][2].second) {
                    onGameStateChange(GameState.WON_PLAYER2, gameStateWonIndex)
                    return GameState.WON_PLAYER2
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
                    return GameState.WON_PLAYER1
                } else if (!states[0][i].second && !states[1][i].second && !states[2][i].second) {
                    onGameStateChange(GameState.WON_PLAYER2, gameStateWonIndex)
                    return GameState.WON_PLAYER2
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
                return GameState.WON_PLAYER1
            } else if (!states[0][0].second && !states[1][1].second && !states[2][2].second) {
                onGameStateChange(GameState.WON_PLAYER2, gameStateWonIndex)
                return GameState.WON_PLAYER2
            }
        }

        val isDiagonalCheckSecond = states[0][2].first && states[1][1].first && states[2][0].first
        if (isDiagonalCheckSecond) {
            val gameStateWonIndex = arrayOf(
                arrayOf(0, 2), arrayOf(1, 1), arrayOf(2, 0)
            )
            if (states[0][2].second && states[1][1].second && states[2][0].second) {
                onGameStateChange(GameState.WON_PLAYER1, gameStateWonIndex)
                return GameState.WON_PLAYER1
            } else if (!states[0][2].second && !states[1][1].second && !states[2][0].second) {
                onGameStateChange(GameState.WON_PLAYER2, gameStateWonIndex)
                return GameState.WON_PLAYER2
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
            return GameState.TIE
        }
        onGameStateChange(
            GameState.UNKNOWN,
            gameWonIndexInit
        )
        return GameState.UNKNOWN
    }


    fun generateComputerMove(
        states: Array<Array<Pair<Boolean, Boolean>>>
    ): Pair<Int, Int>? {
        var bestMove: Pair<Int, Int>? = null
        var bestValue = Int.MIN_VALUE

        for (i in states.indices) {
            for (j in states[i].indices) {
                if (!states[i][j].first) {
                    // Make the move
                    states[i][j] = Pair(true, false)

                    // Get the move value
                    val moveValue = minimax(states, false)

                    // Undo the move
                    states[i][j] = Pair(false, false)

                    // Update the best move
                    if (moveValue > bestValue) {
                        bestValue = moveValue
                        bestMove = Pair(i, j)
                    }
                }
            }
        }

        return bestMove
    }

    private fun minimax(
        states: Array<Array<Pair<Boolean, Boolean>>>,
        isMaximizing: Boolean
    ): Int {
        val gameState = evaluateGameState(states)
        if (gameState != GameState.UNKNOWN) {
            return when (gameState) {
                GameState.WON_PLAYER1 -> -10
                GameState.WON_PLAYER2 -> 10
                GameState.TIE -> 0
                else -> 0
            }
        }

        if (isMaximizing) {
            var bestValue = Int.MIN_VALUE
            for (i in states.indices) {
                for (j in states[i].indices) {
                    if (!states[i][j].first) {
                        // Make the move
                        states[i][j] = Pair(true, false)

                        // Get the move value
                        val value = minimax(states, false)

                        // Undo the move
                        states[i][j] = Pair(false, false)

                        bestValue = maxOf(bestValue, value)
                    }
                }
            }
            return bestValue
        } else {
            var bestValue = Int.MAX_VALUE
            for (i in states.indices) {
                for (j in states[i].indices) {
                    if (!states[i][j].first) {
                        // Make the move
                        states[i][j] = Pair(true, true)

                        // Get the move value
                        val value = minimax(states, true)

                        // Undo the move
                        states[i][j] = Pair(false, false)

                        bestValue = minOf(bestValue, value)
                    }
                }
            }
            return bestValue
        }
    }

    private fun evaluateGameState(states: Array<Array<Pair<Boolean, Boolean>>>): GameState {

        return checkGameState(states) { _,_ -> }
    }
}