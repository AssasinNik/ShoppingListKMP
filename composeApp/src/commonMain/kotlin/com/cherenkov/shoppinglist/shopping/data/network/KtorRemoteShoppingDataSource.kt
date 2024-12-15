package com.cherenkov.shoppinglist.shopping.data.network

import com.cherenkov.shoppinglist.core.data.safeCall
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
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

private const val BASE_URL = "https://cyberprot.ru/shopping/v2"

class KtorRemoteShoppingDataSource(
    private val httpClient: HttpClient
) : RemoteShoppingDataSource{
    override suspend fun searchShoppings(
        key: String
    ): Result<UsersShoppingListsDTO, DataError.Remote>{
        return safeCall {
            httpClient.get(
                urlString = "$BASE_URL/GetAllMyShopLists"
            ){
                parameter("key", key)
            }
        }
    }

    override suspend fun searchItemsForList(id: Int): Result<UsersListItemsDTO, DataError.Remote> {
        return safeCall {
            httpClient.get(
                urlString = "$BASE_URL/GetShoppingList"
            ){
                parameter("list_id", id)
            }
        }
    }

    override suspend fun deleteShoppingLists(id: Int): Result<RemoveShoppingListDTO, DataError.Remote> {
        return safeCall {
            httpClient.get(
                urlString = "$BASE_URL/RemoveShoppingList"
            ){
                parameter("list_id", id)
            }
        }
    }

    override suspend fun addShoppingList(name: String, key: String): Result<AddShoppingListDTO, DataError.Remote> {
        return safeCall {
            httpClient.get(
                urlString = "$BASE_URL/CreateShoppingList"
            ){
                parameter("key", key)
                parameter("name", name)
            }
        }
    }

    override suspend fun deleteItem(
        list_id: Int,
        item_id: Int
    ): Result<RemoveShoppingListDTO, DataError.Remote> {
        return safeCall {
            httpClient.get(
                urlString = "$BASE_URL/RemoveFromList"
            ) {
                parameter("list_id", list_id)
                parameter("item_id", item_id)
            }
        }
    }

    override suspend fun crossItem(id: Int): Result<CrossItOffDTO, DataError.Remote> {
        return safeCall {
            httpClient.get(
                urlString = "$BASE_URL/CrossItOff"
            ){
                parameter("id", id)
            }
        }
    }

    override suspend fun authenticateWithKey(key: String): Result<AuthenticateDTO, DataError.Remote> {
        return safeCall {
            httpClient.get(
                urlString = "$BASE_URL/Authentication"
            ){
                parameter("key", key)
            }
        }
    }

    override suspend fun addItemtoList(
        id: Int,
        value: String,
        n: Int
    ): Result<AddItemTOListDTO, DataError.Remote> {
        return safeCall {
            httpClient.get(
                urlString = "$BASE_URL/AddToShoppingList"
            ){
                parameter("id", id)
                parameter("value", value)
                parameter("n", n)
            }
        }
    }

    override suspend fun generateCode(): Result<CodeGenerationDTO, DataError.Remote> {
        return safeCall {
            httpClient.get(
                urlString = "$BASE_URL/CreateTestKey"
            )
        }
    }

}