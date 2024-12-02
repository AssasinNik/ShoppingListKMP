package com.cherenkov.shoppinglist.shopping.data.mappers

import com.cherenkov.shoppinglist.shopping.data.dto.UsersListItemsDTO
import com.cherenkov.shoppinglist.shopping.domain.ProductItem

fun UsersListItemsDTO.toProductItems(): List<ProductItem>{
    return item_list.map { item ->
        ProductItem(
            id = item.id,
            name = item.name,
            isCrossed = item.is_crossed,
            created = item.created,
            isChecked = false
        )
    }
}