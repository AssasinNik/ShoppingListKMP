package com.cherenkov.shoppinglist.shopping.presentation.shopping_lists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cherenkov.shoppinglist.core.domain.onError
import com.cherenkov.shoppinglist.core.domain.onSuccess
import com.cherenkov.shoppinglist.core.presentation.toUiText
import com.cherenkov.shoppinglist.shopping.domain.ShoppingRepository
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
            is ShoppingListAction.OnListClick -> {

            }
            is ShoppingListAction.OnRemoveListClick -> {

            }
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

}