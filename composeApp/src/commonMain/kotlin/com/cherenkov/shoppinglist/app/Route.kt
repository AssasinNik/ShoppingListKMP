package com.cherenkov.shoppinglist.app

import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object ShoppingGraph: Route

    @Serializable
    data object ShoppingLists: Route

    @Serializable
    data class ShoppingListDetail(val id: Int): Route

}