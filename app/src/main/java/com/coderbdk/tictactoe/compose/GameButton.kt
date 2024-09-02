package com.coderbdk.tictactoe.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.coderbdk.tictactoe.ui.theme.TicTacToeTheme

@Composable
fun GameButton(
    modifier: Modifier = Modifier,
    topStart: Boolean = false,
    topEnd: Boolean = false,
    bottomStart: Boolean = false,
    bottomEnd: Boolean = false,
) {
    Box(
        modifier = modifier
            .padding(bottom = 8.dp)
    ) {
        Box(
            Modifier
                .size(24.dp)
                .border(
                    width = 2.dp,
                    color = colorScheme.outlineVariant,
                    shape = if(topStart)RoundedCornerShape(topStart = 16.dp) else RoundedCornerShape(2.dp)
                )
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            colorScheme.primary,
                            colorScheme.secondary,
                        )
                    ),
                    shape = if(topStart)RoundedCornerShape(topStart = 16.dp) else RoundedCornerShape(2.dp)
                )
                .align(Alignment.TopStart)
        )
        Box(
            Modifier
                .size(24.dp)
                .border(
                    width = 2.dp,
                    color = colorScheme.outlineVariant,
                    shape = if(topEnd)RoundedCornerShape(topEnd = 16.dp) else RoundedCornerShape(2.dp)
                )
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            colorScheme.primary,
                            colorScheme.secondary,
                        )
                    ),
                    shape = if(topEnd)RoundedCornerShape(topEnd = 16.dp) else RoundedCornerShape(2.dp)
                )
                .align(Alignment.TopEnd)
        )
        Box(
            Modifier
                .size(24.dp)
                .border(
                    width = 2.dp,
                    color = colorScheme.outlineVariant,
                    shape = if(bottomStart)RoundedCornerShape(bottomStart = 16.dp) else RoundedCornerShape(2.dp)
                )
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            colorScheme.primary,
                            colorScheme.secondary,
                        )
                    ),
                    shape = if(bottomStart)RoundedCornerShape(bottomStart = 16.dp) else RoundedCornerShape(2.dp)
                )
                .align(Alignment.BottomStart)
        )
        Box(
            Modifier
                .size(24.dp)
                .border(
                    width = 2.dp,
                    color = colorScheme.outlineVariant,
                    shape =if(bottomEnd)RoundedCornerShape(bottomEnd = 16.dp) else RoundedCornerShape(2.dp)
                )
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            colorScheme.primary,
                            colorScheme.secondary,
                        )
                    ),
                    shape =if(bottomEnd)RoundedCornerShape(bottomEnd = 16.dp) else RoundedCornerShape(2.dp)
                )
                .align(Alignment.BottomEnd)
        )
        Box(
            Modifier
                .padding(8.dp)
                .border(
                    width = 3.dp,
                    brush = Brush.verticalGradient(
                        listOf(
                            colorScheme.onPrimaryContainer,
                            colorScheme.onPrimaryContainer,
                        )
                    ),
                    shape = RoundedCornerShape(8.dp)
                )
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            colorScheme.primary,
                            colorScheme.tertiary,
                        )
                    ),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(start = 16.dp, top = 4.dp, end = 16.dp, bottom = 4.dp)
        ) {
            Text(
                text = "Play Button", fontSize = 40.sp,
                style = TextStyle(
                    color = colorScheme.onTertiary,
                    fontWeight = FontWeight.Bold,
                    shadow = Shadow(
                        color = colorScheme.onPrimaryContainer,
                        blurRadius = 8f
                    )
                )
            )
        }

    }
}


@Preview(showBackground = true)
@Composable
fun GameButtonPreview() {
    TicTacToeTheme(
        darkTheme = true,
        dynamicColor = true
    ) {
        Column(
            Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GameButton(
                topStart = true,
                topEnd = true
            )
            GameButton()
            GameButton(
                bottomStart = true,
                bottomEnd = true
            )
        }
    }
}