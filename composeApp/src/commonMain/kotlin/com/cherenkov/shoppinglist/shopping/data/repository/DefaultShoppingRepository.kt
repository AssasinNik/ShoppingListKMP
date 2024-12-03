package com.cherenkov.shoppinglist.shopping.data.repository

import androidx.sqlite.SQLiteException
import com.cherenkov.shoppinglist.core.domain.DataError
import com.cherenkov.shoppinglist.core.domain.EmptyResult
import com.cherenkov.shoppinglist.core.domain.Result
import com.cherenkov.shoppinglist.core.domain.map
import com.cherenkov.shoppinglist.core.domain.onSuccess
import com.cherenkov.shoppinglist.shopping.data.database.ShoppingListDao
import com.cherenkov.shoppinglist.shopping.data.mappers.toProductItem
import com.cherenkov.shoppinglist.shopping.data.mappers.toProductItems
import com.cherenkov.shoppinglist.shopping.data.mappers.toShoppingList
import com.cherenkov.shoppinglist.shopping.data.mappers.toShoppingListEntity
import com.cherenkov.shoppinglist.shopping.data.network.RemoteShoppingDataSource
import com.cherenkov.shoppinglist.shopping.domain.ProductItem
import com.cherenkov.shoppinglist.shopping.domain.ShoppingList
import com.cherenkov.shoppinglist.shopping.domain.ShoppingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

class DefaultShoppingRepository(
    private val remoteShoppingDataSource: RemoteShoppingDataSource,
    private val shoppingListDao: ShoppingListDao
) : ShoppingRepository{

    override suspend fun searchShoppings(key: String): Result<List<ShoppingList>, DataError.Remote>{
        val remoteLists = shoppingListDao.getAllShoppingLists()
        return remoteLists.firstOrNull()?.let { list1 ->
            if (list1.isEmpty()){
                val lists = remoteShoppingDataSource
                    .searchShoppings(key)
                    .map { dto ->
                        dto.toShoppingList()
                    }
                val result = lists.map { list ->
                    list.map { it.toShoppingListEntity() }
                }
                result.onSuccess { list ->
                    list.map {
                        shoppingListDao.insertShoppingList(it)
                    }
                }
                return lists
            }
            else{
                return Result.Success(list1.map { it.toShoppingList() })
            }
        } ?: Result.Error(DataError.Remote.UNKNOWN)
    }

    override suspend fun searchItemsForList(id: Int): Result<List<ProductItem>, DataError.Remote> {
        val localResult = shoppingListDao.getProductsByShoppingListId(id)

        return localResult.firstOrNull()?.let { list ->
            if (list.isEmpty()) {
                remoteShoppingDataSource
                    .searchItemsForList(id)
                    .map { dto ->
                        dto.toProductItems()
                    }
            } else {
                Result.Success(list.map { it.toProductItem() })
            }
        } ?: Result.Error(DataError.Remote.UNKNOWN)

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
}