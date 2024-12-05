package com.cherenkov.shoppinglist.shopping.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cherenkov.shoppinglist.core.domain.onError
import com.cherenkov.shoppinglist.core.domain.onSuccess
import com.cherenkov.shoppinglist.shopping.domain.ShoppingList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SelectedListViewModel: ViewModel() {

    private val _selectedList = MutableStateFlow<ShoppingList?>(null)
    val selectedList = _selectedList.asStateFlow()

    fun onSelectList(list: ShoppingList?){
        _selectedList.value = list
    }

}