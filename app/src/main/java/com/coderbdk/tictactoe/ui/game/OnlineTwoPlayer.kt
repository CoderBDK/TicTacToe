package com.coderbdk.tictactoe.ui.game

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.coderbdk.tictactoe.compose.TTTScreen
import com.coderbdk.tictactoe.ui.theme.TicTacToeTheme
import com.coderbdk.tictactoe.viewmodel.OfflineVsComputerViewModel
import com.coderbdk.tictactoe.viewmodel.OnlinePlayerViewModel

@Composable
fun OnlineTwoPlayerScreen(viewModel: OnlinePlayerViewModel) {
    Box(
        Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        TTTScreen(
            uiState = viewModel.uiState,
            checkGameState = { i, j ->
                viewModel.checkGameState(i, j)
            }, resetGame = {
                viewModel.resetGame()
            })
    }
}


@Preview(showBackground = true)
@Composable
fun OnlineTwoPlayerPreview() {
    TicTacToeTheme {
        OnlineTwoPlayerScreen(viewModel = OnlinePlayerViewModel())
    }
}