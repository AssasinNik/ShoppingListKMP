package com.cherenkov.shoppinglist.core.domain

import com.cherenkov.shoppinglist.core.domain.Error

sealed interface DataError: Error {

    enum class Remote: DataError {
        REQUEST_TIMEOUT,
        TOO_MANY_REQUESTS,
        NO_INTERNET,
        SERVER,
        SERIALIZATION,
        UNKNOWN
    }

    enum class Local: DataError {
        DISK_FULL,
        UNKNOWN
    }

}