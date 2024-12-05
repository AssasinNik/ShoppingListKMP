package com.cherenkov.shoppinglist.shopping.presentation.create_shopping_list


sealed interface CreateShoppingAction {
    data object moveNextScreen: CreateShoppingAction
    data class onAddShoppingClick(val name: String): CreateShoppingAction
    data class onValueTextChange(val name: String): CreateShoppingAction
}