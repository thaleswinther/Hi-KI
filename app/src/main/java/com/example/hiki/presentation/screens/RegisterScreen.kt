package com.example.hiki.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.hiki.R
import com.example.hiki.presentation.event.RegisterUiEvent
import com.example.hiki.presentation.state.RegisterState
import com.example.hiki.presentation.viewmodel.RegisterViewModel
import com.example.hiki.theme.black
import com.example.hiki.theme.red_less
import com.example.hiki.theme.white

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onLoginClick: () -> Unit,
    registerViewModel: RegisterViewModel = hiltViewModel()
) {
    val registerState = registerViewModel.registerState.collectAsState().value
    val keyboardController = LocalSoftwareKeyboardController.current

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (maxWidth < maxHeight) {
            // Layout para orientação vertical
            VerticalLayout(
                registerState = registerState,
                registerViewModel = registerViewModel,
                onRegisterSuccess = onRegisterSuccess,
                onLoginClick = onLoginClick,
                keyboardController = keyboardController
            )
        } else {
            // Layout para orientação horizontal
            HorizontalLayout(
                registerState = registerState,
                registerViewModel = registerViewModel,
                onRegisterSuccess = onRegisterSuccess,
                onLoginClick = onLoginClick,
                keyboardController = keyboardController
            )
        }
    }
}



@Composable
fun VerticalLayout(
    registerState: RegisterState,
    registerViewModel: RegisterViewModel,
    onRegisterSuccess: () -> Unit,
    onLoginClick: () -> Unit,
    keyboardController: SoftwareKeyboardController?
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .background(Color(white)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(104.dp))
        Text(
            text = stringResource(id = R.string.welcome),
            color = Color(black),
            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(48.dp))
        Text(
            text = stringResource(id = R.string.register_message),
            color = Color(black),
            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))

        listOf(
            Triple(registerState.email, KeyboardType.Email, RegisterUiEvent::UpdateEmail),
            Triple(registerState.username, KeyboardType.Text, RegisterUiEvent::UpdateUsername),
            Triple(registerState.password, KeyboardType.Password, RegisterUiEvent::UpdatePassword)
        ).forEach { (value, keyboardType, event) ->
            TextField(
                value = value,
                onValueChange = { registerViewModel.onEvent(event(it)) },
                placeholder = {
                    Text(
                        text = stringResource(
                            id = when (keyboardType) {
                                KeyboardType.Email -> R.string.email
                                KeyboardType.Text -> R.string.username
                                else -> R.string.password
                            }
                        ),
                        style = TextStyle(fontSize = MaterialTheme.typography.bodySmall.fontSize)
                    )
                },
                singleLine = true,
                textStyle = TextStyle(fontSize = MaterialTheme.typography.bodySmall.fontSize),
                keyboardOptions = KeyboardOptions(
                    keyboardType = keyboardType,
                    imeAction = if (keyboardType == KeyboardType.Password) ImeAction.Done else ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onDone = { if (keyboardType == KeyboardType.Password)
                        registerViewModel.onEvent(RegisterUiEvent.Register)
                        keyboardController?.hide() }
                ),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(start = 32.dp, end = 32.dp)
                    .border(1.1.dp, Color(black), RoundedCornerShape(40))
                    .clip(RoundedCornerShape(40)),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(white),
                    unfocusedContainerColor = Color(white),
                    disabledContainerColor = Color(white),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                )
            )
            if (keyboardType == KeyboardType.Email || keyboardType == KeyboardType.Text) {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        if (registerState.error != null) {
            Text(
                text = when (registerState.error) {
                    "All fields are required" -> {
                        stringResource(id = R.string.all_fields_required)
                    }
                    "User already exists" -> {
                        stringResource(id = R.string.user_already_exists)
                    }
                    else -> {
                        registerState.error
                    }
                },
                color = Color.Red,
                fontSize = MaterialTheme.typography.bodySmall.fontSize,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                registerViewModel.onEvent(RegisterUiEvent.Register)
                keyboardController?.hide()
            },
            colors = ButtonDefaults.buttonColors(Color(red_less)),
            modifier = Modifier.size(width = 120.dp, height = 44.dp),
        ) {
            Text(
                text = stringResource(id = R.string.create),
                color = Color(white),
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            HorizontalDivider(
                modifier = Modifier
                    .padding(start = 32.dp)
                    .weight(1f),
                thickness = 1.dp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(stringResource(id = (R.string.or)), color = Color(black))
            Spacer(modifier = Modifier.width(16.dp))
            HorizontalDivider(
                modifier = Modifier
                    .padding(end = 32.dp)
                    .weight(1f),
                thickness = 1.dp,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.facebook_logo_register),
                    contentDescription = stringResource(id = R.string.facebook_description),
                    modifier = Modifier
                        .size(width = 140.dp, height = 64.dp)
                        .clickable { /* Handle Facebook register */ }
                )
                Text("Facebook", color = Color(black))
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.google_logo_register),
                    contentDescription = stringResource(id = R.string.google_description),
                    modifier = Modifier
                        .size(width = 140.dp, height = 64.dp)
                        .clickable { /* Handle Google register */ }
                )
                Text("Google", color = Color(black))
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row (
            verticalAlignment = Alignment.CenterVertically,
        ){
            Text(stringResource(R.string.login_account), color = Color(black))
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = stringResource(R.string.click_here),
                color = Color(red_less),
                style = TextStyle(
                    textDecoration = TextDecoration.Underline
                ),
                modifier = Modifier
                    .clickable(onClick = onLoginClick)
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        Image(
            painter = painterResource(id = R.drawable.hiki_logo_without_text),
            contentDescription = stringResource(id = R.string.logo_description),
            modifier = Modifier
                .padding(bottom = 64.dp)
                .size(width = 68.dp, height = 25.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (registerState.isLoading) {
            Spacer(modifier = Modifier.height(16.dp))
            CircularProgressIndicator()
        }

        if (registerState.isRegistered) {
            onRegisterSuccess()
        }
    }
}

@Composable
fun HorizontalLayout(
    registerState: RegisterState,
    registerViewModel: RegisterViewModel,
    onRegisterSuccess: () -> Unit,
    onLoginClick: () -> Unit,
    keyboardController: SoftwareKeyboardController?
) {
    Row (
        modifier = Modifier
            .background(Color(white))
            .imePadding()
            .padding(16.dp)
            .fillMaxSize(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column (
            modifier = Modifier
                .weight(1f)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(id = R.string.welcome),
                color = Color(black),
                fontSize = MaterialTheme.typography.bodySmall.fontSize,
                textAlign = TextAlign.Center
            )
            Text(
                text = stringResource(id = R.string.register_message),
                color = Color(black),
                fontSize = MaterialTheme.typography.bodySmall.fontSize,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))

            listOf(
                Triple(registerState.email, KeyboardType.Email, RegisterUiEvent::UpdateEmail),
                Triple(registerState.username, KeyboardType.Text, RegisterUiEvent::UpdateUsername),
                Triple(registerState.password, KeyboardType.Password, RegisterUiEvent::UpdatePassword)
            ).forEach { (value, keyboardType, event) ->
                TextField(
                    value = value,
                    onValueChange = { registerViewModel.onEvent(event(it)) },
                    placeholder = {
                        Text(
                            text = stringResource(
                                id = when (keyboardType) {
                                    KeyboardType.Email -> R.string.email
                                    KeyboardType.Text -> R.string.username
                                    else -> R.string.password
                                }
                            ),
                            style = TextStyle(fontSize = 10.sp)
                        )
                    },
                    singleLine = true,
                    textStyle = TextStyle(fontSize = 10.sp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = keyboardType,
                        imeAction = if (keyboardType == KeyboardType.Password) ImeAction.Done else ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { if (keyboardType == KeyboardType.Password)
                            registerViewModel.onEvent(RegisterUiEvent.Register)
                            keyboardController?.hide() }
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                        .padding(horizontal = 32.dp)
                        .border(1.1.dp, Color(black), RoundedCornerShape(40))
                        .clip(RoundedCornerShape(40)),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(white),
                        unfocusedContainerColor = Color(white),
                        disabledContainerColor = Color(white),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
            if (registerState.error != null) {
                Text(
                    text = when (registerState.error) {
                        "All fields are required" -> {
                            stringResource(id = R.string.all_fields_required)
                        }
                        "User already exists" -> {
                            stringResource(id = R.string.user_already_exists)
                        }
                        else -> {
                            registerState.error
                        }
                    },
                    color = Color.Red,
                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    registerViewModel.onEvent(RegisterUiEvent.Register)
                    keyboardController?.hide()
                },
                colors = ButtonDefaults.buttonColors(Color(red_less)),
                modifier = Modifier.size(width = 112.dp, height = 40.dp),
            ) {
                Text(
                    text = stringResource(id = R.string.create),
                    color = Color(white),
                )
            }
        }

        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            Box(
                modifier = Modifier
                    .width(1.dp)
                    .weight(1f)
                    .background(Color.Black)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(stringResource(id = R.string.or), color = Color.Black)
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .width(1.dp)
                    .weight(1f)
                    .background(Color.Black)
            )
            Spacer(modifier = Modifier.height(32.dp))
        }


        Column (
            modifier = Modifier
                .weight(1f)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row {
                Text(
                    text = stringResource(id = R.string.social_media_login),
                    color = Color(black),
                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

            }
            Row {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.facebook_logo_register),
                        contentDescription = stringResource(id = R.string.facebook_description),
                        modifier = Modifier
                            .size(width = 140.dp, height = 64.dp)
                            .clickable { /* Handle Facebook register */ }
                    )
                    Text("Facebook", color = Color(black), fontSize = MaterialTheme.typography.bodySmall.fontSize)
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.google_logo_register),
                        contentDescription = stringResource(id = R.string.google_description),
                        modifier = Modifier
                            .size(width = 140.dp, height = 64.dp)
                            .clickable { /* Handle Google register */ }
                    )
                    Text("Google", color = Color(black), fontSize = MaterialTheme.typography.bodySmall.fontSize)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row (
                verticalAlignment = Alignment.CenterVertically,
            ){
                Text(stringResource(R.string.login_account), color = Color(black), fontSize = MaterialTheme.typography.bodySmall.fontSize)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = stringResource(R.string.click_here),
                    color = Color(red_less),
                    style = TextStyle(
                        textDecoration = TextDecoration.Underline
                    ),
                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                    modifier = Modifier
                        .clickable(onClick = onLoginClick)
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            Image(
                painter = painterResource(id = R.drawable.hiki_logo_without_text),
                contentDescription = stringResource(id = R.string.logo_description),
                modifier = Modifier
                    .size(width = 68.dp, height = 25.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (registerState.isLoading) {
                Spacer(modifier = Modifier.height(16.dp))
                CircularProgressIndicator()
            }
            if (registerState.isRegistered) {
                onRegisterSuccess()
            }
        }
    }
}

