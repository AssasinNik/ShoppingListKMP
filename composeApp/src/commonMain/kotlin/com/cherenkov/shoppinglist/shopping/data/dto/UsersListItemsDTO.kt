package com.cherenkov.shoppinglist.shopping.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class UsersListItemsDTO(
    val item_list: List<Item>,
    val success: Boolean
)

@Serializable
data class Item(
    val created: String,
    val id: Int,
    val is_crossed: Boolean,
    val name: String
)