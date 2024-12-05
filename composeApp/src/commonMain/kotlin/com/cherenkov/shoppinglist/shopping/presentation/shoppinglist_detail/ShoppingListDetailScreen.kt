package com.cherenkov.shoppinglist.shopping.presentation.shoppinglist_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cherenkov.shoppinglist.core.presentation.BackGround
import com.cherenkov.shoppinglist.core.presentation.Buttons
import com.cherenkov.shoppinglist.shopping.presentation.reusable_components.AddButton
import com.cherenkov.shoppinglist.shopping.presentation.reusable_components.TopBar
import com.cherenkov.shoppinglist.shopping.presentation.shoppinglist_detail.components.LazyListItems
import com.cherenkov.shoppinglist.shopping.presentation.shoppinglist_detail.components.TaskProgressCircle
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ShoppingListDetailScreenRoot(
    viewModel: ShoppingListDetailViewModel = koinViewModel(),
    onBackClick: () -> Unit,
    onAddClick: () -> Unit
){
    val state by viewModel.state.collectAsStateWithLifecycle()

    ShoppingListDetailScreen(
        state = state,
        onAction = { action ->
            when(action){
                is ShoppingListDetailAction.OnBackClick -> onBackClick()
                is ShoppingListDetailAction.OnAddClick -> onAddClick()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )

}

@Composable
fun ShoppingListDetailScreen(
    state: ShoppingListDetailState,
    onAction: (ShoppingListDetailAction) -> Unit
) {
    val lazyListState = rememberLazyListState()

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(BackGround),
        color = BackGround
    ) {
        Box(modifier = Modifier.fillMaxSize()){
            if (state.isLoading) {
                LoadingContent(onAction = onAction, title = state.shoppingList?.name.orEmpty())
            } else {
                if (state.listItems.isNotEmpty()) {
                    ContentWithItems(
                        state = state,
                        onAction = onAction,
                        lazyListState = lazyListState
                    )
                    AddButton(
                        onClick = {
                            onAction(ShoppingListDetailAction.OnAddClick)
                        },
                        text = "Add item",
                        color = Buttons,
                        icon = Icons.Default.Add,
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(16.dp)
                    )
                } else {
                    EmptyListContent(
                        onAction = onAction,
                        title = state.shoppingList?.name.orEmpty()
                    )
                }
            }
        }
    }
}

@Composable
fun LoadingContent(
    onAction: (ShoppingListDetailAction) -> Unit,
    title: String
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBar(title = title, onBackClick = { onAction(ShoppingListDetailAction.OnBackClick) })

        CircularProgressIndicator(
            color = Buttons,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun ContentWithItems(
    state: ShoppingListDetailState,
    onAction: (ShoppingListDetailAction) -> Unit,
    lazyListState: LazyListState
) {
    val total = state.listItems.size
    val completed = state.listItems.count { it?.isChecked ?: false }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TopBar(
            title = state.shoppingList?.name.orEmpty(),
            onBackClick = { onAction(ShoppingListDetailAction.OnBackClick) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        TaskProgressCircle(
            total = total,
            completed = completed,
            circleSize = 80.dp,
            strokeWidth = 8.dp
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyListItems(
            lists = state.listItems,
            scrollState = lazyListState,
            onAction = { action ->
                if (action is ShoppingListDetailAction.OnCheckClick) {
                    onAction(action)
                }
            }
        )
    }
}

@Composable
fun EmptyListContent(
    onAction: (ShoppingListDetailAction) -> Unit,
    title: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar(
            title = title,
            onBackClick = { onAction(ShoppingListDetailAction.OnBackClick) }
        )

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Your list is empty,",
                    color = Color.Gray,
                    fontSize = 16.sp
                )
                Text(
                    text = "Click the button below to add an item now",
                    color = Color.Gray,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(10.dp))
                AddButton(
                    onClick = { onAction(ShoppingListDetailAction.OnAddClick) },
                    text = "Add item",
                    color = Buttons,
                    icon = Icons.Default.Add
                )
            }
        }
    }
}

