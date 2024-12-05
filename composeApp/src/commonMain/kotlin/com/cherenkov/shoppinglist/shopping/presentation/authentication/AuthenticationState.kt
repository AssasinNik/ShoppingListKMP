package com.cherenkov.shoppinglist.shopping.presentation.authentication

data class AuthenticationState(
    val code: String = "",
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
    val authenticationStatus: Boolean = false,
)