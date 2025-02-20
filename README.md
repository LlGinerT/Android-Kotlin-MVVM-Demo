# Android Kotlin MVVM Demo

Este proyecto es un ejemplo didáctico que muestra cómo estructurar una aplicación Android siguiendo la **arquitectura MVVM** y algunos principios de **Clean Architecture**.  
La idea principal es cargar y mostrar el perfil de un usuario (con datos ficticios en este caso) para explicar cómo se organiza un proyecto con capas bien definidas. Es un proyecto hecho por un estudiante para otros estudiantes, espero que os sea de gran utilidad.

---

## Índice

1. [Descripción general](#descripción-general)
2. [Objetivos del proyecto](#objetivos-del-proyecto)
3. [Estructura de paquetes](#estructura-de-paquetes)
4. [Flujo de datos (Data Flow)](#flujo-de-datos-data-flow)
5. [Tecnologías y librerías](#tecnologías-y-librerías)
6. [Cómo ejecutar el proyecto](#cómo-ejecutar-el-proyecto)
7. [Posibles mejoras y tareas pendientes](#posibles-mejoras-y-tareas-pendientes)
8. [Licencia](#licencia)

---

## Descripción general

La aplicación consiste en una sola pantalla (por ahora) que muestra la información de un usuario: foto de perfil, nombre, biografía, lenguajes de programación y enlaces a redes sociales. Actualmente, los datos del usuario se obtienen de un repositorio ficticio (`FakeUserDataRepository`), que simula la llegada de datos desde una **API remota** o una **base de datos local**.

Este ejemplo está pensado para enseñar de forma sencilla:

- Cómo organizar las capas de un proyecto (data, domain y UI).
- Cómo desacoplar la fuente de datos de la lógica de negocio mediante repositorios e interfaces.
- Cómo encapsular la lógica de negocio en casos de uso (`UseCases`).
- Cómo gestionar el estado de la UI con un `ViewModel` y Jetpack Compose.

---

## Objetivos del proyecto

1. **Demostrar un ejemplo práctico de MVVM**: Ver cómo la UI se comunica con el `ViewModel`, este llama a un `UseCase`, y finalmente llega a un `Repository` para obtener datos.
2. **Aplicar principios de Clean Architecture**: Separar la lógica de dominio, la fuente de datos y la capa de presentación en paquetes y clases diferentes.
3. **Servir como base educativa**: Para que compañeros o estudiantes puedan explorar y experimentar con la estructura de un proyecto real, pero de complejidad acotada.

---

## Estructura de paquetes

A continuación se detalla la **arquitectura por capas**:

```text
    app
     ├─ manifests
     │   └─ AndroidManifest.xml
     └─ java + kotlin
         └─ com.llginert.personalprofile
             ├─ data
             │   └─ repository
             │       └─ FakeUserDataRepository.kt
             ├─ domain
             │   ├─ models
             │   │   └─ User.kt
             │   ├─ repository
             │   │   └─ UserRepository.kt
             │   └─ useCases
             │       └─ GetUserUseCase.kt
             └─ ui
                 ├─ components
                 │   └─ SocialButton.kt
                 ├─ models
                 │   └─ UserUIModel.kt
                 ├─ profile
                 │   ├─ ProfileState.kt
                 │   └─ ProfileViewModel.kt
                 ├─ screens
                 │   └─ UserProfileScreen.kt
                 ├─ theme
                 │   └─ ...
                 └─ utils
                     └─ IconUtils.kt

         └─ MainActivity.kt
```

### Breve descripción de cada carpeta

- **data**

  - **repository**: Implementaciones concretas de las interfaces de la capa domain (por ejemplo, `FakeUserDataRepository`), simulando la obtención de datos desde un origen externo (API, base de datos, etc.).

- **domain**

  - **models**: Contiene las **entidades puras** que representan la lógica de negocio, por ejemplo `User.kt`.
  - **repository**: Contiene las **interfaces** de repositorio (por ejemplo, `UserRepository`) que definen el contrato para acceder a los datos.
  - **useCases**: Alberga los casos de uso (en este ejemplo, solo `GetUserUseCase`), que encapsulan la lógica de negocio y se encargan de interactuar con los repositorios.

- **ui**

  - **components**: Componentes reutilizables de Jetpack Compose (por ejemplo, `SocialButton`).
  - **models**: Modelos adaptados para la capa de presentación (`UserUIModel`), que pueden diferir de las entidades del dominio.
  - **profile**: El `ViewModel` (`ProfileViewModel`) y el estado de la pantalla (`ProfileState`).
  - **screens**: Las pantallas composables (en este caso, `UserProfileScreen`) que representan la UI principal.
  - **theme**: Ficheros relativos al tema (colores, tipografías, etc.) para Jetpack Compose.
  - **utils**: Clases de utilidad varias, por ejemplo `IconUtils.kt`.

- **MainActivity.kt**: El punto de entrada de la aplicación, donde se configura el `ViewModel` y se llama a la primera pantalla.

---

## Flujo de datos (Data Flow)

1. **UI (UserProfileScreen)**: En Jetpack Compose, la pantalla observa cambios de estado que emite el `ProfileViewModel`.
2. **ViewModel (ProfileViewModel)**: Mantiene y gestiona el estado de la UI (`ProfileState`). Cuando necesita datos del usuario, llama al caso de uso `GetUserUseCase`.
3. **UseCase (GetUserUseCase)**: Encapsula la lógica de negocio de “obtener usuario”. Recibe la implementación concreta de `UserRepository` (por inyección de dependencias o creación directa en este ejemplo).
4. **Repository**: Define la interfaz (`UserRepository`) y la entidad `User` en **domain**, mientras que la implementación concreta (`FakeUserDataRepository`) está en **data**. Al llamar a `getUser()`, se retorna un `User` con datos "hardcodeados" de ejemplo.
5. **Entidad de dominio (User)**: Representa la información real del usuario en la capa de negocio.
6. **Función de extensión `.toUI()`**: Convierte el `User` (dominio) en `UserUIModel` (UI), asegurando que la capa de presentación reciba los datos con el formato que necesita.
7. **Renderizado en la UI**: Finalmente, Jetpack Compose muestra la información en pantalla, actualizando la vista automáticamente cuando el estado (`ProfileState`) cambia.

> En un proyecto real, el repositorio podría obtener los datos de una **API** (Retrofit), de una **base de datos local** (Room) o incluso combinar varias fuentes.

![MVVM](https://objectivegroup.com/wp-content/uploads/2020/01/fluxo-de-comunicacao.png)

<sub>Imagen tomada de [Objective Group](https://objectivegroup.com/insights/clean-architecture-with-mvvm/) y utilizada con fines educativos. Se recomienda la lectura completa del articulo original.</sub>

---

## Tecnologías y librerías

- **Kotlin** como lenguaje principal.
- **Android Jetpack Compose** para la interfaz de usuario declarativa.
- **ViewModel** y **StateFlow** para el manejo de estado reactivo.
- **Coroutines** (se utilizarán con mayor profundidad más adelante).
- **Room** (planeado para una futura implementación de base de datos local).
- **FakeUserDataRepository** como un ejemplo de repositorio para pruebas y desarrollo, eliminando dependencias externas.

---

## Cómo ejecutar el proyecto

1. **Clona o descarga** este repositorio en tu máquina local.
2. **Abre el proyecto** con Android Studio (recomendado).
3. **Sincroniza el proyecto** para descargar las dependencias necesarias.
   - Asegúrate de tener la **versión de Gradle** y la **versión de Kotlin** requeridas (definidas en el `build.gradle`).
4. **Ejecuta la app** en un emulador o dispositivo físico con Android.
5. Deberías ver la pantalla principal mostrando un perfil ficticio.

---

## Posibles mejoras y tareas pendientes

1. **Implementar coroutines de forma completa**: Manejo de operaciones asíncronas y suspending functions para acceder a los repositorios.
2. **Manejo de errores**: Añadir `try/catch`, estados de error o retry si la fuente de datos falla.
3. **Base de datos local con Room**: Sustituir o complementar `FakeUserDataRepository` con una capa de persistencia local.
4. **Inyección de dependencias (DI)**: Utilizar librerías como **Hilt** o **Koin** para inyectar los casos de uso y repositorios.
5. **Múltiples pantallas**: Ampliar la app con más pantallas, otros casos de uso (CRUD: crear, leer, actualizar, eliminar usuarios, etc.).
6. **Optimizar la interfaz**: Mejorar el diseño visual y la experiencia de usuario.

---

## Licencia

Este proyecto está bajo la [Licencia MIT](LICENSE).

---

### Contribuciones

Este proyecto es principalmente educativo y de referencia. Siéntete libre de hacer un fork y experimentar, de proponer cambios y ofrecer feedback.

Gracias por usar este proyecto como guía para entender MVVM y Clean Architecture en Android.
