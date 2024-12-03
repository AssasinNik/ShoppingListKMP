package com.cherenkov.shoppinglist.shopping.data.mappers

import com.cherenkov.shoppinglist.shopping.data.database.ShoppingListEntity
import com.cherenkov.shoppinglist.shopping.data.dto.UsersShoppingListsDTO
import com.cherenkov.shoppinglist.shopping.domain.ShoppingList

fun UsersShoppingListsDTO.toShoppingList(): List<ShoppingList> {
    return shop_list.map { shop ->
        ShoppingList(
            name = shop.name,
            id = shop.id,
            date = shop.created
        )
    }
}

fun ShoppingListEntity.toShoppingList(): ShoppingList{
    return ShoppingList(
        id= id,
        name = name,
        date = date
    )
}

fun ShoppingList.toShoppingListEntity(): ShoppingListEntity{
    return ShoppingListEntity(
        id= id,
        name = name,
        date = date
    )
}