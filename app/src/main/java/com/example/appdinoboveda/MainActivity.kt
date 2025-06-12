package com.example.appdinoboveda

// MainActivity.kt
// Actividad principal de la app. Inicializa y lanza la navegación de Compose.
// Configura el punto de entrada de DinoBóveda.

import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavigation()
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val favoritos = remember { mutableStateListOf<Int>() }
    // mutableStateListOf, no sólo List
    val listaEspecies = remember { mutableStateListOf<Especie>().apply { addAll(especies) } }
    val listaPeriodos = periodos

    NavHost(navController, startDestination = "inicio") {
        composable("inicio") {
            PantallaInicio(
                onComenzarClick = { navController.navigate("periodos") }
            )
        }

        composable(
            "especies/{periodoId}",
            arguments = listOf(navArgument("periodoId") { type = NavType.IntType })
        ) { backStackEntry ->
            val periodoId = backStackEntry.arguments?.getInt("periodoId") ?: 1
            val periodo = periodos.find { it.id == periodoId }
            if (periodo != null) {
                PantallaEspeciesDePeriodo(
                    periodo = periodo,
                    especies = listaEspecies.filter { it.periodoId == periodoId },
                    favoritos = favoritos,
                    onToggleFavorito = { id ->
                        if (favoritos.contains(id)) favoritos.remove(id) else favoritos.add(id)
                    },
                    onBack = { navController.popBackStack() },
                    onEspecieClick = { especie -> navController.navigate("detalle/${especie.id}") }
                )
            }
        }
        composable(
            "detalle/{especieId}",
            arguments = listOf(navArgument("especieId") { type = NavType.IntType })
        ) { backStackEntry ->
            val especieId = backStackEntry.arguments?.getInt("especieId") ?: 0
            PantallaDetalleEspecie(
                especieId = especieId,
                especies = listaEspecies, // <--- usa la lista mutable
                navController = navController,
                onEliminar = { especie -> listaEspecies.remove(especie) }
            )
        }

        // ---- Pantalla de Favoritos ----
        composable("favoritos") {
            PantallaFavoritos(
                especies = listaEspecies,
                favoritos = favoritos,
                onToggleFavorito = { id ->
                    if (favoritos.contains(id)) favoritos.remove(id) else favoritos.add(id)
                },
                onEspecieClick = { especie ->
                    navController.navigate("detalle/${especie.id}")
                },
                onBack = { navController.popBackStack() }
            )
        }
        composable("busqueda") {
            PantallaBusqueda(
                especies = listaEspecies, // Aquí pasas la lista reactiva y mutable
                onEspecieClick = { especie -> navController.navigate("detalle/${especie.id}") },
                onBack = { navController.popBackStack() }
            )
        }

        composable("periodos") {
            PantallaPeriodos(
                navController = navController, // <--- pásale el navController
                onPeriodoClick = { periodoId -> navController.navigate("especies/$periodoId") },
                onFavoritosClick = { navController.navigate("favoritos") }
            )
        }
        composable("agregar_especie") {
            PantallaAgregarEspecie(
                listaEspecies = listaEspecies,     // lista mutable global de especies
                periodos = listaPeriodos,          // lista global de periodos
                navController = navController
            )
        }
        composable("modificar_especie/{especieId}", arguments = listOf(navArgument("especieId") { type = NavType.IntType })) { backStackEntry ->
            val especieId = backStackEntry.arguments?.getInt("especieId") ?: 0
            val especie = listaEspecies.find { it.id == especieId }
            if (especie != null) {
                PantallaModificarEspecie(
                    especieOriginal = especie,
                    periodos = periodos,
                    onModificar = { especieModificada ->
                        val idx = listaEspecies.indexOfFirst { it.id == especieModificada.id }
                        if (idx != -1) listaEspecies[idx] = especieModificada
                        navController.popBackStack()
                    },
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }

}
