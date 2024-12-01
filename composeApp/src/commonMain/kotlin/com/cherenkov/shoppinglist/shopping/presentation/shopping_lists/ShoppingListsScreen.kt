package com.cherenkov.shoppinglist.shopping.presentation.shopping_lists

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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import com.cherenkov.shoppinglist.core.presentation.Red
import com.cherenkov.shoppinglist.shopping.domain.ShoppingList
import com.cherenkov.shoppinglist.shopping.presentation.shopping_lists.components.ShoppingLazyList
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun ShoppingListsScreenRoot(
    viewModel: ShoppingListsViewModel = koinViewModel(),
    onListClick: (ShoppingList) -> Unit
){
    val state by viewModel.state.collectAsStateWithLifecycle()

    ShoppingListsScreen(
        state = state,
        onAction = { action ->
            when(action){
                is ShoppingListAction.OnListClick -> onListClick(action.shoppingList)
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )

}

@Composable
private fun ShoppingListsScreen(
    state: ShoppingListState,
    onAction: (ShoppingListAction) -> Unit
){

    val lazyListState = rememberLazyListState()

    LaunchedEffect(state.listItems){
        lazyListState.animateScrollToItem(0)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(15.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Hi 👋",
                style = TextStyle(
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "These are your shopping lists!",
                style = TextStyle(
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Happy shopping! 🛒",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Light,
                    color = Red
                )
            )
        }

        Spacer(modifier = Modifier.height(28.dp))
        Surface(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            color = BackGround,
            shape = RoundedCornerShape(
                topStart = 32.dp,
                topEnd = 32.dp
            )
        ) {
            ShoppingLazyList(
                lists = state.listItems,
                onListClick = { onAction(ShoppingListAction.OnListClick(it)) },
                onRemoveClick = { onAction(ShoppingListAction.OnRemoveListClick(it)) },
                modifier = Modifier.fillMaxSize(),
                scrollState = lazyListState
            )
        }
    }
}