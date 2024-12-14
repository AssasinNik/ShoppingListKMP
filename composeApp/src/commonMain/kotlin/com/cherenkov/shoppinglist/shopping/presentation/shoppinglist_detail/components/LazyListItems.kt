package com.cherenkov.shoppinglist.shopping.presentation.shoppinglist_detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cherenkov.shoppinglist.shopping.domain.ProductItem
import com.cherenkov.shoppinglist.shopping.presentation.shoppinglist_detail.ShoppingListDetailAction

@Composable
fun LazyListItems(
    lists: List<ProductItem?>,
    modifier: Modifier = Modifier,
    scrollState: LazyListState = rememberLazyListState(),
    onAction: (ShoppingListDetailAction) -> Unit
){
    LazyColumn(
        modifier = modifier,
        state = scrollState,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
        items(
            items =lists,
            key = { it?.id ?: "" }
        ){ item ->
            if (item != null) {
                ShoppingListItem(
                    item = item,
                    onCheckedChange = {
                        onAction(ShoppingListDetailAction.OnCheckClick(item.id))
                    },
                    onRemoveClick = {
                        onAction(ShoppingListDetailAction.OnRemoveClick(item_id = item.id))
                    }
                )
            }
        }
        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}