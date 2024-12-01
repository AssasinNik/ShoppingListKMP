package com.cherenkov.shoppinglist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.cherenkov.shoppinglist.core.data.HttpClientFactory
import com.cherenkov.shoppinglist.shopping.data.network.KtorRemoteShoppingDataSource
import com.cherenkov.shoppinglist.shopping.data.repository.DefaultShoppingRepository
import com.cherenkov.shoppinglist.shopping.presentation.shopping_lists.ShoppingListsScreenRoot
import com.cherenkov.shoppinglist.shopping.presentation.shopping_lists.ShoppingListsViewModel
import io.ktor.client.engine.HttpClientEngine
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(engine: HttpClientEngine) {
    ShoppingListsScreenRoot(
        viewModel = remember { ShoppingListsViewModel(
            repository = DefaultShoppingRepository(
                remoteShoppingDataSource = KtorRemoteShoppingDataSource(
                    httpClient = HttpClientFactory.create(
                        engine
                    )
                )
            )
        ) },
        onListClick = {

        }
    )
}