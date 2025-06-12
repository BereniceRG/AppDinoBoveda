package com.example.appdinoboveda

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

// PantallaAgregarEspecie.kt
// Pantalla con formulario para añadir nuevas especies al catálogo.
// Permite ingresar nombre, descripción, tipo, y periodo de la especie.

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaAgregarEspecie(
    listaEspecies: MutableList<Especie>,
    periodos: List<Periodo>,
    navController: NavHostController
) {
    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var tipo by remember { mutableStateOf("Fauna") }
    var imagen by remember { mutableStateOf("") }
    var periodoSeleccionado by remember { mutableStateOf(periodos.first().nombre) } // por nombre

    Column(Modifier.padding(16.dp)) {
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
            onClick = {
                val periodoId = obtenerIdPeriodoPorNombre(periodoSeleccionado)
                if (periodoId != null) {
                    val nuevoId = (listaEspecies.maxOfOrNull { it.id } ?: 0) + 1
                    listaEspecies.add(
                        Especie(
                            id = nuevoId,
                            nombre = nombre,
                            descripcion = descripcion,
                            tipo = tipo,
                            imagen = imagen,
                            periodoId = periodoId
                        )
                    )
                    navController.popBackStack()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Agregar especie")
        }
    }
}
