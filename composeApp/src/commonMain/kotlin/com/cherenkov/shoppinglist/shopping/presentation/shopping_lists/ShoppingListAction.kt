package com.cherenkov.shoppinglist.shopping.presentation.shopping_lists

import com.cherenkov.shoppinglist.shopping.domain.ShoppingList

interface ShoppingListAction {
    data class OnListClick(val shoppingList: ShoppingList): ShoppingListAction
    data class OnRemoveListClick(val shoppingList: ShoppingList): ShoppingListAction
}