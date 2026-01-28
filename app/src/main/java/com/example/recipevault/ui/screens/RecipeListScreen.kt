package com.example.recipevault.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.recipevault.ui.viewmodel.RecipeViewModel

@Composable
fun RecipeListScreen(
    vm: RecipeViewModel,
    onOpenRecipe: (Long) -> Unit,
    onAdd: () -> Unit
) {
    val recipes = vm.recipes.collectAsState().value

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAdd) { Text("+") }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Text("Recepti", style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(12.dp))

            if (recipes.isEmpty()) {
                Text("Nema recepata. Klikni + da dodaš prvi.")
                return@Column
            }

            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(recipes) { r ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onOpenRecipe(r.id) }
                    ) {
                        Column(Modifier.padding(12.dp)) {
                            Text(r.title, style = MaterialTheme.typography.titleMedium)
                            if (r.category.isNotBlank()) Text(r.category, style = MaterialTheme.typography.bodySmall)
                            Spacer(Modifier.height(6.dp))
                            Text(r.description, maxLines = 2)
                        }
                    }
                }
            }
        }
    }
}
