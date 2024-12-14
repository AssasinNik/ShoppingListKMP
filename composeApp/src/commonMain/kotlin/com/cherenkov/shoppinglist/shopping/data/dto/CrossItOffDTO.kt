package com.cherenkov.shoppinglist.shopping.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CrossItOffDTO (
    val success:Boolean,
    val rows_affected:Int
)