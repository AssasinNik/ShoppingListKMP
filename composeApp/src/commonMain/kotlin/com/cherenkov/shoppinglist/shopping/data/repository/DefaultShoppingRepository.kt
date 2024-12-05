package com.cherenkov.shoppinglist.shopping.data.repository

import androidx.sqlite.SQLiteException
import com.cherenkov.shoppinglist.core.domain.DataError
import com.cherenkov.shoppinglist.core.domain.EmptyResult
import com.cherenkov.shoppinglist.core.domain.Result
import com.cherenkov.shoppinglist.core.domain.map
import com.cherenkov.shoppinglist.core.domain.onError
import com.cherenkov.shoppinglist.core.domain.onSuccess
import com.cherenkov.shoppinglist.shopping.data.database.ShoppingListDao
import com.cherenkov.shoppinglist.shopping.data.database.UserEntity
import com.cherenkov.shoppinglist.shopping.data.dto.RemoveShoppingListDTO
import com.cherenkov.shoppinglist.shopping.data.mappers.toProductItem
import com.cherenkov.shoppinglist.shopping.data.mappers.toProductItems
import com.cherenkov.shoppinglist.shopping.data.mappers.toShoppingList
import com.cherenkov.shoppinglist.shopping.data.mappers.toShoppingListEntity
import com.cherenkov.shoppinglist.shopping.data.network.RemoteShoppingDataSource
import com.cherenkov.shoppinglist.shopping.domain.ProductItem
import com.cherenkov.shoppinglist.shopping.domain.ShoppingList
import com.cherenkov.shoppinglist.shopping.domain.ShoppingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class DefaultShoppingRepository(
    private val remoteShoppingDataSource: RemoteShoppingDataSource,
    private val shoppingListDao: ShoppingListDao
) : ShoppingRepository{

    override fun getProductsStream(): Flow<List<ShoppingList>> = flow {
        var lastProducts: List<ShoppingList> = emptyList()
        while (true) {
            try {
                val result: Result<List<ShoppingList>, DataError.Remote> = searchShoppings()
                var currentProducts: List<ShoppingList>
                result.onSuccess {
                    currentProducts = it
                    if (currentProducts != lastProducts) {
                        emit(currentProducts)
                        lastProducts = currentProducts
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            delay(5000)
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun searchShoppings(): Result<List<ShoppingList>, DataError.Remote>{
        val key = shoppingListDao.getAuthCode().code
        return remoteShoppingDataSource
            .searchShoppings(key)
            .map { dto ->
                dto.toShoppingList()
            }
    }

    override fun getItemsStream(id: Int): Flow<List<ProductItem>> = flow {
        var lastItems: List<ProductItem> = emptyList()
        while (true) {
            try {
                val result: Result<List<ProductItem>, DataError.Remote> = searchItemsForList(id)
                var currentProducts: List<ProductItem>
                result.onSuccess {
                    currentProducts = it
                    if (currentProducts != lastItems) {
                        emit(currentProducts)
                        lastItems = currentProducts
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            delay(5000)
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun searchItemsForList(id: Int): Result<List<ProductItem>, DataError.Remote> {
        return remoteShoppingDataSource
            .searchItemsForList(id)
            .map { dto ->
                dto.toProductItems()
            }
    }

    override suspend fun getShoppingsFromLocal(): Flow<List<ShoppingList>> {
        return shoppingListDao
            .getAllShoppingLists()
            .map { shoppingListEntities ->
                shoppingListEntities.map { it.toShoppingList() }
            }
    }

    override suspend fun addShopping(shoppingList: ShoppingList): EmptyResult<DataError.Local> {
        return try {
            shoppingListDao.insertShoppingList(shoppingList.toShoppingListEntity())
            Result.Success(Unit)
        } catch (e: SQLiteException){
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun deleteAllShoppings(): EmptyResult<DataError.Local> {
        return try {
            shoppingListDao.deleteAllShoppingLists()
            Result.Success(Unit)
        } catch (e: SQLiteException){
            Result.Error(DataError.Local.UNKNOWN)
        }
    }

    override suspend fun deleteShoppingList(shoppingList: ShoppingList): EmptyResult<DataError.Local> {
        return try {
            shoppingListDao.deleteShoppingList(shoppingList.toShoppingListEntity())
            remoteShoppingDataSource.deleteShoppingLists(shoppingList.id)
            Result.Success(Unit)
        } catch (e: SQLiteException){
            Result.Error(DataError.Local.UNKNOWN)
        }
    }

    override suspend fun addShoppingList(name: String): Result<Boolean, DataError.Remote> {
        val key = shoppingListDao.getAuthCode().code
        remoteShoppingDataSource.addShoppingList(name, key)
        return Result.Success(true)
    }

    override suspend fun additemToList(
        list_id: Int,
        name: String,
        n: Int
    ): Result<Boolean, DataError.Remote> {
        remoteShoppingDataSource.addItemtoList(list_id,name,n).map {
            if (it.success){
                return Result.Success(true)
            }
            else{
                return Result.Error(DataError.Remote.UNKNOWN)
            }
        }
        return Result.Error(DataError.Remote.UNKNOWN)
    }

    override suspend fun authenticateUser(key: String): Result<Boolean, DataError.Remote> {
        remoteShoppingDataSource.authenticateWithKey(key)
            .map { list ->
                if (list.success){
                    shoppingListDao.insertAuthCode(UserEntity(0, key))
                    return Result.Success(true)
                }
                else{
                    return Result.Error(DataError.Remote.UNKNOWN)
                }
            }
        return Result.Error(DataError.Remote.UNKNOWN)
    }
}