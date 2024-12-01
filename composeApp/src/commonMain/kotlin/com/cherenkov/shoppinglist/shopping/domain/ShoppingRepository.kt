package com.cherenkov.shoppinglist.shopping.domain

import com.cherenkov.shoppinglist.core.domain.DataError
import com.cherenkov.shoppinglist.core.domain.Result
interface ShoppingRepository {
    suspend fun searchShoppings(key: String): Result<List<ShoppingList>, DataError.Remote>
}