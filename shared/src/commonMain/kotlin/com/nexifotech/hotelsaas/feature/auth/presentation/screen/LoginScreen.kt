package com.nexifotech.hotelsaas.feature.auth.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nexifotech.hotelsaas.feature.auth.presentation.components.*
import com.nexifotech.hotelsaas.feature.auth.presentation.event.LoginEvent
import com.nexifotech.hotelsaas.feature.auth.presentation.viewmodel.LoginViewModel
import org.koin.compose.koinInject

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = koinInject()
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let { error ->
            snackbarHostState.showSnackbar(
                message = error,
                duration = SnackbarDuration.Short
            )
            viewModel.onEvent(LoginEvent.ErrorDismissed)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .widthIn(max = 480.dp)
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LoginHeader()

                Spacer(modifier = Modifier.height(32.dp))

                LoginCard {
                    UsernameTextField(
                        value = uiState.username,
                        onValueChange = { viewModel.onEvent(LoginEvent.UsernameChanged(it)) },
                        enabled = !uiState.isLoading
                    )

                    PasswordTextField(
                        value = uiState.password,
                        onValueChange = { viewModel.onEvent(LoginEvent.PasswordChanged(it)) },
                        isPasswordVisible = uiState.isPasswordVisible,
                        onTogglePasswordVisibility = { viewModel.onEvent(LoginEvent.TogglePasswordVisibility) },
                        enabled = !uiState.isLoading
                    )

                    RememberMeRow(
                        checked = uiState.rememberMe,
                        onCheckedChange = { viewModel.onEvent(LoginEvent.RememberMeChanged(it)) }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    LoginButton(
                        onClick = { viewModel.onEvent(LoginEvent.LoginClicked) },
                        isLoading = uiState.isLoading
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                FooterSection()
            }
        }
    }
}
