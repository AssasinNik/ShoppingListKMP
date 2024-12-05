package com.cherenkov.shoppinglist.shopping.presentation.add_item


sealed interface AddItemAction {
    data object moveNextScreen: AddItemAction
    data class onAddShoppingClick(val name: String): AddItemAction
    data class onValueTextChange(val name: String): AddItemAction
}