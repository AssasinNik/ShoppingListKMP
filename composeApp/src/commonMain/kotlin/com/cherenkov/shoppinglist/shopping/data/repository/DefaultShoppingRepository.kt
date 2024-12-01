package com.cherenkov.shoppinglist.shopping.data.repository

import com.cherenkov.shoppinglist.core.domain.DataError
import com.cherenkov.shoppinglist.core.domain.Result
import com.cherenkov.shoppinglist.core.domain.map
import com.cherenkov.shoppinglist.shopping.data.mappers.toShoppingList
import com.cherenkov.shoppinglist.shopping.data.network.RemoteShoppingDataSource
import com.cherenkov.shoppinglist.shopping.domain.ShoppingList
import com.cherenkov.shoppinglist.shopping.domain.ShoppingRepository

class DefaultShoppingRepository(
    private val remoteShoppingDataSource: RemoteShoppingDataSource
) : ShoppingRepository{

    override suspend fun searchShoppings(key: String): Result<List<ShoppingList>, DataError.Remote>{
        return remoteShoppingDataSource
            .searchShoppings(key)
            .map { dto ->
                dto.toShoppingList()
            }
    }

}