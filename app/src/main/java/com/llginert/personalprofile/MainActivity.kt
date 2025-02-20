package com.llginert.personalprofile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.llginert.personalprofile.data.repository.FakeUserDataRepository
import com.llginert.personalprofile.domain.useCases.GetUserUseCase
import com.llginert.personalprofile.ui.profile.ProfileViewModel
import com.llginert.personalprofile.ui.screens.UserProfileScreen
import com.llginert.personalprofile.ui.theme.PersonalProfileTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // Usa el tema definido
            PersonalProfileTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    /**
                     * Falta manejar la injección de dependencias, pero de esta forma, podemos ver como
                     * se llega desde la UI hasta el repositorio pasando por las distintas capas.
                     */
                    UserProfileScreen(
                        modifier = Modifier.padding(innerPadding),
                        viewModel = ProfileViewModel(
                            getUserUseCase = GetUserUseCase(
                                userRepository = FakeUserDataRepository()
                            )
                        )
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    PersonalProfileTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            UserProfileScreen(
                modifier = Modifier.padding(innerPadding),
                viewModel = ProfileViewModel(
                    getUserUseCase = GetUserUseCase(
                        userRepository = FakeUserDataRepository()
                    )
                )
            )
        }
    }
}