package com.cherenkov.shoppinglist.shopping.presentation.code_generation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cherenkov.shoppinglist.core.presentation.BackGround
import com.cherenkov.shoppinglist.core.presentation.BackGroundItems
import com.cherenkov.shoppinglist.core.presentation.Buttons
import com.cherenkov.shoppinglist.core.presentation.HeaderColor
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun CodeGenerationScreenRoot(
    viewModel: CodeGenerationViewModel = koinViewModel(),
    onNextScreenClick: () -> Unit,
    onAlreadyHaveCodeClicked: () -> Unit
){
    val state by viewModel.state.collectAsStateWithLifecycle()
    CodeGenerationScreen(
        state = state,
        onAction = { action ->
            when(action){
                is CodeGenerationAction.NextScreenClicked -> onNextScreenClick()
                is CodeGenerationAction.AlreadyHaveCodeClicked -> onAlreadyHaveCodeClicked()
                else -> Unit
            }
            viewModel.onAction(action)
        },
        buttonsColor = Buttons,
        backgroundColor = BackGround,
        backgroundItemsColor = BackGroundItems,
        headerColor = HeaderColor
    )

}

@Composable
fun CodeGenerationScreen(
    state: CodeGenerationState,
    onAction: (CodeGenerationAction) -> Unit,
    backgroundColor: Color,
    headerColor: Color,
    buttonsColor: Color,
    backgroundItemsColor: Color
) {

    // Определяем анимации для появления и исчезновения
    val initialContentEnter = fadeIn(
        animationSpec = tween(durationMillis = 500)
    ) + slideInVertically(
        initialOffsetY = { fullHeight -> fullHeight },
        animationSpec = tween(durationMillis = 500)
    )

    val initialContentExit = fadeOut(
        animationSpec = tween(durationMillis = 500)
    ) + slideOutVertically(
        targetOffsetY = { fullHeight -> -fullHeight },
        animationSpec = tween(durationMillis = 500)
    )

    val codeContentEnter = fadeIn(
        animationSpec = tween(durationMillis = 500)
    ) + scaleIn(
        initialScale = 0.8f,
        animationSpec = tween(durationMillis = 500)
    )

    val codeContentExit = fadeOut(
        animationSpec = tween(durationMillis = 500)
    ) + scaleOut(
        targetScale = 1.2f,
        animationSpec = tween(durationMillis = 500)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp)
            .statusBarsPadding()
    ) {
        // Начальное содержимое (форма ввода)
        AnimatedVisibility(
            visible = state.generatedCode == null,
            enter = initialContentEnter,
            exit = initialContentExit
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Enter Your Data",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        color = headerColor
                    ),
                    modifier = Modifier
                        .padding(bottom = 24.dp)
                        .animateEnterExit(
                            enter = fadeIn(),
                            exit = fadeOut()
                        )
                )

                OutlinedTextField(
                    value = state.firstName,
                    onValueChange = {
                        onAction(CodeGenerationAction.FirstNameChanged(it))
                    },
                    label = { Text("First name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateEnterExit(
                            enter = fadeIn(),
                            exit = fadeOut()
                        ),
                    singleLine = true,
                    isError = state.errorMessage != null,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = buttonsColor,
                        unfocusedBorderColor = backgroundItemsColor,
                        focusedLabelColor = buttonsColor,
                        unfocusedLabelColor = headerColor,
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

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = state.lastName,
                    onValueChange = {
                        onAction(CodeGenerationAction.LastNameChanged(it))
                    },
                    label = { Text("Last Name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateEnterExit(
                            enter = fadeIn(),
                            exit = fadeOut()
                        ),
                    singleLine = true,
                    isError = state.errorMessage != null,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = buttonsColor,
                        unfocusedBorderColor = backgroundItemsColor,
                        focusedLabelColor = buttonsColor,
                        unfocusedLabelColor = headerColor,
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

                state.errorMessage?.let {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .animateEnterExit(
                                enter = fadeIn(),
                                exit = fadeOut()
                            )
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { onAction(CodeGenerationAction.GenerateCodeClicked) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateEnterExit(
                            enter = fadeIn(),
                            exit = fadeOut()
                        ),
                    colors = ButtonDefaults.buttonColors(containerColor = buttonsColor),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Generate",
                        color = Color.White,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                val annotatedText = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = headerColor)) {
                        append("Already have a code? ")
                    }
                    pushStringAnnotation(tag = "already", annotation = "already_have_code")
                    withStyle(style = SpanStyle(color = buttonsColor, fontWeight = FontWeight.Bold)) {
                        append("Go")
                    }
                    pop()
                }

                ClickableText(
                    text = annotatedText,
                    style = MaterialTheme.typography.bodyMedium,
                    onClick = { offset ->
                        annotatedText.getStringAnnotations("already", offset, offset)
                            .firstOrNull()?.let {
                                onAction(CodeGenerationAction.AlreadyHaveCodeClicked)
                            }
                    },
                    modifier = Modifier
                        .animateEnterExit(
                            enter = fadeIn(),
                            exit = fadeOut()
                        )
                )
            }
        }

        // Содержимое после генерации кода
        AnimatedVisibility(
            visible = state.generatedCode != null,
            enter = codeContentEnter,
            exit = codeContentExit
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                AnimatedVisibility(
                    visible = state.showCode,
                    enter = fadeIn(
                        animationSpec = tween(durationMillis = 500)
                    ),
                    exit = fadeOut()
                ) {
                    Text(
                        text = state.generatedCode ?: "",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = 36.sp,
                            fontWeight = FontWeight.Bold,
                            color = headerColor
                        ),
                        modifier = Modifier
                            .padding(bottom = 32.dp)
                            .animateEnterExit(
                                enter = slideInHorizontally(
                                    initialOffsetX = { it },
                                    animationSpec = tween(durationMillis = 500)
                                ),
                                exit = slideOutHorizontally(
                                    targetOffsetX = { -it },
                                    animationSpec = tween(durationMillis = 500)
                                )
                            )
                    )
                }

                AnimatedVisibility(
                    visible = state.showNextButton,
                    enter = slideInVertically(
                        initialOffsetY = { it },
                        animationSpec = tween(durationMillis = 500)
                    ) + fadeIn(),
                    exit = slideOutVertically(
                        targetOffsetY = { it },
                        animationSpec = tween(durationMillis = 500)
                    ) + fadeOut()
                ) {
                    Button(
                        onClick = { onAction(CodeGenerationAction.NextScreenClicked) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateEnterExit(
                                enter = scaleIn(),
                                exit = scaleOut()
                            ),
                        colors = ButtonDefaults.buttonColors(containerColor = buttonsColor),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "Continue",
                            color = Color.White,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}