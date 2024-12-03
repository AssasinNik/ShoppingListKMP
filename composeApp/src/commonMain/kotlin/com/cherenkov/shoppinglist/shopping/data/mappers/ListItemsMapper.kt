package com.cherenkov.shoppinglist.shopping.data.mappers

import com.cherenkov.shoppinglist.shopping.data.database.ProductItemEntity
import com.cherenkov.shoppinglist.shopping.data.dto.UsersListItemsDTO
import com.cherenkov.shoppinglist.shopping.domain.ProductItem

fun UsersListItemsDTO.toProductItems(): List<ProductItem>{
    return item_list.map { item ->
        ProductItem(
            id = item.id,
            name = item.name,
            isChecked = item.is_crossed,
            created = item.created
        )
    }
}

fun ProductItemEntity.toProductItem():ProductItem{
    return ProductItem(
        id = id,
        name = name,
        isChecked = isChecked,
        created = created
    )
}

fun ProductItem.toProductItemEntity(shoppingListId: Int):ProductItemEntity{
    return ProductItemEntity(
        id = id,
        name = name,
        isChecked = isChecked,
        created = created,
        shoppingListId = shoppingListId
    )
}