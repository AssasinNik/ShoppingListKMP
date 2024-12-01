package com.cherenkov.shoppinglist.shopping.data.mappers

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