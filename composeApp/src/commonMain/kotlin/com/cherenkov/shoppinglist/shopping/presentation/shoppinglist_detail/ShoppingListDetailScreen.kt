package com.cherenkov.shoppinglist.shopping.presentation.shoppinglist_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cherenkov.shoppinglist.core.presentation.BackGround
import com.cherenkov.shoppinglist.core.presentation.BackGroundBox
import com.cherenkov.shoppinglist.core.presentation.BackGroundItems
import com.cherenkov.shoppinglist.core.presentation.Buttons
import com.cherenkov.shoppinglist.shopping.presentation.reusable_components.AddButton
import com.cherenkov.shoppinglist.shopping.presentation.shopping_lists.ShoppingListAction
import com.cherenkov.shoppinglist.shopping.presentation.shoppinglist_detail.components.LazyListItems
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
){

    val lazyListState = rememberLazyListState()

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(BackGround),
        color = BackGround
    ) {
        if (state.isLoading){
            Box(modifier = Modifier
                .fillMaxSize()
            ){
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp).background(BackGround),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = "Back to lists",
                        tint = Color.White,
                        modifier = Modifier.size(27.dp).clickable {
                            onAction(ShoppingListDetailAction.OnBackClick)
                        }
                    )
                    Spacer(Modifier.width(10.dp))
                    Text(
                        text = state.shoppingList?.name.toString(),
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                CircularProgressIndicator(
                    color = BackGroundBox,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
        else{
            if (state.listItems.isNotEmpty()){
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(16.dp).background(BackGround),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Back to lists",
                            tint = Color.White,
                            modifier = Modifier.size(27.dp).clickable {
                                onAction(ShoppingListDetailAction.OnBackClick)
                            }
                        )
                        if (!state.isLoading){
                            Spacer(Modifier.width(10.dp))
                            Text(
                                text = state.shoppingList?.name.toString(),
                                color = Color.White,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    LazyListItems(
                        lists = state.listItems,
                        scrollState = lazyListState,
                        onAction = { action ->
                            when(action){
                                is ShoppingListDetailAction.OnCheckClick -> {
                                    onAction(ShoppingListDetailAction.OnCheckClick(action.id))
                                }
                                else -> Unit
                            }
                        }
                    )
                }
            }
            else{
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(16.dp).background(BackGround),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Back to lists",
                            tint = Color.White,
                            modifier = Modifier.size(27.dp).clickable {
                                onAction(ShoppingListDetailAction.OnBackClick)
                            }
                        )
                        if (!state.isLoading){
                            Spacer(Modifier.width(10.dp))
                            Text(
                                text = state.shoppingList?.name.toString(),
                                color = Color.White,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Box(modifier = Modifier
                        .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ){
                        Column(
                            modifier = Modifier.fillMaxWidth(),
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
                                onClick = {
                                    onAction(ShoppingListDetailAction.OnAddClick)
                                },
                                text = "Add item",
                                color = Buttons,
                                icon = Icons.Default.Add
                            )
                        }
                    }
                }
            }
        }
    }
}