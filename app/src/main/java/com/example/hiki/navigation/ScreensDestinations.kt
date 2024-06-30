package com.example.hiki.navigation

sealed class ScreenDestinations(val route: String) {

    //data object HomeScreen : ScreenDestinations("home_screen")
    data object WelcomeScreen : ScreenDestinations("welcome_screen")
    data object LoginScreen : ScreenDestinations("login_screen")
    data object RegisterScreen : ScreenDestinations("register_screen")
    data object ChatScreen : ScreenDestinations("chat_screen")
}