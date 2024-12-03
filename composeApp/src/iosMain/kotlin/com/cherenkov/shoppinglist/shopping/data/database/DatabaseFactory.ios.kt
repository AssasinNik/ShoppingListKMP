@file:OptIn(ExperimentalForeignApi::class)

package com.cherenkov.shoppinglist.shopping.data.database

import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

actual class DatabaseFactory {
    actual fun create(): RoomDatabase.Builder<ShoppingListsDatabase> {
        val dbFile = documentDirectory() + "/${ShoppingListsDatabase.DB_NAME}"
        return Room.databaseBuilder<ShoppingListsDatabase>(
            name = dbFile
        )
    }

    private fun documentDirectory(): String{
        val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null
        )
        return requireNotNull(documentDirectory?.path())
    }

}