package com.coderbdk.tictactoe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.coderbdk.tictactoe.engine.GameEngine
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
                        TTTScreen(viewModels<MainViewModel>().value)
                    }

                }
            }
        }
    }
}


@Composable
fun TTTScreen(viewModel: MainViewModel) {
    TTTScreen(
        uiState = viewModel.uiState,
        checkGameState = { i, j ->
            viewModel.checkGameState(i, j)
        }, resetGame = {
            viewModel.resetGame()
        })
}


@Composable
fun TTTScreen(
    uiState: MainViewModel.UiState,
    checkGameState: (Int, Int) -> Unit,
    resetGame: () -> Unit
) {

    val isGameWon =
        uiState.gameState == GameEngine.GameState.WON_PLAYER1 || uiState.gameState == GameEngine.GameState.WON_PLAYER2
    val isGameOver = isGameWon || uiState.gameState == GameEngine.GameState.TIE
    val firstIdx = uiState.gameWonIndex[0]
    val secondIdx = uiState.gameWonIndex[1]
    val thirdIdx = uiState.gameWonIndex[2]

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
                resetGame()
            }) {
            Text(text = "Reset")
        }

        Text(
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Start,
            fontFamily = FontFamily.Serif,
            letterSpacing = 1.5.sp,
            text = "Game State : ${uiState.gameState.name}"
        )
        if (isGameWon) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Start,
                fontFamily = FontFamily.Serif,
                text = "Winner - ${uiState.gameState.name.replace("WON_", "")}"
            )
        } else {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Start,
                fontFamily = FontFamily.Serif,
                text = "Current turn : " + if (!uiState.isCurrentPlayerTurn) "Player1(O)" else "Player2(X)"
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
                                    color = if (isGameWon && isWonIndexMatched) colorScheme.primary else if (uiState.states[i][j].first) if (uiState.states[i][j].second) colorScheme.surfaceVariant else colorScheme.secondaryContainer else colorScheme.outlineVariant,
                                    shape = RoundedCornerShape(4.dp)
                                )
                                .clickable {
                                    if (isGameOver) return@clickable
                                    checkGameState(i, j)
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            if (uiState.states[i][j].first) {
                                Text(
                                    text = if (uiState.states[i][j].second) "O" else "X",
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

@Preview(showBackground = true)
@Composable
fun TTTPreview() {
    TicTacToeTheme {
        TTTScreen(
            MainViewModel()
        )
    }
}