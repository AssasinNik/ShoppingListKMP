package com.cherenkov.shoppinglist.shopping.data.database

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [ProductItemEntity::class, ShoppingListEntity::class],
    version = 1
)
abstract class ShoppingListsDatabase: RoomDatabase() {
    abstract val shoppingListDao:ShoppingListDao

    companion object{
        const val DB_NAME = "shopping_lists.db"
    }
}