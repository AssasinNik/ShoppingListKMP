package com.cherenkov.shoppinglist.shopping.domain

data class ProductItem(
    val created: String,
    val name: String,
    val id: Int,
    val isChecked: Boolean
)
