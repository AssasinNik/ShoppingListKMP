package com.cherenkov.shoppinglist.shopping.data.database

import androidx.room.Room
import androidx.room.RoomDatabase
import java.io.File

actual class DatabaseFactory {
    actual fun create(): RoomDatabase.Builder<ShoppingListsDatabase> {
        val os = System.getProperty("os.name").lowercase()
        val userHome = System.getProperty("user.home")
        val appDataDir = when {
            os.contains("win") -> File(System.getenv("APPDATA"), "ShoppingLists")
            os.contains("mac") -> File(userHome, "Library/Application Support/ShoppingLists")
            else -> File(userHome, ".local/share/ShoppingLists")
        }

        if (!appDataDir.exists()){
            appDataDir.mkdirs()
        }

        val dbFile = File(appDataDir, ShoppingListsDatabase.DB_NAME)
        return Room.databaseBuilder(dbFile.absolutePath)
    }
}