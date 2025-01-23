package com.sadia20

data class UiState(
    val isLoading: Boolean = false,
    val recipes: List<Recipe> = emptyList()
)
