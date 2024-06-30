package com.example.hiki.navigation

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.hiki.common.DURATION_MILLIS

@ExperimentalAnimationApi
fun NavGraphBuilder.screen(
    route: String,
    arguments: List<NamedNavArgument> = listOf(),
    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit,
) {
    val animSpec: FiniteAnimationSpec<IntOffset> = tween(DURATION_MILLIS, easing = FastOutSlowInEasing)

    composable(
        route,
        arguments = arguments,
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { screenWidth -> screenWidth },
                animationSpec = animSpec,
            )
        },
        popEnterTransition = {
            slideInHorizontally(
                initialOffsetX = { screenWidth -> -screenWidth },
                animationSpec = animSpec,
            )
        },
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { screenWidth -> -screenWidth },
                animationSpec = animSpec,
            )
        },
        popExitTransition = {
            slideOutHorizontally(
                targetOffsetX = { screenWidth -> screenWidth },
                animationSpec = animSpec,
            )
        },
        content = content,
    )
}