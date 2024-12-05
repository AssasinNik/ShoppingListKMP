package com.cherenkov.shoppinglist.shopping.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class AddShoppingListDTO(
    val success: Boolean,
    val list_id: Int
)