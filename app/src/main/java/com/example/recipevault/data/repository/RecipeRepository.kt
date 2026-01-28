package com.example.recipevault.data.repository

import com.example.recipevault.data.local.dao.RecipeDao
import com.example.recipevault.data.local.entity.RecipeEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecipeRepository @Inject constructor(
    private val recipeDao: RecipeDao
) {
    fun getAllRecipes(): Flow<List<RecipeEntity>> = recipeDao.getAllRecipes()

    fun getRecipeById(id: Long): Flow<RecipeEntity?> = recipeDao.getRecipeById(id)

    suspend fun addRecipe(recipe: RecipeEntity): Long = recipeDao.insertRecipe(recipe)

    suspend fun updateRecipe(recipe: RecipeEntity) = recipeDao.updateRecipe(recipe)

    suspend fun deleteRecipe(recipe: RecipeEntity) = recipeDao.deleteRecipe(recipe)

    suspend fun deleteById(id: Long) = recipeDao.deleteById(id)
}
