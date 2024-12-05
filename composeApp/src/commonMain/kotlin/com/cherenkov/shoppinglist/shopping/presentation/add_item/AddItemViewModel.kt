package com.cherenkov.shoppinglist.shopping.presentation.add_item

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.cherenkov.shoppinglist.app.Route
import com.cherenkov.shoppinglist.core.domain.onError
import com.cherenkov.shoppinglist.core.domain.onSuccess
import com.cherenkov.shoppinglist.shopping.domain.ShoppingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddItemViewModel(
    private val repository: ShoppingRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val listId = savedStateHandle.toRoute<Route.AddItem>().id

    private val _state = MutableStateFlow(AddItemState())
    val state = _state
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    private val _addStatus = MutableStateFlow(false)
    val addStatus = _addStatus.asStateFlow()

    fun onAction(action: AddItemAction){
        when(action){
            is AddItemAction.onAddShoppingClick -> {
                addItem(action.name)
            }
            is AddItemAction.onValueTextChange -> {
                _state.update { it.copy(
                    name = action.name
                ) }
            }
            else -> Unit
        }
    }

    private fun addItem(name: String){
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoading = true) }

            val result = repository.additemToList(listId, name, 1)

            result.onSuccess {
                _state.update { it.copy(
                    isLoading = false,
                    errorMessage = null
                ) }
                _addStatus.update { true }
            }.onError {
                _state.update { it.copy(
                    isLoading = false,
                    errorMessage = it.errorMessage
                ) }
                _addStatus.update { false }
            }
        }
    }

}