package com.llginert.personalprofile.ui.profile

import com.llginert.personalprofile.ui.models.UserUIModel

/**
 * Representa el estado de la UI en la pantalla de perfil.
 *
 * `ProfileState` es un estado inmutable utilizado por `StateFlow` en `ProfileViewModel`
 * para reflejar los cambios en la UI de manera reactiva.
 *
 * ### Responsabilidades:
 * - Mantener el estado de la UI en todo momento.
 * - Permitir actualizaciones de estado sin modificar directamente las propiedades (uso de `copy()`).
 * - Actuar como un contenedor de datos para la vista en Jetpack Compose.
 *
 * ### ¿Por qué usamos `data class` para el estado?
 * - Permite el uso de `.copy()` para modificar solo propiedades específicas sin alterar el resto del estado.
 * - `StateFlow` solo emite actualizaciones cuando recibe **un nuevo objeto**, por lo que `copy()` ayuda a evitar
 *   mutaciones directas e inmutabilidad en el estado.
 *
 * @property showLoadingIndicator Indica si se debe mostrar el indicador de carga en la UI.
 * @property user Contiene la información del usuario en la interfaz (`UserUIModel`).
 * @property totalSocialNetworkVisits Contador del total de visitas a redes sociales.
 */
data class ProfileState(
    val showLoadingIndicator: Boolean = false, // Indica si la UI debe mostrar un spinner de carga.
    val user: UserUIModel = UserUIModel("", "", 0, emptyMap(), emptyMap()), // Datos del usuario en la UI.
    val totalSocialNetworkVisits: Int = 0 // Contador de visitas a redes sociales.
)
