package com.cherenkov.shoppinglist.shopping.domain

import com.cherenkov.shoppinglist.core.domain.DataError
import com.cherenkov.shoppinglist.core.domain.EmptyResult
import com.cherenkov.shoppinglist.core.domain.Result
import kotlinx.coroutines.flow.Flow

interface ShoppingRepository {
    suspend fun searchShoppings(key: String): Result<List<ShoppingList>, DataError.Remote>
    suspend fun searchItemsForList(id: Int): Result<List<ProductItem>, DataError.Remote>

    suspend fun getShoppingsFromLocal(): Flow<List<ShoppingList>>
    suspend fun addShopping(shoppingList: ShoppingList): EmptyResult<DataError.Local>
    suspend fun deleteAllShoppings(): EmptyResult<DataError.Local>
}