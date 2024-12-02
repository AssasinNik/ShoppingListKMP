package com.cherenkov.shoppinglist.app

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.cherenkov.shoppinglist.shopping.presentation.SelectedListViewModel
import com.cherenkov.shoppinglist.shopping.presentation.shopping_lists.ShoppingListsScreenRoot
import com.cherenkov.shoppinglist.shopping.presentation.shopping_lists.ShoppingListsViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    MaterialTheme {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = Route.ShoppingGraph
        ){
            navigation<Route.ShoppingGraph>(
                startDestination = Route.ShoppingLists
            ){
                composable<Route.ShoppingLists> {
                    val viewModel = koinViewModel<ShoppingListsViewModel>()
                    val selectedListViewModel =
                        it.sharedKoinViewModel<SelectedListViewModel>(navController)
                    ShoppingListsScreenRoot(
                        viewModel = viewModel,
                        onListClick = { list ->
                            navController.navigate(
                                Route.ShoppingListDetail(list.id)
                            )

                        }
                    )
                }
                composable<Route.ShoppingListDetail> {
                    val selectedListViewModel =
                        it.sharedKoinViewModel<SelectedListViewModel>(navController)
                }
            }
        }
    }
}

@Composable
private inline fun <reified T: ViewModel> NavBackStackEntry.sharedKoinViewModel(
    navController: NavController
): T{
    val navGraphRoute = destination.parent?.route?:return koinViewModel<T>()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return koinViewModel(
        viewModelStoreOwner = parentEntry
    )
}