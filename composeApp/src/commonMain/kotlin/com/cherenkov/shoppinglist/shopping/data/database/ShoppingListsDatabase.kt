package com.cherenkov.shoppinglist.shopping.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [ProductItemEntity::class, ShoppingListEntity::class, UserEntity::class],
    version = 3
)
@ConstructedBy(ShoppingListsDatabseConstructor::class)
abstract class ShoppingListsDatabase: RoomDatabase() {
    abstract val shoppingListDao:ShoppingListDao

    companion object{
        const val DB_NAME = "shopping_lists.db"
    }
}