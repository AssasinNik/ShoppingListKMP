package com.cherenkov.shoppinglist.shopping.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey val id: Int = 0,
    val code: String
)