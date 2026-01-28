package com.example.recipevault.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.recipevault.data.local.entity.RecipeEntity
import com.example.recipevault.ui.viewmodel.RecipeViewModel

@Composable
fun RecipeListScreen(vm: RecipeViewModel) {
    val recipes = vm.recipes.collectAsState().value

    var showForm by remember { mutableStateOf(false) }
    var editing by remember { mutableStateOf<RecipeEntity?>(null) }

    if (showForm) {
        RecipeFormScreen(
            vm = vm,
            editing = editing,
            onBack = {
                showForm = false
                editing = null
            }
        )
        return
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    editing = null
                    showForm = true
                }
            ) { Text("+") }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Text("Recepti", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(recipes, key = { it.id }) { r ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                editing = r
                                showForm = true
                            }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(r.title, style = MaterialTheme.typography.titleMedium)
                                if (r.category.isNotBlank()) {
                                    Text(r.category, style = MaterialTheme.typography.bodySmall)
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(r.description, maxLines = 2)
                            }

                            IconButton(onClick = { vm.deleteRecipe(r) }) {
                                Icon(Icons.Filled.Delete, contentDescription = "Obriši")
                            }
                        }
                    }
                }
            }
        }
    }
}
