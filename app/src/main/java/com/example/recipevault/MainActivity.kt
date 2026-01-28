package com.example.recipevault

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.recipevault.ui.screens.RecipeListScreen
import com.example.recipevault.ui.theme.RecipeVaultTheme
import com.example.recipevault.ui.viewmodel.RecipeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RecipeVaultTheme {
                val viewModel: RecipeViewModel = hiltViewModel()
                RecipeListScreen(vm = viewModel)
            }
        }
    }
}
