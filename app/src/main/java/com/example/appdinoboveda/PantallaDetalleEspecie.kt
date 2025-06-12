package com.example.appdinoboveda

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState

// PantallaDetalleEspecie.kt
// Muestra los detalles completos de una especie seleccionada.
// Incluye imagen, nombre, tipo, descripción y acciones como modificar, eliminar o agregar otra especie.

@Composable
fun PantallaDetalleEspecie(
    especieId: Int,
    especies: List<Especie>,
    navController: NavHostController,
    onEliminar: (Especie) -> Unit
) {
    val especie = especies.find { it.id == especieId }
    var mostrarDialogo by remember { mutableStateOf(false) }

    if (especie == null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text("Especie no encontrada", style = MaterialTheme.typography.headlineSmall)
            Button(onClick = { navController.popBackStack() }) {
                Text("Volver")
            }
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    )
    {
        // Imagen
        Image(
            painter = painterResource(id = getDrawableIdByName(especie.imagen)),
            contentDescription = especie.nombre,
            modifier = Modifier
                .height(220.dp)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Nombre
        Text(especie.nombre, style = MaterialTheme.typography.headlineMedium)
        // Tipo
        Text(especie.tipo, style = MaterialTheme.typography.bodySmall)
        Spacer(modifier = Modifier.height(8.dp))
        // Descripción larga
        Text(especie.descripcion, style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(16.dp))

        // Botón: Añadir otra especie
        Button(
            onClick = { navController.navigate("agregar_especie") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Añadir otra especie")
        }

        // Botón: Eliminar (con confirmación)
        Button(
            onClick = { mostrarDialogo = true },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        ) {
            Text("Eliminar especie", color = Color.White)
        }

        // Botón: Volver
        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Text("Volver")
        }

        Button(
            onClick = { navController.navigate("modificar_especie/${especie.id}") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Modificar especie")
        }
    }

    // Diálogo de confirmación
    if (mostrarDialogo) {
        AlertDialog(
            onDismissRequest = { mostrarDialogo = false },
            title = { Text("Eliminar especie") },
            text = { Text("¿Estás seguro de que quieres eliminar esta especie? Esta acción no se puede deshacer.") },
            confirmButton = {
                Button(
                    onClick = {
                        onEliminar(especie)
                        mostrarDialogo = false
                        navController.popBackStack()
                    }
                ) { Text("Eliminar") }
            },
            dismissButton = {
                OutlinedButton(onClick = { mostrarDialogo = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}
