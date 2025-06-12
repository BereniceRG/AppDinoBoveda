package com.example.appdinoboveda

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.TextFieldValue

// PantallaBusqueda.kt
// Pantalla de búsqueda de especies por nombre o descripción.
// Permite al usuario filtrar y ver resultados instantáneamente.

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaBusqueda(
    especies: List<Especie>,
    onBack: () -> Unit = {},
    onEspecieClick: (Especie) -> Unit = {}
) {
    var query by remember { mutableStateOf(TextFieldValue("")) }

    val resultados = remember(query, especies) {
        especies.filter {
            it.nombre.contains(query.text, ignoreCase = true) ||
                    it.descripcion.contains(query.text, ignoreCase = true)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Buscar especie") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                label = { Text("Buscar") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            if (resultados.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No se encontraron resultados.", style = MaterialTheme.typography.bodyLarge)
                }
            } else {
                LazyColumn {
                    items(resultados) { especie ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .clickable { onEspecieClick(especie) }
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(12.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = getDrawableIdByName(especie.imagen)),
                                    contentDescription = especie.nombre,
                                    modifier = Modifier
                                        .size(60.dp)
                                )
                                Spacer(modifier = Modifier.width(16.dp))
                                Text(especie.nombre, style = MaterialTheme.typography.titleMedium)
                            }
                        }
                    }
                }
            }
        }
    }
}

