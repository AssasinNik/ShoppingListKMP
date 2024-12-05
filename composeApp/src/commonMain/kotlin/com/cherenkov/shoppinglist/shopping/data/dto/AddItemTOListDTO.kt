package com.cherenkov.shoppinglist.shopping.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class AddItemTOListDTO(
    val success: Boolean,
    val item_id: Int
)