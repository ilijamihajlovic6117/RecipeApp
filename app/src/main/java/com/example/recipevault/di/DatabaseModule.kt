package com.example.recipevault.di

import android.content.Context
import androidx.room.Room
import com.example.recipevault.data.local.AppDatabase
import com.example.recipevault.data.local.dao.IngredientDao
import com.example.recipevault.data.local.dao.RecipeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "recipevault.db"
        ).build()
    }

    @Provides
    fun provideRecipeDao(db: AppDatabase): RecipeDao = db.recipeDao()

    @Provides
    fun provideIngredientDao(db: AppDatabase): IngredientDao = db.ingredientDao()
}
