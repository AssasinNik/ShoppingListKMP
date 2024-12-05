package com.cherenkov.shoppinglist.shopping.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class AuthenticateDTO(
    val success: Boolean
)