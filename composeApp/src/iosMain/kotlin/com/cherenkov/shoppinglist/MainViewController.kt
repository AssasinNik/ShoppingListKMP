package com.cherenkov.shoppinglist

import androidx.compose.ui.window.ComposeUIViewController
import com.cherenkov.shoppinglist.app.App
import com.cherenkov.shoppinglist.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { App() }