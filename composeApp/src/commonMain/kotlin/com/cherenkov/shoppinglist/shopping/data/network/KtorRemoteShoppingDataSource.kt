package com.cherenkov.shoppinglist.shopping.data.network

import com.cherenkov.shoppinglist.core.data.safeCall
import com.cherenkov.shoppinglist.core.domain.DataError
import com.cherenkov.shoppinglist.core.domain.Result
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

    override suspend fun addShoppingList(name: String): Result<RemoveShoppingListDTO, DataError.Remote> {
        return safeCall {
            httpClient.get(
                urlString = "$BASE_URL/CreateShoppingList"
            ){
                parameter("name", name)
            }
        }
    }
}