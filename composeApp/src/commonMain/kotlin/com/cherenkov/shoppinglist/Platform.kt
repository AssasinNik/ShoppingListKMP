package com.cherenkov.shoppinglist

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform