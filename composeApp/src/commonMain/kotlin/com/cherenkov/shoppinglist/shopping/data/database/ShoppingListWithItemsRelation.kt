package com.cherenkov.shoppinglist.shopping.data.database

import androidx.room.Embedded
import androidx.room.Relation

data class ShoppingListWithItems(
    @Embedded
    val shoppingList: ShoppingListEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "shopping_list_id"
    )
    val items: List<ProductItemEntity>
)