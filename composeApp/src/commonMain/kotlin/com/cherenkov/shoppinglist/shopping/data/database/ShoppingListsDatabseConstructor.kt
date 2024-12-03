package com.cherenkov.shoppinglist.shopping.data.database

import androidx.room.RoomDatabaseConstructor

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object ShoppingListsDatabseConstructor: RoomDatabaseConstructor<ShoppingListsDatabase>{
    override fun initialize(): ShoppingListsDatabase
}