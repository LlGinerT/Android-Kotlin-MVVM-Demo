package com.llginert.personalprofile.ui.utils

import com.llginert.personalprofile.R

/**
 * Obtiene el identificador de recurso del icono de una red social o lenguaje de programación.
 *
 * Esta función recibe un `String` con el nombre de la red social o lenguaje
 * y devuelve el ID del recurso drawable correspondiente.
 *
 * @param source Nombre del lenguaje de programación o red social.
 * @return ID del recurso drawable asociado.
 */
fun getIconSourceID(source: String): Int {
    return when (source) {
        "Kotlin" -> R.drawable.ic_kotlin
        "Java" -> R.drawable.ic_java
        "Python" -> R.drawable.ic_python
        "GitHub" -> R.drawable.ic_github
        "Linkedin" -> R.drawable.ic_linkedin
        else -> R.drawable.ic_kotlin // Deberia ser un icono default para cuando no se encuentre el correspondiente.
    }
}