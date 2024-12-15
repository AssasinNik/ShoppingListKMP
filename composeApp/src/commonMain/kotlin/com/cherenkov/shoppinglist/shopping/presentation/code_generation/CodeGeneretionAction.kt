package com.cherenkov.shoppinglist.shopping.presentation.code_generation

sealed interface CodeGenerationAction {
    data class FirstNameChanged(val firstName: String) : CodeGenerationAction
    data class LastNameChanged(val lastName: String) : CodeGenerationAction
    object GenerateCodeClicked : CodeGenerationAction
    object NextScreenClicked : CodeGenerationAction
    object AlreadyHaveCodeClicked : CodeGenerationAction
}