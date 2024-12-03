package com.cherenkov.shoppinglist.shopping.data.database

import androidx.room.RoomDatabase

expect class DatabaseFactory {
    fun create(): RoomDatabase.Builder<ShoppingListsDatabase>
}