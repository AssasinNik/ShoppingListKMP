package com.cherenkov.shoppinglist.shopping.presentation.shopping_lists

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cherenkov.shoppinglist.core.presentation.BackGround
import com.cherenkov.shoppinglist.core.presentation.BackGroundBox
import com.cherenkov.shoppinglist.core.presentation.BackGroundItems
import com.cherenkov.shoppinglist.core.presentation.Buttons
import com.cherenkov.shoppinglist.core.presentation.HeaderColor
import com.cherenkov.shoppinglist.core.presentation.UiText
import com.cherenkov.shoppinglist.shopping.domain.ShoppingList
import com.cherenkov.shoppinglist.shopping.presentation.reusable_components.AddButton
import com.cherenkov.shoppinglist.shopping.presentation.shopping_lists.components.ShoppingLazyList
import org.koin.compose.viewmodel.koinViewModel
import shoppinglist.composeapp.generated.resources.Res
import shoppinglist.composeapp.generated.resources.no_shopping_lists


@Composable
fun ShoppingListsScreenRoot(
    viewModel: ShoppingListsViewModel = koinViewModel(),
    onListClick: (ShoppingList) -> Unit,
    onAddClick: () -> Unit
){
    val state by viewModel.state.collectAsStateWithLifecycle()

    ShoppingListsScreen(
        state = state,
        onAction = { action ->
            when(action){
                is ShoppingListAction.OnListClick -> onListClick(action.shoppingList)
                is ShoppingListAction.OnFloatingButtonClick -> onAddClick()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )

}

@Composable
fun ShoppingListsScreen(
    state: ShoppingListState,
    onAction: (ShoppingListAction) -> Unit
) {
    val lazyListState = rememberLazyListState()

    Scaffold(
        floatingActionButton = {
            if (state.listItems.isNotEmpty()) {
                FloatingActionButton(
                    onClick = { onAction(ShoppingListAction.OnFloatingButtonClick) },
                    containerColor = Buttons,
                    contentColor = Color.White
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "Add List")
                }
            }
        },
        containerColor = BackGround
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Text(
                text = "Your Shopping Lists",
                style = MaterialTheme.typography.titleLarge.copy(
                    color = HeaderColor,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Surface(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                color = BackGroundBox,
                shape = RoundedCornerShape(16.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    when {
                        state.isLoading -> {
                            CircularProgressIndicator(
                                color = Buttons,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                        state.errorMessage != null -> {
                            Text(
                                text = state.errorMessage.asString(),
                                color = MaterialTheme.colorScheme.error,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                        state.listItems.isNotEmpty() -> {
                            ShoppingLazyList(
                                lists = state.listItems,
                                onListClick = { onAction(ShoppingListAction.OnListClick(it)) },
                                onRemoveClick = { onAction(ShoppingListAction.OnRemoveListClick(it)) },
                                modifier = Modifier.fillMaxSize(),
                                scrollState = lazyListState
                            )
                        }
                        else -> {
                            EmptyStatePlaceholder(
                                onAddClick = { onAction(ShoppingListAction.OnFloatingButtonClick) }
                            )
                        }
                    }
                }
            }
        }
    }

}


@Composable
fun EmptyStatePlaceholder(onAddClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = "Shopping Cart",
                tint = HeaderColor,
                modifier = Modifier
                    .size(115.dp)
                    .aspectRatio(
                        ratio = 0.65f,
                        matchHeightConstraintsFirst = true
                    )
            )
            Text(
                text = UiText.StringResourceId(Res.string.no_shopping_lists).asString(),
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Light,
                    color = HeaderColor
                )
            )
            AddButton(
                onClick = {
                    onAddClick()
                },
                color = Buttons,
                icon = Icons.Filled.Add,
                text = "Create"
            )
        }
    }
}