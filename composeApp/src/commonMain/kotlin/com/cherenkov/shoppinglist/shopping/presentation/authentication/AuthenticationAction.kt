package com.cherenkov.shoppinglist.shopping.presentation.authentication

sealed interface AuthenticationAction {
    data object SetCodeNextScreen: AuthenticationAction
    data class SetCodeClick(val code: String) : AuthenticationAction
    data class onValueTextChange(val code: String): AuthenticationAction
}