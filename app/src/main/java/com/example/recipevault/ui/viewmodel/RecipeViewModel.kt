package com.example.recipevault.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipevault.data.local.dao.IngredientDao
import com.example.recipevault.data.local.entity.IngredientEntity
import com.example.recipevault.data.local.entity.RecipeEntity
import com.example.recipevault.data.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val repo: RecipeRepository,
    private val ingredientDao: IngredientDao
) : ViewModel() {


    val recipes: StateFlow<List<RecipeEntity>> =
        repo.getAllRecipes()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())


    private val _selectedRecipe = MutableStateFlow<RecipeEntity?>(null)
    val selectedRecipe: StateFlow<RecipeEntity?> = _selectedRecipe.asStateFlow()


    private val _selectedIngredients = MutableStateFlow<List<IngredientEntity>>(emptyList())
    val selectedIngredients: StateFlow<List<IngredientEntity>> = _selectedIngredients.asStateFlow()

    private var ingredientsJob: kotlinx.coroutines.Job? = null

    fun selectRecipe(id: Long) {
        viewModelScope.launch {
            repo.getRecipeById(id).collect { r ->
                _selectedRecipe.value = r
            }
        }

        ingredientsJob?.cancel()
        ingredientsJob = viewModelScope.launch {
            ingredientDao.getIngredientsForRecipe(id).collect { list ->
                _selectedIngredients.value = list
            }
        }
    }

    fun saveRecipe(
        id: Long,
        title: String,
        description: String,
        category: String,
        imageUrl: String
    ) {
        viewModelScope.launch {
            val t = title.trim()
            val d = description.trim()

            if (t.isBlank() || d.isBlank()) return@launch

            if (id == 0L) {

                repo.addRecipe(
                    RecipeEntity(
                        title = t,
                        description = d,
                        category = category.trim(),
                        imageUrl = imageUrl.trim()
                    )
                )
            } else {

                repo.updateRecipe(
                    RecipeEntity(
                        id = id,
                        title = t,
                        description = d,
                        category = category.trim(),
                        imageUrl = imageUrl.trim(),
                        createdAt = _selectedRecipe.value?.createdAt ?: System.currentTimeMillis()
                    )
                )
            }
        }
    }

    fun deleteRecipeById(id: Long) {
        viewModelScope.launch {
            repo.deleteById(id)
        }
    }

    fun addIngredient(recipeId: Long, name: String, amount: String) {
        viewModelScope.launch {
            val n = name.trim()
            if (n.isBlank()) return@launch

            ingredientDao.insertIngredient(
                IngredientEntity(
                    recipeId = recipeId,
                    name = n,
                    amount = amount.trim()
                )
            )
        }
    }

    fun deleteIngredientById(ingredientId: Long) {
        viewModelScope.launch {

            val item = _selectedIngredients.value.firstOrNull { it.id == ingredientId } ?: return@launch
            ingredientDao.deleteIngredient(item)
        }
    }
}
