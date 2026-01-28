package com.example.recipevault.data.repository

import com.example.recipevault.data.local.dao.IngredientDao
import com.example.recipevault.data.local.dao.RecipeDao
import com.example.recipevault.data.local.entity.IngredientEntity
import com.example.recipevault.data.local.entity.RecipeEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecipeRepository @Inject constructor(
    private val recipeDao: RecipeDao,
    private val ingredientDao: IngredientDao
) {
    fun getAllRecipes(): Flow<List<RecipeEntity>> = recipeDao.getAllRecipes()
    fun getRecipeById(id: Long): Flow<RecipeEntity?> = recipeDao.getRecipeById(id)

    suspend fun addRecipe(recipe: RecipeEntity): Long = recipeDao.insertRecipe(recipe)
    suspend fun updateRecipe(recipe: RecipeEntity) = recipeDao.updateRecipe(recipe)
    suspend fun deleteRecipe(recipe: RecipeEntity) = recipeDao.deleteRecipe(recipe)
    suspend fun deleteById(id: Long) = recipeDao.deleteById(id)

    fun getIngredients(recipeId: Long): Flow<List<IngredientEntity>> =
        ingredientDao.getIngredientsForRecipe(recipeId)

    suspend fun addIngredient(ingredient: IngredientEntity): Long =
        ingredientDao.insertIngredient(ingredient)

    suspend fun updateIngredient(ingredient: IngredientEntity) =
        ingredientDao.updateIngredient(ingredient)

    suspend fun deleteIngredientById(id: Long) =
        ingredientDao.deleteById(id)

    suspend fun deleteAllIngredientsForRecipe(recipeId: Long) =
        ingredientDao.deleteAllForRecipe(recipeId)
}
