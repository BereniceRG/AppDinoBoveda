package com.example.appdinoboveda

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

// PantallaModificarEspecie.kt
// Pantalla con formulario para editar/modificar los datos de una especie existente.
// Permite actualizar los campos y confirma antes de guardar los cambios.

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaModificarEspecie(
    especieOriginal: Especie,
    periodos: List<Periodo>,
    onModificar: (Especie) -> Unit,
    onBack: () -> Unit
) {
    var nombre by remember { mutableStateOf(especieOriginal.nombre) }
    var descripcion by remember { mutableStateOf(especieOriginal.descripcion) }
    var tipo by remember { mutableStateOf(especieOriginal.tipo) }
    var imagen by remember { mutableStateOf(especieOriginal.imagen) }
    var periodoSeleccionado by remember {
        mutableStateOf(periodos.find { it.id == especieOriginal.periodoId }?.nombre ?: periodos.first().nombre)
    }
    var mostrarDialogo by remember { mutableStateOf(false) }

    Column(
        Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState()) // permite deslizar
    ) {
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre de la especie") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripción") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = tipo,
            onValueChange = { tipo = it },
            label = { Text("Tipo (Fauna/Flora)") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = imagen,
            onValueChange = { imagen = it },
            label = { Text("Imagen (nombre en drawable)") },
            modifier = Modifier.fillMaxWidth()
        )

        // Dropdown de periodos
        var expanded by remember { mutableStateOf(false) }
        Box {
            OutlinedTextField(
                value = periodoSeleccionado,
                onValueChange = { periodoSeleccionado = it },
                label = { Text("Periodo") },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = { expanded = true }) {
                        Icon(Icons.Filled.ArrowDropDown, null)
                    }
                }
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                periodos.forEach { periodo ->
                    DropdownMenuItem(
                        text = { Text(periodo.nombre) },
                        onClick = {
                            periodoSeleccionado = periodo.nombre
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { mostrarDialogo = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Modificar especie")
        }

        Spacer(modifier = Modifier.height(8.dp))
        OutlinedButton(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cancelar")
        }
    }

    // Diálogo de confirmación
    if (mostrarDialogo) {
        AlertDialog(
            onDismissRequest = { mostrarDialogo = false },
            title = { Text("Modificar especie") },
            text = { Text("¿Estás seguro de que deseas modificar esta especie?") },
            confirmButton = {
                Button(
                    onClick = {
                        val periodoId = obtenerIdPeriodoPorNombre(periodoSeleccionado)
                        if (periodoId != null) {
                            onModificar(
                                especieOriginal.copy(
                                    nombre = nombre,
                                    descripcion = descripcion,
                                    tipo = tipo,
                                    imagen = imagen,
                                    periodoId = periodoId
                                )
                            )
                        }
                        mostrarDialogo = false
                    }
                ) { Text("Modificar") }
            },
            dismissButton = {
                OutlinedButton(onClick = { mostrarDialogo = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}
