package com.cherenkov.shoppinglist

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.cherenkov.shoppinglist.app.App
import com.cherenkov.shoppinglist.di.initKoin

fun main() = application {
    initKoin()
    Window(
        onCloseRequest = ::exitApplication,
        title = "ShoppingList",
    ) {
        App()
    }
}