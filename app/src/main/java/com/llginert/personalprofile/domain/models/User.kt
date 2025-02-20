package com.llginert.personalprofile.domain.models

/**
 * Representa la estructura de datos que un usuario debe tener en la aplicación.
 *
 * Estos datos pueden llegar de distintas fuentes:
 *  - Input del usuario
 *  - De una base de datos, o una API a traves del repository.
 *
 * Estamos hablando de usuario, por el formato de la aplicación de ejemplo que es mostrar un perfil,
 * pero podria ser cualquier objeto que deba manejar la aplicación, como producto, vehiculo, o cualquier
 * objeto que vaya a manejar nuestra aplicación y sus casos de uso.
 *
 * ## **Apunte sobre el manejo de imágenes en Android**:
 * #### **Imágenes estáticas** (guardadas en `res/drawable`):
 *   Se usa un `Int` porque `R.drawable.nombre`
 *   devuelve un ID de recurso. Estos IDs cambian en cada recompilación.
 * #### **Imágenes dinámicas** (subidas por el usuario):
 * Se usa un `String`, que debe ser transformado a un ID, estos `Strings` pueden ser:
 *   - El nombre del archivo (`"profile_picture.jpg"`) almacenado en memoria interna.
 *   - Una URL (`"https://ejemplo.com/profile.jpg"`) que se carga con Coil/Glide/Picasso.
 *   - Un `Uri` (`"content://..."`) si la imagen se almacena en el dispositivo.
 *
 * ### **Ejemplo de cómo cargar imágenes dinámicas**:
 * ```kotlin
 * // Ejemplo recomendado en jetpack compose usando la libreria coil
 * data class User(
 *  val imageUrl = "https://ejemplo.com/profile.jpg"
 *  )
 * AsyncImage(
 *     model = user.imageUrl,
 *     contentDescription = "Foto de perfil",
 *     placeholder = painterResource(id = R.drawable.placeholder),// Imagen que se muestra mientras carga
 *     error = painterResource(id = R.drawable.error_image),// Imagen que se muestra en caso de error
 *     contentScale = ContentScale.Crop
 * )
 *
 * ```
 *
 * @property name Nombre del usuario.
 * @property bio Biografía del usuario.
 * @property imageUrl Identificador de la imagen de perfil (puede ser un ID `Int`, una URL o un Uri `String`).
 * @property languages Mapa de lenguajes de programación (`"Kotlin" -> "Principiante"`).
 * @property socialLinks Mapa de enlaces a redes sociales (`"GitHub" -> "https://github.com/usuario"`).
 */

data class User(
    val name: String,
    val bio: String,
    val imageUrl: Int,
    val languages: Map<String, String>,// Equivale a un diccionario Clave-Valor.
    val socialLinks: Map<String, String>,
)
