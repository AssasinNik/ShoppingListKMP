package com.cherenkov.shoppinglist.shopping.presentation.code_generation

data class CodeGenerationState(
    val firstName: String = "",
    val lastName: String = "",
    val generatedCode: String? = null,
    val showCode: Boolean = false,
    val showNextButton: Boolean = false,
    val errorMessage: String? = null
)
