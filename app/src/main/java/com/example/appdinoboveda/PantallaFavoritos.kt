package com.example.appdinoboveda

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

// PantallaFavoritos.kt
// Muestra todas las especies marcadas como favoritas por el usuario.
// Permite quitar especies de favoritos o ver sus detalles.

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaFavoritos(
    especies: List<Especie>,
    favoritos: List<Int>,
    onToggleFavorito: (Int) -> Unit,
    onEspecieClick: (Especie) -> Unit,
    onBack: () -> Unit = {}
) {
    val especiesFavoritas = especies.filter { favoritos.contains(it.id) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Favoritos") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        }
    ) { paddingValues ->
        if (especiesFavoritas.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text("No hay favoritos aún.", style = MaterialTheme.typography.bodyLarge)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                items(especiesFavoritas) { especie ->
                    EspecieCard(
                        especie = especie,
                        esFavorito = true,
                        onToggleFavorito = { onToggleFavorito(especie.id) },
                        onClick = { onEspecieClick(especie) }
                    )
                }
            }
        }
    }
}
