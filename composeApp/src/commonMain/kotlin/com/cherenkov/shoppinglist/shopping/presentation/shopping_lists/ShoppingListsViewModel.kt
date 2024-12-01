package com.cherenkov.shoppinglist.shopping.presentation.shopping_lists

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ShoppingListsViewModel: ViewModel() {

    private val _state = MutableStateFlow(ShoppingListState())
    val state = _state.asStateFlow()

    fun onAction(action: ShoppingListAction){
        when(action){
            is ShoppingListAction.OnListClick -> {

            }
            is ShoppingListAction.OnRemoveListClick -> {

            }
        }
    }

}