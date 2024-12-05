package com.cherenkov.shoppinglist.shopping.presentation.shopping_lists.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.cherenkov.shoppinglist.shopping.domain.ShoppingList

@Composable
fun ShoppingLazyList(
    lists: List<ShoppingList>,
    onListClick: (ShoppingList) -> Unit,
    onRemoveClick: (ShoppingList) -> Unit,
    modifier: Modifier = Modifier,
    scrollState: LazyListState = rememberLazyListState()
) {
    if (lists.isEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = "No Items",
                tint = Color.Gray,
                modifier = Modifier.size(80.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "No items in your shopping list.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }
    } else {
        LazyColumn(
            modifier = modifier.padding(16.dp),
            state = scrollState,
            verticalArrangement = Arrangement.spacedBy(8.dp), // Более плотное расположение элементов
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(
                items = lists,
                key = { list -> list.id }
            ) { list ->
                ListItem(
                    list = list,
                    modifier = Modifier
                        .widthIn(max = 700.dp)
                        .fillMaxWidth(),
                    onListClick = { onListClick(list) },
                    onRemoveClick = { onRemoveClick(list) }
                )
            }
        }
    }
}