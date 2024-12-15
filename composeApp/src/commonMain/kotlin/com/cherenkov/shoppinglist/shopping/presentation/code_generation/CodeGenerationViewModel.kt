package com.cherenkov.shoppinglist.shopping.presentation.code_generation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cherenkov.shoppinglist.core.domain.onError
import com.cherenkov.shoppinglist.core.domain.onSuccess
import com.cherenkov.shoppinglist.shopping.domain.ShoppingRepository
import com.cherenkov.shoppinglist.shopping.presentation.authentication.AuthenticationState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CodeGenerationViewModel(
    private val repository: ShoppingRepository
) : ViewModel(){
    private val _state = MutableStateFlow(CodeGenerationState())
    val state = _state
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    fun onAction(action: CodeGenerationAction){
        when(action){
            is CodeGenerationAction.GenerateCodeClicked -> {
                generateCode()
            }
            is CodeGenerationAction.LastNameChanged -> {
                _state.update { it.copy(
                    lastName = action.lastName
                ) }
            }
            is CodeGenerationAction.FirstNameChanged -> {
                _state.update { it.copy(
                    firstName = action.firstName
                ) }
            }
            else -> Unit
        }
    }

    fun generateCode(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.generateCode()
                .onSuccess { code ->
                    _state.update { it.copy(
                        generatedCode = code,
                        showCode = true,
                        showNextButton = true
                    ) }
                }
                .onError { message ->
                    _state.update { it.copy(
                        errorMessage = "Oops, something went wrong"
                    ) }
                }
        }
    }


}