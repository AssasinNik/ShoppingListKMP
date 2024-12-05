package com.cherenkov.shoppinglist.shopping.presentation.create_shopping_list

data class CreateShoppingState(
    val name: String = "",
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
    val createStatus: Boolean = false,
)