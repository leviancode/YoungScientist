package com.leviancode.youngscientist.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.leviancode.youngscientist.ui.screens.detail.DetailScreen
import com.leviancode.youngscientist.ui.screens.home.HomeScreen

@Composable
fun NavigationComponent() {
    val navController = rememberNavController()
    val navigator = remember { Navigator(navController) }

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen { journalId ->
                navigator.navigateTo(Screen.Detail(journalId))
            }
        }
        composable(Screen.Detail.route) { backStackEntry ->
            val journalId = backStackEntry.arguments?.getString(Screen.Detail.KEY_ID)
            requireNotNull(journalId) { "journalId parameter wasn't found. Please make sure it's set!" }
            DetailScreen(journalId.toInt()){
                navigator.navigateBack()
            }
        }
    }
}

class Navigator(private val navController: NavHostController) {

    fun navigateTo(navTarget: Screen) {
        navController.navigate(navTarget.route)
    }

    fun navigateBack() {
        navController.popBackStack()
    }
}

sealed class Screen(val route: String) {
    object Home : Screen("home")
    class Detail(journalId: Int) : Screen("detail/$journalId") {

        companion object {
            const val KEY_ID = "journalId"
            const val route = "detail/{$KEY_ID}"
        }
    }
}

