package com.cherenkov.shoppinglist.shopping.presentation

import androidx.lifecycle.ViewModel
import com.cherenkov.shoppinglist.shopping.domain.ShoppingList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SelectedListViewModel: ViewModel() {

    private val _selectedList = MutableStateFlow<ShoppingList?>(null)
    val selectedList = _selectedList.asStateFlow()

    fun onSelectList(list: ShoppingList?){
        _selectedList.value = list
    }

}