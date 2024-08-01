package com.example.hiki.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Send
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hiki.R
import com.example.hiki.presentation.event.ChatUiEvent
import com.example.hiki.presentation.viewmodel.ChatViewModel
import com.example.hiki.theme.black
import com.example.hiki.theme.primary
import com.example.hiki.theme.red_less
import com.example.hiki.theme.white
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

@Composable
fun ChatScreen(
    onBackPress: () -> Unit,
    uriState: MutableStateFlow<String>,
) {
    val chatViewModel = viewModel<ChatViewModel>()
    val chatState = chatViewModel.chatState.collectAsState().value
    val isLoading = chatState.showIndicator

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(Color(white))
            .navigationBarsPadding()
            .imePadding(),
        verticalArrangement = Arrangement.Bottom,
    ) {
        TopBar(onBackPress = onBackPress)
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            reverseLayout = true,
        ) {
            itemsIndexed(chatState.chatList) { _, chat ->
                if (chat.isFromUser) {
                    UserChatItem(
                        prompt = chat.prompt,
                    )
                } else {
                    ModelChatItem(response = chat.prompt)
                }
            }
        }
        val focusManager = LocalFocusManager.current

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .background(Color(primary))
                .padding(bottom = 20.dp, start = 16.dp, end = 16.dp, top = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(modifier = Modifier.width(8.dp))

            TextField(
                modifier = Modifier
                    .weight(1f),
                value = chatState.prompt,
                onValueChange = {
                    chatViewModel.onEvent(ChatUiEvent.UpdatePrompt(it))
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(white),
                    unfocusedContainerColor = Color(white),
                    disabledContainerColor = Color(white),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                ),
                shape = RoundedCornerShape(20.dp),
                singleLine = true,
                trailingIcon = {
                    if (chatState.prompt.isNotEmpty()) {
                        IconButton(
                            onClick = {
                                // clear the prompt
                                chatViewModel.onEvent(ChatUiEvent.UpdatePrompt(""))
                            },
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Close,
                                contentDescription = null,
                            )
                        }
                    } else if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = MaterialTheme.colorScheme.primary,
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = if (chatState.prompt.isNotEmpty()) {
                        ImeAction.Send
                    } else {
                        ImeAction.None
                    },
                ),
                keyboardActions = KeyboardActions(
                    onSend = {
                        // hide keyboard ime keyboard when send button is clicked
                        chatViewModel.onEvent(ChatUiEvent.SendPrompt(chatState.prompt))
                        uriState.update { "" }
                        focusManager.clearFocus()
                    },
                ),
                placeholder = {
                    Text(
                        text = stringResource(R.string.type_message_here),
                        fontSize = 16.sp,
                    )
                },
            )
            Spacer(modifier = Modifier.width(16.dp))

            Icon(
                modifier = Modifier
                    .size(40.dp)
                    .clickable {
                        chatViewModel.onEvent(ChatUiEvent.SendPrompt(chatState.prompt))
                        uriState.update { "" }
                        focusManager.clearFocus()
                    },
                imageVector = Icons.AutoMirrored.Rounded.Send,
                contentDescription = stringResource(id = R.string.send_prompt),
                tint = Color(white),
            )
        }
    }
}

@Composable
fun ModelChatItem(response: String) {
    Column(
        modifier = Modifier.padding(end = 100.dp, bottom = 16.dp),
    ) {
        Text(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(12.dp))
                .background(Color(primary))
                .padding(16.dp),
            text = response,
            fontSize = 17.sp,
            color = MaterialTheme.colorScheme.onPrimary,
        )
    }
}

@Composable
fun UserChatItem(prompt: String) {
    Column(
        modifier = Modifier
            .padding(start = 100.dp, bottom = 16.dp),
    ) {
        Text(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(12.dp))
                .background(Color(red_less))
                .padding(16.dp),
            text = prompt,
            fontSize = 17.sp,
            color = MaterialTheme.colorScheme.onPrimary,
        )
    }
}

@Composable
fun TopBar(onBackPress: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
            .background(Color(primary))
            .padding(bottom = 24.dp, end = 16.dp, top = 24.dp),
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row {
                IconButton(onClick = { onBackPress() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_back),
                        tint = Color(white),
                        contentDescription = stringResource(id = R.string.back)
                    )
                }
                // Avatar do usuário
                Image(
                    painter = painterResource(id = R.drawable.avatar_icon),
                    contentDescription = stringResource(id = R.string.user_avatar),
                    modifier = Modifier.size(48.dp)
                )
                Column(
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Text(
                        text = "Ana",
                        color = Color(black),
                        fontSize = 20.sp
                    )
                    Text(
                        text = stringResource(id = R.string.work),
                        color = Color(black),
                        fontSize = 14.sp
                    )
                }
            }
            // Três pontos do menu
            IconButton(onClick = { /* TODO */ }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    tint = Color(white),
                    contentDescription = stringResource(id = R.string.menu)
                )
            }

        }
    }
}







