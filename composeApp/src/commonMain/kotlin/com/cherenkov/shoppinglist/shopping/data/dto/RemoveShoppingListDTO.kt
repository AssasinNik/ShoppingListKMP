package com.cherenkov.shoppinglist.shopping.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class RemoveShoppingListDTO(
    val success: Boolean,
    val new_value: Boolean
)