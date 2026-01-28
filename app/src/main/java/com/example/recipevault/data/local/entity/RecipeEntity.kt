package com.example.recipevault.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class RecipeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    val title: String,
    val description: String,

    val category: String = "",
    val imageUrl: String = "",

    val createdAt: Long = System.currentTimeMillis()
)
