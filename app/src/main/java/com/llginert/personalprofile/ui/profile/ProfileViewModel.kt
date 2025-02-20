package com.llginert.personalprofile.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.llginert.personalprofile.domain.useCases.GetUserUseCase
import com.llginert.personalprofile.ui.models.toUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


/**
 * ViewModel encargado de gestionar el estado de la UI para la pantalla de perfil.
 *
 * `ProfileViewModel` es parte de la capa de presentación en MVVM y actúa como el intermediario
 * entre la capa de dominio (caso de uso `GetUserUseCase`) y la UI.
 *
 * ## **¡¡ATENCIÓN!!**
 * No fijaros demasiado en la implementación, esta simplificado para prototipar el proyecto,
 * falta implementar mejor las corutinas, el manejo de errores con catch y demas. El objetivo es explicar
 * la estructura del proyecto y las responsabilidades que tendria un `ViewModel`.
 * Tampoco confundir el estado de la UI(`ProfileState`) con el ciclo de vida de la app (onCreate,onPause,etc)
 *
 * ### Responsabilidades:
 * - Obtener los datos del usuario mediante `GetUserUseCase` y actualizar el estado de la UI.
 * - Manejar el estado de la UI con `StateFlow`, permitiendo que la pantalla observe cambios reactivos.
 * - Controlar la lógica de presentación, como la gestión del indicador de carga y el conteo de visitas.
 *
 * @property getUserUseCase Caso de uso encargado de obtener los datos del usuario.
 *
 * @see ProfileState
 * @see com.llginert.personalprofile.ui.models.UserUIModel
 * @see GetUserUseCase
 * @see com.llginert.personalprofile.ui.screens.UserProfileScreen
 */
class ProfileViewModel(
    private val getUserUseCase: GetUserUseCase,
) : ViewModel() {

    /**
     * Estado interno de la UI, gestionado con `MutableStateFlow`.
     *
     * `MutableStateFlow` se usa para mantener y actualizar el estado de la UI de forma reactiva.
     * La UI observará este estado a través de `uiState` y se actualizará automáticamente cuando cambie.
     */
    private val _uiState: MutableStateFlow<ProfileState> = MutableStateFlow(ProfileState())

    /**
     * Estado público e inmutable de la UI.
     *
     * La UI solo tiene acceso a `uiState` (de tipo `StateFlow`), evitando modificaciones directas.
     */
    val uiState: StateFlow<ProfileState> = _uiState

    init {
        fetchUserDetails()
    }

    /**
     * Obtiene los datos del usuario y actualiza el estado de la UI.
     *
     * - Muestra el indicador de carga(si lo implementaramos) mientras se recuperan los datos.
     * - Obtiene el usuario llamando al caso de uso `getUserUseCase()`.
     * - Transforma los datos a `UserUIModel` y actualiza el estado de la UI.
     *
     * Se ejecuta dentro de `viewModelScope.launch`, asegurando que las operaciones se realicen en
     * un hilo de fondo sin bloquear la UI.
     *
    ### Explicación de `state` y `copy()`
     * - `state` representa el estado actual almacenado en `MutableStateFlow` o `StateFlow`.
     * - Usamos `.copy()` para modificar solo el atributo deseado, sin alterar los demás valores.
     * - `StateFlow` solo emite cambios si se le asigna un **nuevo objeto**, por eso usamos `.copy()`
     * dentro de `.update()`.
     *
     * @see com.llginert.personalprofile.ui.screens.UserProfileScreen
     */
    private fun fetchUserDetails() = viewModelScope.launch {
        // Mostrar el indicador de carga mientras se obtienen los datos, en nuestro caso simulado 0 segundos
        _uiState.update { state -> state.copy(showLoadingIndicator = true) }

        // Obtener el usuario llamando al caso de uso, falta mejorar aplicando corutinas.
        val user = getUserUseCase()

        // Actualizar el estado de la UI con los datos del usuario transformando el User en un UserUIModel
        _uiState.update { state ->
            state.copy(
                user = user.toUI(),
                showLoadingIndicator = false
            )
        }
    }

    /**
     * Incrementa el contador de visitas a redes sociales.
     *
     * Cada vez que un usuario visita un enlace de red social, se incrementa el contador en `ProfileState`.
     *
     * @see com.llginert.personalprofile.ui.components.SocialButton
     * @see ProfileState
     */
    fun incrementTotalVisits() {
        _uiState.update { state ->
            state.copy(totalSocialNetworkVisits = state.totalSocialNetworkVisits + 1)
        }
    }
}


