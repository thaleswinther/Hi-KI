package com.example.hiki.presentation.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.hiki.R
import com.example.hiki.navigation.ScreenDestinations
import com.example.hiki.theme.secundary
import com.example.hiki.theme.white

@Composable
fun OnBoardingScreenTwo(
    navController: NavController,
    onBackPress: () -> Unit
) {

    BackHandler {
        onBackPress()
    }

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (maxWidth < maxHeight) {
            // Layout para orientação vertical
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(white)),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                VerticalLayoutTwo(navController)
            }
        } else {
            // Layout para orientação horizontal
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFFFF7DF)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                HorizontalLayoutTwo(navController)
            }
        }
    }
}

@Composable
fun VerticalLayoutTwo(navController: NavController) {
    Spacer(modifier = Modifier.height(74.dp))
    Image(
        painter = painterResource(id = R.drawable.three_points_red),
        contentDescription = stringResource(id = R.string.step_one_description),
        modifier = Modifier
            .size(width = 42.dp, height = 12.dp),
    )
    Spacer(modifier = Modifier.height(106.dp))
    Image(
        painter = painterResource(id = R.drawable.speech_bubble),
        contentDescription = stringResource(id = R.string.logo_description),
        modifier = Modifier
            .size(width = 174.5.dp, height = 80.36.dp)
    )
    Spacer(modifier = Modifier.height(32.dp))
    Text(
        text = stringResource(id = R.string.welcome_message_two),
        modifier = Modifier
            .padding(horizontal = 32.dp, vertical = 64.dp),
        color = Color(secundary),
        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
        textAlign = TextAlign.Center
    )
    Spacer(modifier = Modifier.height(32.dp))
    Button(
        modifier = Modifier
            .size(width = 128.dp, height = 40.dp),
        onClick = {
            navController.navigate(ScreenDestinations.OnBoardingScreenThree.route) {
                popUpTo(ScreenDestinations.OnBoardingScreenOne.route) {
                    inclusive = false
                }
            }
        },
        colors = ButtonDefaults.buttonColors(
            Color(secundary)
        ),
    ) {
        Text(
            text = stringResource(id = R.string.next),
            color = Color(white),
        )
    }
    Spacer(modifier = Modifier.height(120.dp))
    Image(
        painter = painterResource(id = R.drawable.hiki_logo_without_text),
        contentDescription = stringResource(id = R.string.logo_description),
        modifier = Modifier
            .size(width = 68.dp, height = 25.dp)
    )
}

@Composable
fun HorizontalLayoutTwo(navController: NavController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = painterResource(id = R.drawable.three_points_red),
            contentDescription = stringResource(id = R.string.step_one_description),
            modifier = Modifier
                .size(width = 42.dp, height = 12.dp),
        )
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(id = R.drawable.speech_bubble),
            contentDescription = stringResource(id = R.string.logo_description),
            modifier = Modifier
                .size(width = 174.5.dp, height = 80.36.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(id = R.string.welcome_message_two),
            modifier = Modifier
                .padding(horizontal = 16.dp),
            color = Color(secundary),
            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = Modifier
                .size(width = 128.dp, height = 40.dp),
            onClick = {
                navController.navigate(ScreenDestinations.OnBoardingScreenThree.route) {
                    popUpTo(ScreenDestinations.OnBoardingScreenOne.route) {
                        inclusive = false
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(
                Color(secundary)
            ),
        ) {
            Text(
                text = stringResource(id = R.string.next),
                color = Color(white),
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        Image(
            painter = painterResource(id = R.drawable.hiki_logo_without_text),
            contentDescription = stringResource(id = R.string.logo_description),
            modifier = Modifier
                .size(width = 68.dp, height = 25.dp)
        )
    }
}