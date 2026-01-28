package com.example.recipevault.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.recipevault.ui.viewmodel.RecipeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailScreen(
    vm: RecipeViewModel,
    recipeId: Long,
    onBack: () -> Unit,
    onEdit: () -> Unit
) {
    LaunchedEffect(recipeId) { vm.selectRecipe(recipeId) }

    val recipe = vm.selectedRecipe.collectAsState().value
    val ingredients = vm.selectedIngredients.collectAsState().value

    var ingName by remember { mutableStateOf("") }
    var ingAmount by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(recipe?.title ?: "Detalji") },
                navigationIcon = {
                    TextButton(onClick = onBack) { Text("Nazad") }
                },
                actions = {
                    TextButton(onClick = onEdit) { Text("Edit") }
                    TextButton(
                        onClick = { vm.deleteRecipeById(recipeId); onBack() }
                    ) { Text("Obriši") }
                }
            )
        }
    ) { padding ->
        if (recipe == null) {
            Box(Modifier.padding(padding).fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                Text("Recept nije pronađen.")
            }
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Text(recipe.title, style = MaterialTheme.typography.headlineSmall)
            if (recipe.category.isNotBlank()) {
                Spacer(Modifier.height(4.dp))
                Text("Kategorija: ${recipe.category}", style = MaterialTheme.typography.bodyMedium)
            }

            Spacer(Modifier.height(12.dp))
            Text("Opis", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(6.dp))
            Text(recipe.description)

            Spacer(Modifier.height(16.dp))
            Divider()
            Spacer(Modifier.height(12.dp))

            Text("Sastojci", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = ingName,
                onValueChange = { ingName = it },
                label = { Text("Naziv sastojka") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = ingAmount,
                onValueChange = { ingAmount = it },
                label = { Text("Količina (npr. 200g)") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))

            Button(
                onClick = {
                    if (ingName.isNotBlank()) {
                        vm.addIngredient(recipeId, ingName, ingAmount)
                        ingName = ""
                        ingAmount = ""
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Dodaj sastojak")
            }

            Spacer(Modifier.height(12.dp))

            if (ingredients.isEmpty()) {
                Text("Nema sastojaka još.")
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(ingredients) { ing ->
                        Card(Modifier.fillMaxWidth()) {
                            Row(
                                Modifier.padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(Modifier.weight(1f)) {
                                    Text(ing.name, style = MaterialTheme.typography.titleSmall)
                                    if (ing.amount.isNotBlank()) Text(ing.amount)
                                }
                                TextButton(onClick = { vm.deleteIngredientById(ing.id) }) {
                                    Text("Obriši")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
