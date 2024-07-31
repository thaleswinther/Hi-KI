/*package com.example.hiki.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.hiki.presentation.screens.ChatScreen
import com.example.hiki.presentation.screens.HomeScreen
import kotlinx.coroutines.flow.MutableStateFlow

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainAnimationNavHost(
    uriState: MutableStateFlow<String>,
    navController: NavHostController,
    startDestination: String = ScreenDestinations.HomeScreen.route,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        screen(ScreenDestinations.HomeScreen.route) {
            HomeScreen(navController = navController)
        }
        screen(ScreenDestinations.ChatScreen.route) {
            ChatScreen(
                onBackPress = {
                    navController.navigateTo(ScreenDestinations.HomeScreen.route)
                },
                uriState = uriState,
            )
        }
    }
    // Back Handler
    BackHandler {
        navController.popBackStack()
    }
}*/

package com.example.hiki.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.hiki.presentation.screens.ChatScreen
//import com.example.hiki.presentation.screens.HomeScreen
import com.example.hiki.presentation.screens.LoginScreen
import com.example.hiki.presentation.screens.OnBoardingScreenOne
import com.example.hiki.presentation.screens.RegisterScreen
import kotlinx.coroutines.flow.MutableStateFlow

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainAnimationNavHost(
    uriState: MutableStateFlow<String>,
    navController: NavHostController,
    startDestination: String = ScreenDestinations.LoginScreen.route,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        screen(ScreenDestinations.LoginScreen.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(ScreenDestinations.OnBoardingScreenOne.route) {
                        popUpTo(ScreenDestinations.LoginScreen.route) { inclusive = true }
                    }
                },
                onRegisterClick = {
                    navController.navigate(ScreenDestinations.RegisterScreen.route)
                }
            )
        }
        screen(ScreenDestinations.RegisterScreen.route) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(ScreenDestinations.LoginScreen.route) {
                        popUpTo(ScreenDestinations.RegisterScreen.route) { inclusive = true }
                    }
                },
                onLoginClick = {
                    navController.navigate(ScreenDestinations.LoginScreen.route)
                }
            )
        }
        screen(ScreenDestinations.OnBoardingScreenOne.route) {
            OnBoardingScreenOne(navController = navController)
        }
        screen(ScreenDestinations.ChatScreen.route) {
            ChatScreen(
                onBackPress = {
                    navController.navigateTo(ScreenDestinations.OnBoardingScreenOne.route)
                },
                uriState = uriState,
            )
        }
    }
}
