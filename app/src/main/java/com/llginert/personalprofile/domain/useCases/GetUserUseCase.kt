package com.llginert.personalprofile.domain.useCases

import com.llginert.personalprofile.domain.models.User
import com.llginert.personalprofile.domain.repository.UserRepository

/**
 * Caso de uso para obtener los datos del usuario.
 *
 * En la arquitectura MVVM con Clean Architecture, los casos de uso pertenecen a la capa de dominio y
 * encapsulan una única responsabilidad de negocio. Su propósito es centralizar la lógica de aplicación,
 * evitando que la UI acceda directamente a los repositorios.
 *
 * Este caso de uso se encarga de recuperar los datos del usuario a través de `UserRepository`,
 * sin importar su fuente real (API, base de datos local o datos simulados) ya que al instanciarlo
 * podriamos pasarle cualquier implementación del repositorio como argumento(`FakeUserDataRepository`,
 * `ApiUserRepository`).
 * Otro caso de uso podria ser `DeleteUserUseCase` el cual encapsularia otro caso de uso de nuestra
 * aplicación.
 *
 *
 *
 * ### Responsabilidades:
 * - Obtener los datos del usuario desde `UserRepository`.
 * - Aplicar cualquier transformación o validación antes de devolver los datos a la UI.
 * - Mantener la lógica de negocio separada de la UI y la infraestructura de datos.
 *
 * ### Beneficios de usar un caso de uso:
 * 1. **Encapsulación de la lógica de negocio**: La UI solo llama a este caso de uso sin preocuparse por la fuente de datos.
 * 2. **Facilidad de testeo**: Se pueden realizar pruebas unitarias sin depender de la capa de datos.
 * 3. **Reutilización**: Si en el futuro necesitamos obtener el usuario en otra parte de la app, podemos usar este mismo caso de uso.
 *
 * @see UserRepository
 * @property userRepository Repositorio que proporciona los datos del usuario.
 */
class GetUserUseCase(
    private val userRepository: UserRepository
) {
    /**
     * Ejecuta el caso de uso para obtener el usuario.
     *
     * Se sobrecarga el operador `invoke()` para que la llamada sea más sencilla desde la UI.
     * Esto permite que en lugar de `getUserUseCase.invoke()`, simplemente se pueda llamar `getUserUseCase()`.
     *
     * ### **Apuntes sobre la sintaxis usada:**
     * Kotlin nos permite usar una sintaxis abreviada, muy utíl cuando solo tenemos una expresión.
     *
     * **Sintaxis abreviada usada:**
     * ```kotlin
     *  operator fun invoke(): User = userRepository.getUser()
     * ```
     *
     * **Equivalente en sintaxis completa:**
     * ```kotlin
     * operator fun invoke(): User {
     * return userRepository.getUser()
     * }
     * ```
     * @return Un objeto `User` con la información del usuario.
     */
    operator fun invoke(): User = userRepository.getUser()
}
