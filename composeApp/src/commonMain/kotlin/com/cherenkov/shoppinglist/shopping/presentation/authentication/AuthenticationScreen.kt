package com.cherenkov.shoppinglist.shopping.presentation.authentication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cherenkov.shoppinglist.core.presentation.BackGround
import com.cherenkov.shoppinglist.core.presentation.BackGroundItems
import com.cherenkov.shoppinglist.core.presentation.Buttons
import com.cherenkov.shoppinglist.core.presentation.HeaderColor
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AuthenticationScreenRoot(
    viewModel: AuthenticationViewModel = koinViewModel(),
    onSetCodeClick: () -> Unit
){
    val state by viewModel.state.collectAsStateWithLifecycle()
    val authenticationStatus by viewModel.authenticationStatus.collectAsStateWithLifecycle()

    AuthenticationScreen(
        state = state,
        onAction = { action ->
            when(action){
                is AuthenticationAction.SetCodeNextScreen -> onSetCodeClick()
                else -> Unit
            }
            viewModel.onAction(action)
        },
        authenticationStatus = authenticationStatus
    )

}

@Composable
fun AuthenticationScreen(
    state: AuthenticationState,
    onAction: (AuthenticationAction) -> Unit,
    authenticationStatus: Boolean
) {
    val errorMessage = state.errorMessage

    LaunchedEffect(authenticationStatus) {
        if (authenticationStatus) {
            onAction(AuthenticationAction.SetCodeNextScreen)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackGround)
            .padding(16.dp)
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Enter your authentication code",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                color = HeaderColor
            ),
            modifier = Modifier.padding(bottom = 32.dp)
        )

        OutlinedTextField(
            value = state.code,
            onValueChange = {
                onAction(AuthenticationAction.onValueTextChange(it))
            },
            label = { Text("Authentication Code") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = errorMessage != null,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Buttons,
                unfocusedBorderColor = BackGroundItems,
                focusedLabelColor = Buttons,
                unfocusedLabelColor = HeaderColor,
                errorBorderColor = MaterialTheme.colorScheme.error,
                errorLabelColor = MaterialTheme.colorScheme.error
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text
            ),
            textStyle = TextStyle(
                fontSize = 16.sp,
                color = Color.White
            ),
        )

        errorMessage?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                onAction(AuthenticationAction.SetCodeClick(state.code))
                      },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Buttons
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            if (state.isLoading){
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.size(40.dp)
                )
            }
            else{
                Text(
                    text = "Authenticate",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}







