package com.llginert.personalprofile.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.llginert.personalprofile.ui.components.SocialButton
import com.llginert.personalprofile.ui.profile.ProfileState
import com.llginert.personalprofile.ui.profile.ProfileViewModel
import com.llginert.personalprofile.ui.utils.getIconSourceID
/**
 * Pantalla de perfil de usuario en Jetpack Compose.
 *
 * `UserProfileScreen` es una función `@Composable` que se encarga de observar el estado del `ProfileViewModel`
 * y renderizar la UI de acuerdo con los datos del usuario y el estado actual.
 *
 * ### Responsabilidades:
 * - Observar el estado del `ProfileViewModel` mediante `collectAsStateWithLifecycle()`.
 * - Pasar el estado actualizado a `UserProfileContent`.
 * - Manejar la acción de clic en redes sociales mediante `SocialButton` asignandole
 * la acción `viewModel.incrementTotalVisits()`.
 *
 * @param viewModel ViewModel que proporciona los datos del usuario y el estado de la UI.
 * @param modifier Modificador opcional para ajustes de diseño.
 *
 * @see ProfileViewModel
 * @see ProfileState
 * @see SocialButton
 */
@Composable
fun UserProfileScreen(
    viewModel: ProfileViewModel,
    modifier: Modifier = Modifier
) {
    // Observa el estado de la UI
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // Renderiza el estado de la pantalla actualizado y asigna funcionalidad a `SocialButton`
    UserProfileContent(uiState, modifier, onSocialClick = {viewModel.incrementTotalVisits()})
}


/**
 * Contenido de la pantalla de perfil de usuario (seria como el "HTML,CSS").
 *
 * `UserProfileContent` es una función `@Composable` que recibe el estado actual de la UI
 * y lo utiliza para construir la interfaz gráfica.
 * Al igual que en `ProfileViewModel` no fijarse demasiado en la implementación, se puede hacer de
 * mil formas, es un prototipo para explicar la estructura.
 *
 * ### Responsabilidades:
 * - Mostrar la información del usuario (imagen, nombre, biografía, lenguajes y redes sociales).
 * - Gestionar la interacción del usuario con los botones de redes sociales.
 * - Mostrar un indicador de carga si `showLoadingIndicator` está activo.
 *
 * @param uiState Estado actual de la UI (`ProfileState`).
 * @param modifier Modificador opcional para ajustes de diseño.
 * @param onSocialClick Callback que se ejecuta al hacer clic en un botón de red social.
 *
 * @see SocialButton
 * @see com.llginert.personalprofile.ui.utils.getIconSourceID
 * 
 */
@Composable
fun UserProfileContent(
    uiState: ProfileState,
    modifier: Modifier = Modifier,
    onSocialClick:() -> Unit // es como void en java
) {
    //Comprueba en el estado si los datos aun no estan cargados y muestra un spinner de carga
    if (uiState.showLoadingIndicator) {
        CircularProgressIndicator()
    }
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(18.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            // Foto perfil
            Image(
                painter = painterResource(id = uiState.user.imageUrl),
                contentDescription = "Foto de perfil",
                modifier = Modifier
                    .size(180.dp)
                    .clip(CircleShape)
                    .border(4.dp, Color.Gray, shape = CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Nombre
            Text(uiState.user.name, fontSize = 24.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(20.dp))

            // Sección sobre mí
            Text("Sobre mí:", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)

            Text(
                uiState.user.bio,
                fontSize = 16.sp,
                textAlign = TextAlign.Justify,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("Lenguajes de programación:", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            // bucle para mostrar cualquier numero de lenguajes que contenga el User
            uiState.user.languages.forEach { (language, level) ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    Icon(
                        // Se obtiene la imagen vectorial a traves de IconUtils.kt
                        painter = painterResource(id = getIconSourceID(language)),
                        contentDescription = language,
                        modifier = Modifier.size(24.dp),
                        // Conserva el color original de la imagen vectorial
                        tint = Color.Unspecified
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text("$language - $level", fontSize = 16.sp)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Redes:", fontSize = 10.sp, fontWeight = FontWeight.SemiBold)

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                // Crea un boton para cada red social en el User.
                uiState.user.socialLinks.forEach { (network, url) ->
                    SocialButton(
                        network = network,
                        url = url,
                        iconId = getIconSourceID(network),
                        onSocialClick = onSocialClick
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Muestra el estado actualizado del total de visitas a las redes sociales
            Text(
                text = "Total de visitas: ${uiState.totalSocialNetworkVisits}",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
        }
    }
}
