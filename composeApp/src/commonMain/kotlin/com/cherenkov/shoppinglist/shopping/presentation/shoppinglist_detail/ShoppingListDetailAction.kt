package com.cherenkov.shoppinglist.shopping.presentation.shoppinglist_detail

import com.cherenkov.shoppinglist.shopping.domain.ShoppingList

sealed interface ShoppingListDetailAction {

    data object OnBackClick: ShoppingListDetailAction
    data class OnCheckClick(val id: Int): ShoppingListDetailAction
    data object OnCompleteClick: ShoppingListDetailAction
    data object OnAddClick: ShoppingListDetailAction
    data class OnRemoveClick(val item_id: Int): ShoppingListDetailAction
    data class OnSelectedListChange(val shoppingList: ShoppingList): ShoppingListDetailAction
}