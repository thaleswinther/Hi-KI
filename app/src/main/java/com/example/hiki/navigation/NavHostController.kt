package com.example.hiki.navigation

import androidx.navigation.NavHostController

fun NavHostController.navigateTo(route: String) = navigate(route) {
    popUpTo(route)
    launchSingleTop = true
}