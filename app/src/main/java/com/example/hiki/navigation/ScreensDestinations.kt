package com.example.hiki.navigation

sealed class ScreenDestinations(val route: String) {
    data object OnBoardingScreenOne : ScreenDestinations("on_boarding_screen_one")
    data object OnBoardingScreenTwo : ScreenDestinations("on_boarding_screen_two")
    data object OnBoardingScreenThree : ScreenDestinations("on_boarding_screen_three")
    data object LoginScreen : ScreenDestinations("login_screen")
    data object RegisterScreen : ScreenDestinations("register_screen")
    data object ChatScreen : ScreenDestinations("chat_screen")
}