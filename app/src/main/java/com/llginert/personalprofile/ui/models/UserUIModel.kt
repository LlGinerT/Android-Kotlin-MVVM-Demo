package com.llginert.personalprofile.ui.models

import com.llginert.personalprofile.domain.models.User

/**
 * Representa los datos del usuario que serán utilizados en la UI.
 *
 * Esta clase es una representación de `User`, pero adaptada a los requisitos de la capa de UI.
 * En este caso, `User` y `UserUIModel` son exactamente iguales, pero aquí realmente solo necesitaríamos
 * los datos que se van a mostrar en la UI y/o transformarlos al formato que necesitemos.
 *
 * - **Ejemplo**:
 * Si en nuestra UI solo vamos a mostrar el nombre y la imagen de perfil del usuario,
 * esta clase solo debería tener esos dos campos, ya que nuestra `data class User` puede contener
 * información que no queremos que sea pública.
 * Otro ejemplo, si manejaramos fechas, seria transformar el formato de la fecha, al formato local.
 * O incluso, convertir a mayusculas la primera letra del nombre. En definitiva, aqui se realizaria
 * toda la logica para convertir un `User` de nuestra logica de negocio, a un `User` con los datos formateados
 * para mostrarlos en la UI.
 *
 * @property name Nombre del usuario.
 * @property bio Biografía del usuario.
 * @property imageUrl Identificador de la imagen de perfil.
 * @property languages Mapa/Diccionario de lenguajes de programación.
 * @property socialLinks Mapa/Diccionario de enlaces a redes sociales.
 */
data class UserUIModel(
    val name: String,
    val bio: String,
    val imageUrl: Int,
    val languages: Map<String, String>,
    val socialLinks: Map<String, String>,
)

/**
 * Convierte un objeto `User` en `UserUIModel` para su uso en la UI, a través de una función de extensión.
 *
 * En Kotlin, una **función de extensión** nos permite agregar nuevas funciones a una clase sin modificar
 * su código. En este caso, `.toUI()`.
 *
 * Esta función de extensión transforma los datos del modelo `User` al modelo `UserUIModel`,
 * asegurando que la capa de presentación tenga solo la información necesaria.
 *
 * ### **¿Por qué usar una función de extensión en vez de declararla en `User`?**
 *
 * En arquitectura **MVVM**, los **modelos de datos (`User`)** y los **modelos de UI (`UserUIModel`)**
 * deben estar separados. La transformación entre ellos debe hacerse **fuera** de la clase `User`
 * para cumplir con el **principio de responsabilidad única (SRP)**.
 *
 * 1. **Evita que `User` tenga responsabilidades de la UI**
 *    - `User` pertenece a la **capa de datos** y no debería conocer detalles de la UI.
 *    - Si agregamos `.toUI()` dentro de `User`, estamos **acoplando** `User` a la presentación.
 *
 * 2. **Mantiene `User` reutilizable**
 *    - `User` podría usarse en múltiples partes del código, no solo en la UI.
 *    - Si `User` tiene funciones específicas para la UI, eso **limita su reutilización**.
 *
 * 3. **Separa la conversión en otro nivel de abstracción**
 *    - La conversión de `User` a `UserUIModel` es una **transformación de datos**,
 *      por lo que no pertenece ni a la UI ni al modelo de datos, sino a la **capa de dominio o de presentación**.
 *
 * 4. **Mejor mantenibilidad**
 *    - Si la UI cambia, solo modificamos la función `.toUI()` en **un solo lugar**, sin tocar `User`.
 *
 * ### **Ejemplo de uso en el código**
 * ```kotlin
 * val user = User("Pepito Perez", "Desarrollador", R.drawable.profile_image, mapOf("Kotlin" to "Principiate"),
 * mapOf("GitHub" to "github.com/pepitoperez"))
 * val userUI = user.toUI()  // Convierte User a UserUIModel
 * ```
 *
 * @receiver `User` El objeto de usuario original.
 * @return Un objeto `UserUIModel` con los datos transformados.
 */
fun User.toUI() = UserUIModel(
    name = name, bio = bio, imageUrl = imageUrl, languages = languages,
    socialLinks = socialLinks
)