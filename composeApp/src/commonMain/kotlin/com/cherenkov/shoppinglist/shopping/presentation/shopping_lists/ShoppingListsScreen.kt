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
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
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
import com.cherenkov.shoppinglist.core.presentation.BackGroundItems
import com.cherenkov.shoppinglist.core.presentation.Buttons
import com.cherenkov.shoppinglist.core.presentation.HeaderColor
import com.cherenkov.shoppinglist.core.presentation.UiText
import com.cherenkov.shoppinglist.shopping.domain.ShoppingList
import com.cherenkov.shoppinglist.shopping.presentation.shopping_lists.components.AddListButton
import com.cherenkov.shoppinglist.shopping.presentation.shopping_lists.components.ShoppingLazyList
import org.koin.compose.viewmodel.koinViewModel
import shoppinglist.composeapp.generated.resources.Res
import shoppinglist.composeapp.generated.resources.no_shopping_lists


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
    Spacer(modifier = Modifier.height(15.dp))
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackGround)
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(70.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Your shopping Lists",
                style = TextStyle(
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Medium,
                    color = HeaderColor
                )
            )
        }
        Spacer(modifier = Modifier.height(70.dp))
        Surface(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            color = Buttons,
            shape = RoundedCornerShape(
                topStart = 32.dp,
                topEnd = 32.dp
            )
        ) {

            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                if(state.isLoading){
                    CircularProgressIndicator(
                        color = BackGround
                    )
                }
                else {
                    if(state.errorMessage != null){
                        Text(
                            text = state.errorMessage.asString(),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                    else{
                        if (!state.listItems.isEmpty())
                        ShoppingLazyList(
                            lists = state.listItems,
                            onListClick = { onAction(ShoppingListAction.OnListClick(it)) },
                            onRemoveClick = { onAction(ShoppingListAction.OnRemoveListClick(it)) },
                            modifier = Modifier.fillMaxSize(),
                            scrollState = lazyListState
                        )
                        else{
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
                                    tint = BackGround,
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
                                        color = BackGround
                                    )
                                )
                                AddListButton(
                                    onClick = {
                                        onAction(ShoppingListAction.OnAddListClick)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}