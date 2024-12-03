package com.cherenkov.shoppinglist.shopping.data.network

import com.cherenkov.shoppinglist.core.domain.DataError
import com.cherenkov.shoppinglist.core.domain.Result
import com.cherenkov.shoppinglist.shopping.data.dto.RemoveShoppingListDTO
import com.cherenkov.shoppinglist.shopping.data.dto.UsersListItemsDTO
import com.cherenkov.shoppinglist.shopping.data.dto.UsersShoppingListsDTO

interface RemoteShoppingDataSource {

    suspend fun searchShoppings(
        key: String
    ): Result<UsersShoppingListsDTO, DataError.Remote>

    suspend fun searchItemsForList(
        id: Int
    ): Result<UsersListItemsDTO, DataError.Remote>

    suspend fun deleteShoppingLists(
        id: Int
    ): Result<RemoveShoppingListDTO, DataError.Remote>

    suspend fun addShoppingList(
        name: String
    ): Result<RemoveShoppingListDTO, DataError.Remote>

}