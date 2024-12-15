package com.cherenkov.shoppinglist.shopping.data.network

import com.cherenkov.shoppinglist.core.domain.DataError
import com.cherenkov.shoppinglist.core.domain.Result
import com.cherenkov.shoppinglist.shopping.data.dto.AddItemTOListDTO
import com.cherenkov.shoppinglist.shopping.data.dto.AddShoppingListDTO
import com.cherenkov.shoppinglist.shopping.data.dto.AuthenticateDTO
import com.cherenkov.shoppinglist.shopping.data.dto.CodeGenerationDTO
import com.cherenkov.shoppinglist.shopping.data.dto.CrossItOffDTO
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

    suspend fun deleteItem(
        list_id: Int,
        item_id: Int
    ): Result<RemoveShoppingListDTO, DataError.Remote>

    suspend fun deleteShoppingLists(
        id: Int
    ): Result<RemoveShoppingListDTO, DataError.Remote>

    suspend fun addShoppingList(
        name: String, key: String
    ): Result<AddShoppingListDTO, DataError.Remote>

    suspend fun crossItem(
        id: Int
    ): Result<CrossItOffDTO, DataError.Remote>

    suspend fun authenticateWithKey(
        key: String
    ): Result<AuthenticateDTO, DataError.Remote>

    suspend fun addItemtoList(
        id: Int,
        value: String,
        n: Int
    ): Result<AddItemTOListDTO, DataError.Remote>

    suspend fun generateCode(): Result<CodeGenerationDTO, DataError.Remote>

}