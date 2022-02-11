package com.leviancode.youngscientist.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.leviancode.youngscientist.ui.Navigator.NavTarget
import com.leviancode.youngscientist.ui.screens.detail.DetailScreen
import com.leviancode.youngscientist.ui.screens.home.HomeScreen
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun NavigationComponent(navController: NavHostController, navigator: Navigator) {
    LaunchedEffect("navigation") {
        navigator.navTargetFlow.onEach {
            navController.navigate(it.label)
        }.launchIn(this)
    }

    NavHost(
        navController = navController,
        startDestination = NavTarget.HOME.label
    ) {
        composable(NavTarget.HOME.label) {
            HomeScreen()
        }
        composable(NavTarget.DETAIL.label) {
            DetailScreen()
        }
    }
}

class Navigator {
    private val _navTargetFlow = MutableSharedFlow<NavTarget>(extraBufferCapacity = 1)
    val navTargetFlow = _navTargetFlow.asSharedFlow()

    fun navigateTo(navTarget: NavTarget) {
        _navTargetFlow.tryEmit(navTarget)
    }

    enum class NavTarget(val label: String) {
        HOME("home"),
        DETAIL("detail")
    }
}

