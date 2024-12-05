package com.cherenkov.shoppinglist.shopping.presentation.create_shopping_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cherenkov.shoppinglist.core.domain.onError
import com.cherenkov.shoppinglist.core.domain.onSuccess
import com.cherenkov.shoppinglist.shopping.domain.ShoppingRepository
import com.cherenkov.shoppinglist.shopping.presentation.authentication.AuthenticationAction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CreateShoppingViewModel(
    private val repository: ShoppingRepository,
): ViewModel() {
    private val _state = MutableStateFlow(CreateShoppingState())
    val state = _state
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    private val _addStatus = MutableStateFlow(false)
    val addStatus = _addStatus.asStateFlow()

    fun onAction(action: CreateShoppingAction){
        when(action){
            is CreateShoppingAction.onAddShoppingClick -> {
                addShopping()
            }
            is CreateShoppingAction.onValueTextChange -> {
                _state.update { it.copy(
                    name = action.name
                ) }
            }
            else -> Unit
        }
    }

    private fun addShopping(){
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoading = true) }

            val result = repository.addShoppingList(_state.value.name)

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