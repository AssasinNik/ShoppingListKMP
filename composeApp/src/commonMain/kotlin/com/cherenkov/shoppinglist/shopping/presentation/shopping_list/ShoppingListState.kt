package com.cherenkov.shoppinglist.shopping.presentation.shopping_list

import com.cherenkov.shoppinglist.core.presentation.UiText
import com.cherenkov.shoppinglist.shopping.domain.ShoppingList

data class ShoppingListState(
    val listItems: List<ShoppingList> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: UiText? = null
)
