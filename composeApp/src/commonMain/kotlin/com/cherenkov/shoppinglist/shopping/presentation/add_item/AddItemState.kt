package com.cherenkov.shoppinglist.shopping.presentation.add_item

data class AddItemState(
    val name: String = "",
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
    val createStatus: Boolean = false
)