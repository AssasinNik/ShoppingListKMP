package com.cherenkov.shoppinglist.shopping.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class UsersShoppingListsDTO(
    val shop_list: List<Shop> = emptyList(),
    val success: Boolean
)

@Serializable
data class Shop(
    val created: String,
    val id: Int,
    val name: String
)