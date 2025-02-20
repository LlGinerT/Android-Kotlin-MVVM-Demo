package com.llginert.personalprofile.domain.repository

import com.llginert.personalprofile.domain.models.User

/**
 * Interfaz que define el contrato para obtener información del usuario.
 *
 * En la arquitectura MVVM con Clean Architecture, `UserRepository` pertenece a la capa de dominio.
 * Su propósito es actuar como una abstracción que permite desacoplar la fuente de datos
 * de los casos de uso y la UI.
 *
 * Esta interfaz no sabe de dónde provienen los datos, lo que significa que la implementación
 * concreta puede obtener los datos de una API, una base de datos local o incluso de datos simulados.
 *
 * ### Responsabilidades:
 * - Definir las funciónes `getUser()` y demas metodos para manejar los datos,
 *   que deberán ser implementados por un repositorio en la capa de datos.
 * - Proporcionar una manera de acceder a la información del usuario sin exponer la implementación.
 * - Mantener la capa de dominio independiente de detalles de infraestructura como redes o bases de datos.
 *
 * ### Beneficios de usar una interfaz:
 * 1. **Desacoplamiento**: Permite cambiar la fuente de datos sin afectar la capa de dominio o la UI.
 * 2. **Facilidad de prueba**: Podemos crear una implementación falsa para pruebas
 *    sin depender de una API real, como es nuestro caso de ejemplo con `FakeUserDataRepository`.
 * 3. **Escalabilidad**: Si en el futuro se necesita cambiar la fuente de datos, o añadir una nueva fuente
 *    solo se modifica la implementación concreta, o se añade otra implementación,
 *    sin afectar otras partes del código.
 *
 *
 * @see com.llginert.personalprofile.data.repository.FakeUserDataRepository Implementación de ejemplo para pruebas.
 */
interface UserRepository {
    fun getUser(): User
    //TODO: Faltan otras funciones para completar el comportamiento en un caso real
}