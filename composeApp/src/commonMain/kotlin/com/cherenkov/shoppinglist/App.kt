package com.cherenkov.shoppinglist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.cherenkov.shoppinglist.shopping.presentation.shopping_lists.ShoppingListsScreenRoot
import com.cherenkov.shoppinglist.shopping.presentation.shopping_lists.ShoppingListsViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    ShoppingListsScreenRoot(
        viewModel = remember { ShoppingListsViewModel() },
        onListClick = {

        }
    )
}