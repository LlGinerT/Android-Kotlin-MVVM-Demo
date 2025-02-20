package com.llginert.personalprofile.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp

/**
 * Botón de red social en Jetpack Compose.
 *
 * `SocialButton` es un componente reutilizable que muestra un botón con un icono de una red social
 * y abre la URL correspondiente en el navegador cuando se presiona, ademas de realizar una acción adicional.
 *
 * ### Responsabilidades:
 * - Mostrar un botón con el icono de la red social especificada.
 * - Abrir la URL en el navegador al hacer clic en el botón.
 * - Ejecutar una acción personalizada (`onClick()`).
 *
 * @param network Nombre de la red social (por ejemplo, "GitHub", "LinkedIn").
 * @param url Enlace a la red social que se abrirá en el navegador.
 * @param iconId ID del recurso drawable que representa el icono de la red social.
 * @param onSocialClick Acción adicional que se ejecuta antes de abrir la URL (por ejemplo, registrar una visita).
 */
@Composable
fun SocialButton(
    network: String,
    url: String,
    iconId: Int,
    onSocialClick: () -> Unit
) {
    // Le da contexto a android para que tome "decisiones" a traves de un Intent.
    // en este caso, le vamos a pedir a android que elija la mejor aplicación para abrir esta URL.
    // android buscara en su configuración y abrira la URL en el navegador que tenga como principal.
    // otros casos podria ser para pedirle acceso a la camara, galeria, sms, etc..
    val context = LocalContext.current

    IconButton(
        onClick = {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url))) // Abre la URL en el navegador
            onSocialClick() // Ejecuta la acción adicional (por ejemplo, incrementar contador de visitas)
        },
        modifier = Modifier.size(48.dp)
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = iconId),
            contentDescription = "Ir a $network",
            // la ventaja de las imagenes vectoriales, es que al ser figuras basicas, las podemos pintar
            // y modificar a necesidad.
            tint = MaterialTheme.colorScheme.primary // Usa el color primario definido en theme.kt
        )
    }
}
