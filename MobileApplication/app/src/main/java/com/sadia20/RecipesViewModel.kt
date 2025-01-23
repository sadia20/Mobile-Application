package com.sadia20

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class RecipesViewModel: ViewModel() {

    // Sample list of recipes
    private val allRecipes = listOf(
        Recipe(1, "Spaghetti Bologna", "A classic Italian dish."),
        Recipe(2, "Chicken Soup", "Chicken soup with a splash of sour cream."),
        Recipe(3, "Fruits Bowl", "Consists of different fruits."),
        Recipe(4, "Pancakes", "A sweet breakfast favorite."),
        Recipe(5, "Pasta with tomato sauce", "Pasta with a rich tomato sauce."),
    )

    // StateFlow to handle UI state
    private val _uiState = MutableStateFlow(UiState(isLoading = false, recipes = allRecipes))
    val uiState: StateFlow<UiState> = _uiState

    // StateFlow to expose filtered recipes(Recipes Searcher using ViewModel and StateFlow)
    private val _recipesStateFlow = MutableStateFlow(allRecipes)
    val recipesStateFlow: StateFlow<List<Recipe>> = _recipesStateFlow

    private val queryFlow = MutableStateFlow("")

    // Initialize the ViewModel
    init {
        viewModelScope.launch {
            queryFlow
                .debounce(300) // Wait 300ms for typing to stop
                .collect { query ->
                    _uiState.value = _uiState.value.copy(isLoading = true) // Show loading
                    delay(2000) // Simulate loading time
                    val filteredRecipes = if (query.length < 3) {
                        allRecipes
                    } else {
                        allRecipes.filter {
                            it.title.contains(query, ignoreCase = true) ||
                                    it.description.contains(query, ignoreCase = true)
                        }
                    }
                    _uiState.value = UiState(isLoading = false, recipes = filteredRecipes)
                }
        }
    }

    fun updateQuery(query: String) {
        queryFlow.value = query
    }

    // Function to search for recipes based on a query(Recipes Searcher using ViewModel and StateFlow)
    fun searchRecipeName(query: String) {
        if (query.length < 3) {
            // If the query is less than 3 characters, show all recipes
            _recipesStateFlow.value = allRecipes
        } else {
            val filteredRecipes = allRecipes.filter { recipe ->
                recipe.title.contains(query, ignoreCase = true) ||
                        recipe.description.contains(query, ignoreCase = true)
            }
            // Update the state only if the result is different from the previous one
            if (filteredRecipes != _recipesStateFlow.value) {
                _recipesStateFlow.update { filteredRecipes }
            }
        }
    }
}