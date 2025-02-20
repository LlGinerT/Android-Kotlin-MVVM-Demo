package com.llginert.personalprofile.data.repository

import com.llginert.personalprofile.R
import com.llginert.personalprofile.domain.models.User
import com.llginert.personalprofile.domain.repository.UserRepository

/**
 * Implementación ficticia del repositorio de usuario para pruebas y desarrollo.
 *
 * Esta clase proporciona un usuario de prueba sin necesidad de acceder a una API o base de datos.
 * Se utiliza en entornos de desarrollo y pruebas para simular la obtención de datos,
 * evitando dependencias externas y facilitando el testing al controlar los datos.
 *
 * ### Implementación del Repositorio en un Proyecto Real
 * Cada fuente de datos debe tener su propia implementación del repositorio(`UserRepository.kt`),
 * asegurando que la capa de datos pueda cambiar sin afectar la lógica de la aplicación. Dependiendo del caso,
 * podrían existir distintas implementaciones:
 *
 * - **Repositorio de pruebas**: Como este, que proporciona datos estáticos para desarrollo y testing.
 * - **Repositorio basado en API**: Obtiene datos de un servicio web mediante Retrofit.(`ApiUserDataRepository.kt`)
 * - **Repositorio basado en base de datos local**: Utiliza Room con objetos DAO y DTO ("un pequeño backend")
 *    para almacenamiento offline.(`RoomUserDataRepository.kt`)
 *
 * Cada repositorio tiene su propia casuistica, en este ejemplo, solo nos devuelve un usuario, pero
 * la API nos podria devolver una lista de usuarios que despues tendriamos que manejar.
 *
 * ### Responsabilidades:
 * - Proporcionar datos estáticos simulados en lugar de datos reales de una API o base de datos.
 * - Implementar la interfaz `UserRepository` para cumplir con el principio de inversión de dependencias.
 * - Permitir que la UI o los casos de uso obtengan datos sin importar su origen real.
 *
 * @see UserRepository Interfaz que define el contrato del repositorio.
 */

class FakeUserDataRepository: UserRepository {

    private val mockUserData = User(
        name = "Luis Giner Tendero",
        bio = "Tengo 33 años y he dedicado mi vida a la sanidad, y tras 13 años en el SAMU, he decidido dar un " +
                "salto a este inmenso mundo del desarrollo de software y enfrentarme a " +
                "todos los retos que puede ofrecer.",
        imageUrl = R.drawable.profile_image,
        languages = mapOf(
            "Python" to "Medio",
            "Java" to "Principiante",
            "Kotlin" to "MUUUY Principiante"
        ),
        socialLinks = mapOf(
            "GitHub" to "https://github.com/LlGinerT",
            "Linkedin" to "https://linkedin.com/in/luis-giner-tendero-76490430b"
        )
    )
    /**
     * Implementación que sobrescribe el metodo de la interfaz.
     * Devuelve los datos del usuario simulado.
     *
     * @return Un objeto `User` con los datos simulados.
     */
    override fun getUser(): User = mockUserData
}