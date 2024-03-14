package com.kingofraccoons.lab1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kingofraccoons.lab1.ui.screens.ChoiceScreen
import com.kingofraccoons.lab1.ui.screens.MainScreen
import com.kingofraccoons.lab1.ui.theme.Lab1Theme

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<UnitsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab1Theme {
                App(viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(unitsViewModel: UnitsViewModel) {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState()
    val needBackArrowRoutes = Routes.entries.filter { it.needBackArrow }.map { it.name }

    Scaffold(
        Modifier.fillMaxSize(),
        {
            TopAppBar(title = {
                Text(
                    text = "Convert",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleLarge
                )
            }, Modifier.fillMaxWidth(), navigationIcon =
            if (currentRoute.value?.destination?.route in needBackArrowRoutes) {
                {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Default.ArrowBack, contentDescription = "",
                            Modifier
                                .fillMaxHeight()
                                .padding(6.dp)
                                .aspectRatio(1f),
                            MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            } else {
                {}
            })
        }, contentColor = MaterialTheme.colorScheme.background
    ) {
        NavHost(navController = navController, Routes.Main.name, Modifier.padding(it)) {
            composable(Routes.Main.name,
                enterTransition = { enterAnimation },
                exitTransition = { exitAnimation }
            ) {
                MainScreen(unitsViewModel = unitsViewModel) {
                    navController.navigate(Routes.Choice.name)
                }
            }

            composable(Routes.Choice.name,
                enterTransition = { enterAnimation },
                exitTransition = { exitAnimation }
            ) {
                ChoiceScreen(unitsViewModel) { navController.popBackStack() }
            }
        }
    }
}


val enterAnimation = fadeIn() + slideIn(initialOffset = { IntOffset(-it.width, 0) })
val exitAnimation = fadeOut() + slideOut(targetOffset = { IntOffset(-it.width, 0) })