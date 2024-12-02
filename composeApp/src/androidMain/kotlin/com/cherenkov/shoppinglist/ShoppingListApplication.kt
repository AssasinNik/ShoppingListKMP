package com.cherenkov.shoppinglist

import android.app.Application
import com.cherenkov.shoppinglist.di.initKoin
import org.koin.android.ext.koin.androidContext

class ShoppingListApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@ShoppingListApplication)
        }
    }
}