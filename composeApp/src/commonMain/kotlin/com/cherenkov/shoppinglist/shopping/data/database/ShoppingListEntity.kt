package com.cherenkov.shoppinglist.shopping.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_list")
data class ShoppingListEntity(
    @PrimaryKey(autoGenerate = false) val id: Int = 0,
    val name: String,
    val date: String
)
