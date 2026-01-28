package com.example.recipevault.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.recipevault.ui.screens.RecipeDetailScreen
import com.example.recipevault.ui.screens.RecipeFormScreen
import com.example.recipevault.ui.screens.RecipeListScreen
import com.example.recipevault.ui.viewmodel.RecipeViewModel

@Composable
fun AppNavGraph() {
    val nav = rememberNavController()
    val vm: RecipeViewModel = hiltViewModel()

    NavHost(navController = nav, startDestination = NavRoutes.LIST) {

        composable(NavRoutes.LIST) {
            RecipeListScreen(
                vm = vm,
                onOpenRecipe = { id -> nav.navigate(NavRoutes.detail(id)) },
                onAdd = { nav.navigate(NavRoutes.form(null)) }
            )
        }

        composable(
            route = NavRoutes.DETAIL,
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong("id") ?: 0L
            if (id == 0L) {
                nav.popBackStack()
                return@composable
            }

            RecipeDetailScreen(
                vm = vm,
                recipeId = id,
                onBack = { nav.popBackStack() },
                onEdit = { nav.navigate(NavRoutes.form(id)) }
            )
        }

        composable(
            route = NavRoutes.FORM,
            arguments = listOf(navArgument("id") {
                type = NavType.LongType
                defaultValue = 0L
            })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong("id") ?: 0L


            if (id != 0L) vm.selectRecipe(id)
            val editing = vm.selectedRecipe.collectAsState().value

            RecipeFormScreen(
                vm = vm,
                editing = if (id == 0L) null else editing,
                onBack = { nav.popBackStack() }
            )
        }
    }
}
