package com.example.recipevault.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.recipevault.data.local.dao.IngredientDao
import com.example.recipevault.data.local.dao.RecipeDao
import com.example.recipevault.data.local.entity.IngredientEntity
import com.example.recipevault.data.local.entity.RecipeEntity

@Database(
    entities = [RecipeEntity::class, IngredientEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
    abstract fun ingredientDao(): IngredientDao
}
