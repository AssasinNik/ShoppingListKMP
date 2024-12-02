package com.cherenkov.shoppinglist.shopping.presentation.shoppinglist_detail

import com.cherenkov.shoppinglist.shopping.domain.ShoppingList

sealed interface ShoppingListDetailAction {

    data object OnBackClick: ShoppingListDetailAction
    data object OnCompleteClick: ShoppingListDetailAction
    data class OnSelectedListChange(val shoppingList: ShoppingList): ShoppingListDetailAction
}