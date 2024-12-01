package com.cherenkov.shoppinglist.shopping.presentation.shopping_lists.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cherenkov.shoppinglist.shopping.domain.ShoppingList

@Composable
fun ShoppingLazyList(
    lists: List<ShoppingList>,
    onListClick: (ShoppingList) -> Unit,
    onRemoveClick: (ShoppingList) -> Unit,
    modifier: Modifier = Modifier,
    scrollState: LazyListState = rememberLazyListState()
){
    LazyColumn(
        modifier = modifier,
        state = scrollState,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(
            items =lists,
            key = { it.id }
        ){ list ->
            ListItem(
                list = list,
                modifier = Modifier
                    .widthIn(max = 700.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                onListClick = {
                    onListClick(list)
                },
                onRemoveClick = {
                    onRemoveClick(list)
                }
            )
        }
    }
}