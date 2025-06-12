package com.example.appdinoboveda

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Icon
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder

// PantallaEspeciesDePeriodo.kt
// Pantalla que muestra la información general del periodo y las listas de fauna y flora asociadas.
// Usa tabs para navegar entre información general, fauna y flora.

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaEspeciesDePeriodo(
    periodo: Periodo,
    especies: List<Especie>,
    favoritos: MutableList<Int>,
    onToggleFavorito: (Int) -> Unit,
    onBack: () -> Unit,
    onEspecieClick: (Especie) -> Unit
) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabTitles = listOf("Información", "Fauna", "Flora")
    val faunaList = especies.filter { it.periodoId == periodo.id && it.tipo == "Fauna" }
    val floraList = especies.filter { it.periodoId == periodo.id && it.tipo == "Flora" }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("DinoBóveda") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF876441))
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            item {
                // Imagen del periodo más pequeña y con margen arriba
                Image(
                    painter = painterResource(id = getDrawableIdByName(periodo.imagen)),
                    contentDescription = periodo.nombre,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .padding(top = 16.dp, bottom = 8.dp),
                    contentScale = ContentScale.Crop
                )
            }
            item {
                Text(
                    text = periodo.nombre,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color(0xFF145a13),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 0.dp, start = 16.dp, end = 16.dp, bottom = 0.dp)
                )
            }
            item {
                TabRow(
                    selectedTabIndex = selectedTab,
                    modifier = Modifier.padding(top = 12.dp)
                ) {
                    tabTitles.forEachIndexed { index, title ->
                        Tab(
                            text = { Text(title) },
                            selected = selectedTab == index,
                            onClick = { selectedTab = index }
                        )
                    }
                }
            }
            when (selectedTab) {
                0 -> {
                    item {
                        Text(
                            periodo.descripcion,
                            modifier = Modifier.padding(20.dp)
                        )
                    }
                }
                1 -> {
                    items(faunaList) { especie ->
                        EspecieCard(
                            especie = especie,
                            esFavorito = favoritos.contains(especie.id),
                            onToggleFavorito = onToggleFavorito,
                            onClick = { onEspecieClick(especie) }
                        )
                    }
                }
                2 -> {
                    items(floraList) { especie ->
                        EspecieCard(
                            especie = especie,
                            esFavorito = favoritos.contains(especie.id),
                            onToggleFavorito = onToggleFavorito,
                            onClick = { onEspecieClick(especie) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun EspecieCard(
    especie: Especie,
    esFavorito: Boolean,
    onToggleFavorito: (Int) -> Unit,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation()
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Image(
                painter = painterResource(id = getDrawableIdByName(especie.imagen)),
                contentDescription = especie.nombre,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = especie.nombre,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF145A13)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = especie.descripcion,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = { onToggleFavorito(especie.id) }) {
                    Icon(
                        imageVector = if (esFavorito) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                        contentDescription = if (esFavorito) "Quitar de Favoritos" else "Agregar a Favoritos"
                    )
                }
            }
        }
    }
}

@Composable
fun getDrawableIdByName(name: String): Int {
    val context = LocalContext.current
    // Se quita extensión si la trae
    val cleanName = name.substringBeforeLast(".")
    return context.resources.getIdentifier(cleanName, "drawable", context.packageName)
}

