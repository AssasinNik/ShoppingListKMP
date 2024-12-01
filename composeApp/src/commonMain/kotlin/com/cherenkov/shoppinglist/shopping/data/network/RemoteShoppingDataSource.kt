package com.cherenkov.shoppinglist.shopping.data.network

import com.cherenkov.shoppinglist.core.domain.DataError
import com.cherenkov.shoppinglist.core.domain.Result
import com.cherenkov.shoppinglist.shopping.data.dto.UsersShoppingListsDTO

interface RemoteShoppingDataSource {

    suspend fun searchShoppings(
        key: String
    ): Result<UsersShoppingListsDTO, DataError.Remote>

}