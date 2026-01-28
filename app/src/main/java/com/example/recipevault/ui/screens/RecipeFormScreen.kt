package com.example.recipevault.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.recipevault.data.local.entity.RecipeEntity
import com.example.recipevault.ui.viewmodel.RecipeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeFormScreen(
    vm: RecipeViewModel,
    editing: RecipeEntity?,
    onBack: () -> Unit
) {
    var title by remember { mutableStateOf(editing?.title ?: "") }
    var description by remember { mutableStateOf(editing?.description ?: "") }
    var category by remember { mutableStateOf(editing?.category ?: "") }
    var imageUrl by remember { mutableStateOf(editing?.imageUrl ?: "") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (editing == null) "Dodaj recept" else "Izmeni recept") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Naslov") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = category,
                onValueChange = { category = it },
                label = { Text("Kategorija (opciono)") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = imageUrl,
                onValueChange = { imageUrl = it },
                label = { Text("Link slike (opciono)") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Opis recepta") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedButton(
                    onClick = onBack,
                    modifier = Modifier.weight(1f)
                ) { Text("Otkaži") }

                Button(
                    onClick = {
                        vm.saveRecipe(
                            id = editing?.id ?: 0L,
                            title = title,
                            description = description,
                            category = category,
                            imageUrl = imageUrl
                        )
                        onBack()
                    },
                    modifier = Modifier.weight(1f)
                ) { Text("Sačuvaj") }
            }
        }
    }
}
