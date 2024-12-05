package com.cherenkov.shoppinglist.shopping.presentation.shoppinglist_detail

import com.cherenkov.shoppinglist.core.presentation.UiText
import com.cherenkov.shoppinglist.shopping.domain.ProductItem
import com.cherenkov.shoppinglist.shopping.domain.ShoppingList

data class ShoppingListDetailState(
    val isLoading: Boolean = false,

    val shoppingList: ShoppingList? = null,
    val errorMessage: String? = null,
    val listItems: List<ProductItem?> = emptyList(),
    val showDialog: Boolean = false
)
