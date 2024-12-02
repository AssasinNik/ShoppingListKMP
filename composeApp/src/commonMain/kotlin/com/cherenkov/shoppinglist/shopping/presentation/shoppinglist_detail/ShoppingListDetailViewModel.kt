package com.cherenkov.shoppinglist.shopping.presentation.shoppinglist_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.cherenkov.shoppinglist.app.Route
import com.cherenkov.shoppinglist.core.domain.onError
import com.cherenkov.shoppinglist.core.domain.onSuccess
import com.cherenkov.shoppinglist.core.presentation.toUiText
import com.cherenkov.shoppinglist.shopping.domain.ShoppingRepository
import com.cherenkov.shoppinglist.shopping.presentation.shopping_lists.ShoppingListAction
import com.cherenkov.shoppinglist.shopping.presentation.shopping_lists.ShoppingListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ShoppingListDetailViewModel(
    private val repository: ShoppingRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val listId = savedStateHandle.toRoute<Route.ShoppingListDetail>().id

    private val _state = MutableStateFlow(ShoppingListDetailState())
    val state = _state
        .onStart {
            searchItems()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    fun onAction(action: ShoppingListDetailAction){
        when(action){
            is ShoppingListDetailAction.OnBackClick -> {

            }
            is ShoppingListDetailAction.OnCompleteClick -> {

            }
            is ShoppingListDetailAction.OnSelectedListChange -> {
                _state.update { it.copy(
                    shoppingList = action.shoppingList
                ) }
            }
            is ShoppingListDetailAction.OnAddClick -> {

            }
            is ShoppingListDetailAction.OnCheckClick -> {
                _state.update { currentState ->
                    currentState.copy(
                        listItems = currentState.listItems.map { item ->
                            if (item?.id == action.id) {
                                item.copy(isChecked = !item.isChecked)
                            } else {
                                item
                            }
                        }
                    )
                }
            }
        }
    }

    private fun searchItems(){
        viewModelScope.launch {
            _state.update{ it.copy(
                isLoading = true
            ) }
            repository
                .searchItemsForList(listId)
                .onSuccess { searchItems ->
                    _state.update { it.copy(
                        isLoading = false,
                        errorMessage = null,
                        listItems = searchItems
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