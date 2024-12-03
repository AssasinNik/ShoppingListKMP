package com.cherenkov.shoppinglist.shopping.presentation.shopping_lists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cherenkov.shoppinglist.core.domain.onError
import com.cherenkov.shoppinglist.core.domain.onSuccess
import com.cherenkov.shoppinglist.core.presentation.toUiText
import com.cherenkov.shoppinglist.shopping.domain.ShoppingList
import com.cherenkov.shoppinglist.shopping.domain.ShoppingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ShoppingListsViewModel(
    private val repository: ShoppingRepository
): ViewModel() {


    private val _state = MutableStateFlow(ShoppingListState())
    val state = _state
        .onStart {
            searchShoppings("92EGHS")
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    fun onAction(action: ShoppingListAction){
        when(action){
            is ShoppingListAction.OnRemoveListClick -> {
                removeList(action.shoppingList)
            }
            is ShoppingListAction.OnAddListClick -> {
                addList(action.listName)
            }
            is ShoppingListAction.OnFloatingButtonClick -> {
                _state.update { it.copy(
                    showDialog = !it.showDialog
                ) }
            }
            else -> Unit
        }
    }

    private fun searchShoppings(key:String){
        viewModelScope.launch {
            _state.update{ it.copy(
                isLoading = true
            ) }
            repository
                .searchShoppings(key)
                .onSuccess { searchShoppings ->
                    _state.update { it.copy(
                        isLoading = false,
                        errorMessage = null,
                        listItems = searchShoppings
                    ) }
                }
                .onError { error ->
                    _state.update { it.copy(
                        listItems = emptyList(),
                        isLoading = false,
                        errorMessage = error.toUiText()
                    ) }
                }
        }
    }

    private fun removeList(list:ShoppingList){
        viewModelScope.launch(Dispatchers.IO) {
            repository
                .deleteShoppingList(list)
                .onSuccess { _state.update { listShopping ->
                    listShopping.copy(
                        listItems = listShopping.listItems.filter { it.id != list.id }
                    )
                } }
        }
    }

    private fun addList(name: String){
        viewModelScope.launch(Dispatchers.IO) {
            repository
                .addShoppingList(name)
                .onSuccess {
                    searchShoppings("92EGHS")
                }
                .onError { error ->
                    _state.update { it.copy(
                        errorMessage = error.toUiText()
                    ) }
                }
        }
    }

}