package com.example.appdinoboveda

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.material3.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Bookmark
import androidx.navigation.NavHostController

// PantallaPeriodos.kt
// Muestra los periodos (Triásico, Jurásico, Cretácico) como tarjetas seleccionables con imagen y descripción.
// Desde aquí se navega a la pantalla de especies del periodo seleccionado.
@Composable
fun PantallaPeriodos(
    navController: NavHostController,
    onPeriodoClick: (Int) -> Unit,
    onFavoritosClick: () -> Unit
) {
    Scaffold(
        topBar = { BarraSuperior() },
        bottomBar = {
            BarraInferior(
                onHomeClick = { navController.navigate("inicio") },
                onFavoritosClick = onFavoritosClick,
                onBusquedaClick = { navController.navigate("busqueda") },
                onUsuarioClick = { /* navega a usuario */ }
            )
        },
        containerColor = Color(0xFFA3B09E)
    )  { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {
            items(periodos) { periodo ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable { onPeriodoClick(periodo.id) },
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(110.dp)
                    ) {
                        // Imagen del periodo
                        Image(
                            painter = painterResource(
                                id = getDrawableIdByName(periodo.imagen.removeSuffix(".jpg"))
                            ),
                            contentDescription = periodo.nombre,
                            modifier = Modifier
                                .width(120.dp)
                                .fillMaxHeight(),
                            contentScale = ContentScale.Crop
                        )

                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .weight(1f),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = periodo.nombre,
                                style = MaterialTheme.typography.titleLarge.copy(
                                    color = when (periodo.nombre) {
                                        "Cretácico" -> Color(0xFFFFE600)
                                        "Jurásico" -> Color(0xFFAAFF3A)
                                        "Triásico" -> Color(0xFFFFC600)
                                        else -> Color.Black
                                    },
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = periodo.descripcion.take(55) + "...",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}

// Barra superior
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarraSuperior() {
    TopAppBar(
        title = { Text("DinoBóveda", color = Color.White, fontWeight = FontWeight.Bold) },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF876441))
    )
}

// Barra de navegación inferior
@Composable
fun BarraInferior(
    onHomeClick: () -> Unit,
    onFavoritosClick: () -> Unit,
    onBusquedaClick: () -> Unit,
    onUsuarioClick: () -> Unit
) {
    BottomAppBar(
        containerColor = Color(0xFF876441),
        contentPadding = PaddingValues(0.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconButton(onClick = onHomeClick) {
                Icon(Icons.Default.Home, contentDescription = "Inicio", tint = Color.White)
            }
            IconButton(onClick = { onBusquedaClick() }) {
                Icon(Icons.Default.Search, contentDescription = "Buscar", tint = Color.White)
            }
            IconButton(onClick = { onFavoritosClick() }) {
                Icon(Icons.Filled.Bookmark, contentDescription = "Favoritos", tint = Color.White)
            }
            IconButton(onClick = { /* Navegar a usuario */ }) {
                Icon(Icons.Default.Person, contentDescription = "Usuario", tint = Color.White)
            }
        }
    }
}


