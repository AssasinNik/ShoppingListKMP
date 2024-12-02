package com.cherenkov.shoppinglist.app

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.cherenkov.shoppinglist.core.presentation.BackGround
import com.cherenkov.shoppinglist.shopping.presentation.SelectedListViewModel
import com.cherenkov.shoppinglist.shopping.presentation.shopping_lists.ShoppingListsScreenRoot
import com.cherenkov.shoppinglist.shopping.presentation.shopping_lists.ShoppingListsViewModel
import com.cherenkov.shoppinglist.shopping.presentation.shoppinglist_detail.ShoppingListDetailAction
import com.cherenkov.shoppinglist.shopping.presentation.shoppinglist_detail.ShoppingListDetailScreenRoot
import com.cherenkov.shoppinglist.shopping.presentation.shoppinglist_detail.ShoppingListDetailViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = BackGround
        ) {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = Route.ShoppingGraph
            ){
                navigation<Route.ShoppingGraph>(
                    startDestination = Route.ShoppingLists
                ){
                    composable<Route.ShoppingLists> (
                        exitTransition = { slideOutHorizontally() },
                        popEnterTransition = { slideInHorizontally() }
                    ){
                        val viewModel = koinViewModel<ShoppingListsViewModel>()
                        val selectedListViewModel =
                            it.sharedKoinViewModel<SelectedListViewModel>(navController)

                        LaunchedEffect(true) {
                            selectedListViewModel.onSelectList(null)
                        }

                        ShoppingListsScreenRoot(
                            viewModel = viewModel,
                            onListClick = { list ->
                                selectedListViewModel.onSelectList(list)
                                navController.navigate(
                                    Route.ShoppingListDetail(list.id)
                                )

                            }
                        )
                    }
                    composable<Route.ShoppingListDetail>(
                        enterTransition = { slideInHorizontally { initialOffset ->
                            initialOffset
                        } },
                        exitTransition = { slideOutHorizontally { initialOffset ->
                            initialOffset
                        } }
                    ) { entry ->
                        val viewModel = koinViewModel<ShoppingListDetailViewModel>()
                        val selectedListViewModel =
                            entry.sharedKoinViewModel<SelectedListViewModel>(navController)
                        val selectedBook by selectedListViewModel.selectedList.collectAsStateWithLifecycle()

                        LaunchedEffect(selectedBook) {
                            selectedBook?.let {
                                viewModel.onAction(ShoppingListDetailAction.OnSelectedListChange(it))
                            }
                        }

                        ShoppingListDetailScreenRoot(
                            viewModel = viewModel,
                            onBackClick = {
                                navController.navigate(
                                    Route.ShoppingLists
                                )
                            },
                            onAddClick = {

                            }
                        )
                    }
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