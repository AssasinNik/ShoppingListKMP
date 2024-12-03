package com.cherenkov.shoppinglist.shopping.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "product_item",
    foreignKeys = [
        ForeignKey(
            entity = ShoppingListEntity::class,
            parentColumns = ["id"],
            childColumns = ["shopping_list_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ProductItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "shopping_list_id")
    val shoppingListId: Int?,
    val created: String,
    val name: String,
    val isChecked: Boolean
)