package com.coderbdk.tictactoe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.coderbdk.tictactoe.ui.game.OfflineTwoPlayerScreen
import com.coderbdk.tictactoe.ui.game.OfflineVsComputerScreen
import com.coderbdk.tictactoe.ui.home.HomeScreen
import com.coderbdk.tictactoe.ui.theme.TicTacToeTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TicTacToeTheme(
                darkTheme = false
            ) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainUi()
                }
            }
        }
    }
}

@Composable
private fun MainUi(
) {

    val navController = rememberNavController()
    var isSplashLoaded by rememberSaveable {
        mutableStateOf(true)
    }

    navController.addOnDestinationChangedListener { controller, destination, arguments ->
        if (destination.route == Screen.Home.route) {

        }
    }

    MainScreen(
        navController = navController,
        isSplashLoaded = isSplashLoaded
    ) {
        composable(Screen.Splash.route) {
            SplashScreen {
                isSplashLoaded = true
            }
        }
        composable(Screen.Home.route) { HomeScreen(navController = navController) }
        composable(Screen.OfflineTwoPlayer.route) { OfflineTwoPlayerScreen(viewModel = viewModel()) }
        composable(Screen.OfflineVsComputer.route) { OfflineVsComputerScreen(viewModel = viewModel()) }
    }
}

fun navigate(navController: NavController, screen: Screen) {
    navController.navigate(screen.route) {
        // Pop up to the start destination of the graph to
        // avoid building up a large stack of destinations
        // on the back stack as users select items
//        popUpTo(navController.graph.findStartDestination().id) {
//            saveState = true
//        }
        // Avoid multiple copies of the same destination when
        // reselecting the same item
        launchSingleTop = true
        // Restore state when reselecting a previously selected item
        restoreState = true

    }
}

@Composable
fun MainScreen(
    navController: NavHostController,
    isSplashLoaded: Boolean,
    builder: NavGraphBuilder.() -> Unit
) {

    Scaffold { innerPadding ->
        NavHost(
            navController,
            startDestination = if (isSplashLoaded) Screen.Home.route else Screen.Splash.route,
            Modifier.padding(innerPadding)
        ) {
            builder()
        }
    }
}

@Composable
fun SplashScreen(onLoaded: () -> Unit) {
    Text(
        modifier = Modifier
            .padding(28.dp),
        text = "Hello"
    )
    LaunchedEffect(key1 = true) {
        delay(100)
        onLoaded()
    }
}

sealed class Screen(
    val route: String,
) {
    data object Splash : Screen("splash")
    data object Home : Screen("home")
    data object OfflineTwoPlayer : Screen("offline_two_player")
    data object OfflineVsComputer : Screen("offline_vs_computer")
    data object OnlineTwoPlayer : Screen("online_two_player")
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    TicTacToeTheme {
        MainScreen(
            navController = rememberNavController(),
            isSplashLoaded = true
        ) {
            composable(Screen.Home.route) { HomeScreen(navController = rememberNavController()) }
        }
    }
}