package com.example.recipevault.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipevault.data.local.entity.RecipeEntity
import com.example.recipevault.data.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val repo: RecipeRepository
) : ViewModel() {

    val recipes: StateFlow<List<RecipeEntity>> =
        repo.getAllRecipes()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun saveRecipe(
        id: Long,
        title: String,
        description: String,
        category: String,
        imageUrl: String
    ) {
        val t = title.trim()
        val d = description.trim()
        val c = category.trim()
        val img = imageUrl.trim()

        if (t.isBlank() || d.isBlank()) return

        viewModelScope.launch {
            if (id == 0L) {
                repo.addRecipe(
                    RecipeEntity(
                        title = t,
                        description = d,
                        category = c,
                        imageUrl = img
                    )
                )
            } else {
                val old = recipes.value.firstOrNull { it.id == id }
                repo.updateRecipe(
                    RecipeEntity(
                        id = id,
                        title = t,
                        description = d,
                        category = c,
                        imageUrl = img,
                        createdAt = old?.createdAt ?: System.currentTimeMillis()
                    )
                )
            }
        }
    }

    fun deleteRecipe(recipe: RecipeEntity) {
        viewModelScope.launch {
            repo.deleteRecipe(recipe)
        }
    }
}
