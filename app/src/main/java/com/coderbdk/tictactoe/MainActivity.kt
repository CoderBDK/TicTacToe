package com.coderbdk.tictactoe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.coderbdk.tictactoe.ui.theme.TicTacToeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TicTacToeTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                ) { innerPadding ->
                    Box(
                        Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        TTTScreen()
                    }

                }
            }
        }
    }
}

enum class GameState {
    UNKNOWN, TIE, WON_PLAYER1, WON_PLAYER2
}

private val gameStatesInit
    get() = Array(3) {
        Array(3) {
            Pair(false, false)
        }
    }
private val gameWonIndexInit
    get() = Array(3) {
        Array(2) { 0 }
    }

@Composable
fun TTTScreen() {

    var gameState by remember {
        mutableStateOf(GameState.UNKNOWN)
    }
    var isCurrentPlayerTurn by remember {
        mutableStateOf(false)
    }
    var states by remember {
        mutableStateOf(
            gameStatesInit
        )
    }
    var gameWonIndex by remember {
        mutableStateOf(
            Array(3) {
                Array(2) { 0 }
            }
        )
    }

    val isGameWon = gameState == GameState.WON_PLAYER1 || gameState == GameState.WON_PLAYER2
    val isGameOver = isGameWon || gameState == GameState.TIE
    val firstIdx = gameWonIndex[0]
    val secondIdx = gameWonIndex[1]
    val thirdIdx = gameWonIndex[2]

    Column(
        modifier = Modifier
            .padding(8.dp)
            .size(300.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = {
                // isCurrentPlayerTurn = false
                gameState = GameState.UNKNOWN
                states = gameStatesInit
            }) {
            Text(text = "Reset")
        }

        Text(
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Start,
            fontFamily = FontFamily.Serif,
            letterSpacing = 1.5.sp,
            text = "Game State : ${gameState.name}"
        )
        if (isGameWon) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Start,
                fontFamily = FontFamily.Serif,
                text = "Winner - ${gameState.name.replace("WON_", "")}"
            )
        } else {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Start,
                fontFamily = FontFamily.Serif,
                text = "Current turn : " + if (!isCurrentPlayerTurn) "Player1(O)" else "Player2(X)"
            )
        }


        Column(
            modifier = Modifier
                .padding(top = 8.dp)
                .shadow(
                    elevation = 4.dp
                )
                .background(
                    color = colorScheme.surfaceContainerLow,
                    shape = RoundedCornerShape(4.dp)
                )
                .padding(4.dp)
        ) {
            for (i in 0 until 3) {
                Row {
                    for (j in 0 until 3) {
                        val isWonIndexMatched =
                            firstIdx[0] == i && firstIdx[1] == j ||
                                    secondIdx[0] == i && secondIdx[1] == j ||
                                    thirdIdx[0] == i && thirdIdx[1] == j

                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .padding(1.dp)
                                .border(
                                    width = 1.dp,
                                    color = colorScheme.outlineVariant,
                                    shape = RoundedCornerShape(4.dp)
                                )
                                .background(
                                    color = if (isGameWon && isWonIndexMatched) colorScheme.primary else if (states[i][j].first) if (states[i][j].second) colorScheme.surfaceVariant else colorScheme.secondaryContainer else colorScheme.outlineVariant,
                                    shape = RoundedCornerShape(4.dp)
                                )
                                .clickable {
                                    if (isGameOver) return@clickable
                                    if (!states[i][j].first) {
                                        states[i][j] = Pair(true, !isCurrentPlayerTurn)
                                        isCurrentPlayerTurn = !isCurrentPlayerTurn
                                    }
                                    checkGameState(states) { state, gameWonIdx ->
                                        gameState = state
                                        gameWonIndex = gameWonIdx
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            if (states[i][j].first) {
                                Text(
                                    text = if (states[i][j].second) "O" else "X",
                                    textAlign = TextAlign.Center,
                                    fontFamily = FontFamily.Serif,
                                    color = if (isGameWon && isWonIndexMatched) colorScheme.onPrimary else colorScheme.onPrimaryContainer,
                                    fontSize = 24.sp
                                )
                            }

                        }
                    }
                }

            }
        }
    }
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
    // checked tie
    if (states[0][0].first && states[0][1].first && states[0][2].first &&
        states[1][0].first && states[1][1].first && states[1][2].first &&
        states[2][0].first && states[2][1].first && states[2][2].first
    ) {
        onGameStateChange(GameState.TIE, gameWonIndexInit)
        return
    }
    onGameStateChange(GameState.UNKNOWN, gameWonIndexInit)
}

@Preview(showBackground = true)
@Composable
fun TTTPreview() {
    TicTacToeTheme {
        TTTScreen()
    }
}