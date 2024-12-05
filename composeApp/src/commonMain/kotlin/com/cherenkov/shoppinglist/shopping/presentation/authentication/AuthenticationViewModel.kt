package com.cherenkov.shoppinglist.shopping.presentation.authentication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cherenkov.shoppinglist.core.domain.onError
import com.cherenkov.shoppinglist.core.domain.onSuccess
import com.cherenkov.shoppinglist.shopping.domain.ShoppingRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthenticationViewModel(
    private val repository: ShoppingRepository,
): ViewModel() {

    private val _state = MutableStateFlow(AuthenticationState())
    val state = _state
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    private val _authenticationStatus = MutableStateFlow(false)
    val authenticationStatus = _authenticationStatus.asStateFlow()

    fun onAction(action: AuthenticationAction){
        when(action){
            is AuthenticationAction.SetCodeClick -> {
                authenticateWithKey()
            }
            is AuthenticationAction.onValueTextChange -> {
                _state.update { it.copy(
                    code = action.code
                ) }
            }
            else -> Unit
        }
    }

    private fun authenticateWithKey(){
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            val result = repository.authenticateUser(_state.value.code)

            result.onSuccess {
                _state.update { it.copy(
                    isLoading = false,
                    errorMessage = null,
                    authenticationStatus = true
                ) }
                _authenticationStatus.update { true }
            }.onError {
                _state.update { it.copy(
                    isLoading = false,
                    errorMessage = "Oops, something went wrong"
                ) }
                _authenticationStatus.update { false }
            }
        }
    }
}