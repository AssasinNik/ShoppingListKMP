package com.cherenkov.shoppinglist.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.cherenkov.shoppinglist.core.data.HttpClientFactory
import com.cherenkov.shoppinglist.shopping.data.database.DatabaseFactory
import com.cherenkov.shoppinglist.shopping.data.database.ShoppingListsDatabase
import com.cherenkov.shoppinglist.shopping.data.network.KtorRemoteShoppingDataSource
import com.cherenkov.shoppinglist.shopping.data.network.RemoteShoppingDataSource
import com.cherenkov.shoppinglist.shopping.data.repository.DefaultShoppingRepository
import com.cherenkov.shoppinglist.shopping.domain.ShoppingRepository
import com.cherenkov.shoppinglist.shopping.presentation.SelectedListViewModel
import com.cherenkov.shoppinglist.shopping.presentation.add_item.AddItemViewModel
import com.cherenkov.shoppinglist.shopping.presentation.authentication.AuthenticationViewModel
import com.cherenkov.shoppinglist.shopping.presentation.create_shopping_list.CreateShoppingViewModel
import com.cherenkov.shoppinglist.shopping.presentation.shopping_lists.ShoppingListsViewModel
import com.cherenkov.shoppinglist.shopping.presentation.shoppinglist_detail.ShoppingListDetailViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    single { HttpClientFactory.create(get()) }
    singleOf(::KtorRemoteShoppingDataSource).bind<RemoteShoppingDataSource>()
    singleOf(::DefaultShoppingRepository).bind<ShoppingRepository>()

    single{
        get<DatabaseFactory>().create()
            .setDriver(BundledSQLiteDriver())
            .build()
    }

    single { get<ShoppingListsDatabase>().shoppingListDao }

    viewModelOf(::CreateShoppingViewModel)
    viewModelOf(::ShoppingListsViewModel)
    viewModelOf(::AuthenticationViewModel)
    viewModelOf(::SelectedListViewModel)
    viewModelOf(::ShoppingListDetailViewModel)
    viewModelOf(::AddItemViewModel)
}