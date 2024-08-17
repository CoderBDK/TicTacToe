package com.coderbdk.tictactoe.ui.home

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.coderbdk.tictactoe.Screen
import com.coderbdk.tictactoe.navigate
import com.coderbdk.tictactoe.ui.theme.TicTacToeTheme

@Composable
fun HomeScreen(navController: NavController) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val (share,settings,sound,help, menu) = createRefs()

        Button(
            modifier = Modifier
                .padding(8.dp)
                .constrainAs(share) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                },
            onClick = { /*TODO*/ }) {
            Text(text = "Share")
        }
        Button(
            modifier = Modifier
                .padding(8.dp)
                .constrainAs(settings) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                },
            onClick = { /*TODO*/ }) {
            Text(text = "Settings")
        }

        Column(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant
                )
                .padding(8.dp)
                .constrainAs(menu) {
                    start.linkTo(parent.start)
                    top.linkTo(share.bottom)
                    end.linkTo(parent.end)
                    bottom.linkTo(sound.top)
                    height = Dimension.fillToConstraints
                },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier
                    .padding(bottom = 16.dp),
                text = "Tic\nTacToe",
                fontSize = 64.sp,
                lineHeight = 64.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Cursive,
            )
            Button(
                modifier = Modifier.padding(start = 24.dp, end = 24.dp, bottom = 8.dp),
                onClick = {  navigate(navController, Screen.OfflineTwoPlayer) }) {
                Row(
                    Modifier.fillMaxWidth()
                ) {
                    // Icon(imageVector = Icons.Default.AccountBox, contentDescription = "play icon")
                    Text(text = "Offline Two Player", fontSize = 24.sp)
                }

            }
            Button(
                modifier = Modifier.padding(start = 24.dp, end = 24.dp, bottom = 8.dp),
                onClick = {  navigate(navController, Screen.OfflineVsComputer) }) {
                Row(
                    Modifier.fillMaxWidth()
                ) {
                    // Icon(imageVector = Icons.Default.AccountBox, contentDescription = "play icon")
                    Text(text = "Player Vs Computer", fontSize = 24.sp)
                }


            }
            Button(
                modifier = Modifier.padding(start = 24.dp, end = 24.dp, bottom = 8.dp),
                onClick = {  navigate(navController, Screen.OfflineTwoPlayer) }) {
                Row(
                    Modifier.fillMaxWidth()
                ) {
                   // Icon(imageVector = Icons.Default.AccountBox, contentDescription = "play icon")
                    Text(text = "Online Two Player",fontSize = 24.sp)
                }

            }
        }

        Button(
            modifier = Modifier
                .padding(8.dp)
                .constrainAs(sound) {
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                },
            onClick = { /*TODO*/ }) {
            Text(text = "Sound")
        }
        Button(
            modifier = Modifier
                .padding(8.dp)
                .constrainAs(help) {
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                },
            onClick = { /*TODO*/ }) {
            Text(text = "Help")
        }

    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview(modifier: Modifier = Modifier) {
    TicTacToeTheme {
        HomeScreen(navController = rememberNavController())
    }
}

