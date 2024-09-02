package com.coderbdk.tictactoe.ui.home

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
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
import com.coderbdk.tictactoe.R
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
            .background(
                brush = Brush.linearGradient(
                    listOf(
                        colorScheme.surfaceDim,
                        colorScheme.primaryContainer,
                        colorScheme.primaryContainer
                    )
                )
            )
    ) {
        val (share, settings, sound, help, menu) = createRefs()

        ElevatedButton(
            modifier = Modifier
                .padding(8.dp)
                .constrainAs(share) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                },
            shape = RoundedCornerShape(8.dp),
            onClick = { /*TODO*/ }) {
            Text(text = "Share")
        }
        ElevatedButton(
            modifier = Modifier
                .padding(8.dp)
                .constrainAs(settings) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                },
            shape = RoundedCornerShape(8.dp),
            onClick = { /*TODO*/ }) {
            Text(text = "Settings")
        }

        Column(
            modifier = Modifier
                .wrapContentHeight()
                .padding(top = 8.dp, start = 8.dp, end = 8.dp)
                .shadow(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(8.dp)
                )
                .background(
                    color = colorScheme.surface,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(start = 4.dp, end = 4.dp)
                .padding(top = 2.dp)
                .background(
                   brush = Brush.linearGradient(
                       listOf(
                           colorScheme.primaryContainer,
                           colorScheme.surfaceVariant,
                           colorScheme.surfaceDim
                       )
                   ),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(bottom = 8.dp)
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
                text = "Tic \nTac Toe",
                fontSize = 64.sp,
                lineHeight = 64.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                style = TextStyle(
                    color = colorScheme.surface,
                    shadow = Shadow(
                        color = colorScheme.outline,
                        blurRadius = 8f
                    )
                )
            )
            ElevatedButton(
                modifier = Modifier
                    .height(64.dp)
                    .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
                    .border(
                        width = 1.dp,
                        color = colorScheme.surface,
                        shape = RoundedCornerShape(8.dp)
                    ),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = colorScheme.surfaceVariant,
                    contentColor = colorScheme.onSurfaceVariant
                ),
                onClick = { navigate(navController, Screen.OfflineTwoPlayer) }) {
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier
                            .size(40.dp),
                        painter = painterResource(id = R.drawable.baseline_people_24),
                        contentDescription = "play icon"
                    )
                    Text(
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxWidth(),
                        text = "Offline Two Player".uppercase(),
                        fontSize = 22.sp,
                        textAlign = TextAlign.Justify,
                    )
                }

            }
            ElevatedButton(
                modifier = Modifier
                    .height(64.dp)
                    .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
                    .border(
                        width = 1.dp,
                        color = colorScheme.surface,
                        shape = RoundedCornerShape(8.dp)
                    ),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = colorScheme.surfaceVariant,
                    contentColor = colorScheme.onSurfaceVariant
                ),
                onClick = { navigate(navController, Screen.OfflineVsComputer) }) {
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier
                            .size(40.dp),
                        imageVector = Icons.Default.Person, contentDescription = "play icon"
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        text = "Player Vs Computer".uppercase(),
                        fontSize = 22.sp,
                        textAlign = TextAlign.Justify
                    )
                }


            }
            ElevatedButton(
                modifier = Modifier
                    .height(64.dp)
                    .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
                    .border(
                        width = 1.dp,
                        color = colorScheme.surface,
                        shape = RoundedCornerShape(8.dp)
                    ),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = colorScheme.surfaceVariant,
                    contentColor = colorScheme.onSurfaceVariant
                ),
                onClick = { navigate(navController, Screen.OnlineTwoPlayer) }) {
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier
                            .size(40.dp),
                        painter = painterResource(id = R.drawable.baseline_connect_without_contact_24),
                        contentDescription = "play icon"
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        text = "Online Two Player".uppercase(), fontSize = 22.sp,
                        textAlign = TextAlign.Justify
                    )
                }

            }
        }

        ElevatedButton(
            modifier = Modifier
                .padding(8.dp)
                .constrainAs(sound) {
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                },
            shape = RoundedCornerShape(8.dp),
            onClick = { /*TODO*/ }) {
            Text(text = "Sound")
        }
        ElevatedButton(
            modifier = Modifier
                .padding(8.dp)
                .constrainAs(help) {
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                },
            shape = RoundedCornerShape(8.dp),
            onClick = { /*TODO*/ }) {
            Text(text = "Help")
        }

    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview(modifier: Modifier = Modifier) {
    TicTacToeTheme(
        dynamicColor = false,
        darkTheme = false
    ) {
        HomeScreen(navController = rememberNavController())
    }
}

