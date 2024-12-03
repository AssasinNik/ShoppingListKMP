package com.cherenkov.shoppinglist.shopping.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShoppingList(shoppingList: ShoppingListEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProductItem(productItem: ProductItemEntity)

    @Query("SELECT * FROM shopping_list")
    fun getAllShoppingLists(): Flow<List<ShoppingListEntity>>

    @Query("SELECT * FROM product_item WHERE shopping_list_id = :shoppingListId")
    fun getProductsByShoppingListId(shoppingListId: Int): Flow<List<ProductItemEntity>>

    @Query("DELETE FROM shopping_list")
    suspend fun deleteAllShoppingLists()

    @Query("DELETE FROM product_item")
    suspend fun deleteAllProductItems()

    @Delete
    suspend fun deleteShoppingList(shoppingList: ShoppingListEntity)

    @Delete
    suspend fun deleteProductItem(productItem: ProductItemEntity)
}