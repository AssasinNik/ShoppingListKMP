package com.cherenkov.shoppinglist.shopping.data.network

import com.cherenkov.shoppinglist.core.domain.DataError
import com.cherenkov.shoppinglist.core.domain.Result
import com.cherenkov.shoppinglist.shopping.data.dto.UsersListItemsDTO
import com.cherenkov.shoppinglist.shopping.data.dto.UsersShoppingListsDTO

interface RemoteShoppingDataSource {

    suspend fun searchShoppings(
        key: String
    ): Result<UsersShoppingListsDTO, DataError.Remote>

    suspend fun searchItemsForList(
        id: Int
    ): Result<UsersListItemsDTO, DataError.Remote>

}