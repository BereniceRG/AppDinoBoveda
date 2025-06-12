package com.example.appdinoboveda

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// PantallaInicio.kt
// Pantalla de bienvenida con el logo, nombre de la app y botón para comenzar el recorrido.

@Composable
fun PantallaInicio(onComenzarClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Fondo
        Image(
            painter = painterResource(id = R.drawable.fondo),
            contentDescription = "Fondo de pantalla",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        // Centrar todo el contenido en el centro
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo ampliado
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier.size(200.dp) // Logo más grande
            )
            Spacer(modifier = Modifier.height(28.dp))
            // Título
            Text(
                text = "DinoBóveda",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(12.dp))
            // Subtítulo
            Text(
                text = "Explora la era de los gigantes:\n¡DinoBóveda, tu portal a la prehistoria!",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(40.dp))
            // Botón
            Button(
                onClick = onComenzarClick,
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(54.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF64996B))
            ) {
                Text(
                    text = "Comenzar mi viaje",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
