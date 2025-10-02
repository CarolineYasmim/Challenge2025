package com.example.challenge2025

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.challenge2025.ui.components.assets.BottomBar
import com.example.challenge2025.ui.screens.auth.LoginScreen
import com.example.challenge2025.ui.screens.auth.SignUpScreen
import com.example.challenge2025.ui.screens.auth.StartScreen
import com.example.challenge2025.ui.screens.dashboard.DashboardScreen
import com.example.challenge2025.ui.screens.home.CheckinScreen
import com.example.challenge2025.ui.screens.home.HomeScreen
import com.example.challenge2025.ui.screens.menu.MenuScreen
import com.example.challenge2025.ui.screens.onboarding.OnboardingFlow
import com.example.challenge2025.ui.screens.onboarding.WelcomeScreen
import com.example.challenge2025.ui.screens.tests.TestDescriptionScreen
import com.example.challenge2025.ui.screens.tests.TestQuestionScreen
import com.example.challenge2025.ui.screens.tests.TestResultScreen
import com.example.challenge2025.ui.screens.tests.TestsScreen
import com.example.challenge2025.ui.theme.Challenge2025Theme
import com.example.challenge2025.ui.viewmodel.test.TestInProgressViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Challenge2025Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val mainAppRoutes = listOf("home", "tests", "dashboard", "menu")
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentRoute = navBackStackEntry?.destination?.route

                    Scaffold(
                        bottomBar = {
                            if (currentRoute in mainAppRoutes) {
                                BottomBar(navController = navController)
                            }
                        }
                    ) { innerPadding ->
                        NavHost(
                            navController = navController,
                            startDestination = "start_screen",
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            // MUDANÇA 2: As telas de Auth agora recebem lambdas para navegação
                            composable("start_screen") {
                                StartScreen(
                                    onLoginClick = { navController.navigate("login") },
                                    onSignUpClick = { navController.navigate("sign_up") }
                                )
                            }
                            composable("login") {
                                LoginScreen(
                                    onNavigateToSignUp = { navController.navigate("sign_up") },
                                    onLoginSuccess = { isFirstLogin ->
                                        val destination = if (isFirstLogin) "onboarding_route" else "home"
                                        navController.navigate(destination) {
                                            // Limpa a pilha de navegação para que o usuário não possa voltar para a tela de login
                                            popUpTo("start_screen") { inclusive = true }
                                        }
                                    }
                                )
                            }

                            composable("sign_up") {
                                SignUpScreen(
                                    onSignUpSuccess = { navController.navigate("login") }
                                )
                            }

                            navigation(
                                startDestination = "onboarding_welcome",
                                route = "onboarding_route"
                            ) {
                                composable("onboarding_welcome") { WelcomeScreen(navController) }
                                composable("onboarding_flow") { OnboardingFlow(navController) }
                            }

                            composable("home") { HomeScreen(navController) }
                            composable("tests") {
                                TestsScreen(
                                    onTestClick = { testItem ->
                                        navController.navigate("test_flow/${testItem.id}")
                                    }
                                )
                            }
                            composable("dashboard") { DashboardScreen() }
                            composable("menu") {
                                MenuScreen(
                                    onNavigateToPersonalData = { /* navController.navigate("personal_data_route") */ },
                                    onNavigateToCompanyData = { /* navController.navigate("company_data_route") */ },
                                    onNavigateToLanguage = { /* navController.navigate("language_route") */ },
                                    onNavigateToHelpCenter = { /* navController.navigate("help_center_route") */ },
                                    onLogout = {
                                        // Lógica para deslogar e navegar para a tela inicial
                                        navController.navigate("start_screen") {
                                            popUpTo(0) // Limpa toda a pilha de navegação
                                        }
                                    }
                                )
                            }
                            composable("checkin/{date}") { backStackEntry ->
                                // 1. Buscamos a data que veio na rota de navegação
                                val dateString = backStackEntry.arguments?.getString("date")
                                val date = if (dateString != null) LocalDate.parse(dateString) else LocalDate.now()

                                // 2. Passamos a data para a tela
                                CheckinScreen(
                                    date = date,
                                    onClose = { navController.popBackStack() },
                                    onSubmit = { navController.popBackStack() }
                                )
                            }

                            // MUDANÇA 3: O fluxo de teste está ajustado e usa a função sharedViewModel
                            navigation(
                                route = "test_flow/{testId}",
                                startDestination = "testDescription/{testId}"
                            ) {
                                composable("testDescription/{testId}") { backStackEntry ->
                                    val testId = backStackEntry.arguments?.getString("testId")
                                    TestDescriptionScreen(
                                        onExit = { navController.popBackStack() },
                                        onStartTest = {
                                            navController.navigate("testQuestion/$testId")
                                        }
                                    )
                                }
                                composable("testQuestion/{testId}") { backStackEntry ->
                                    val viewModel = backStackEntry.sharedViewModel<TestInProgressViewModel>(navController)
                                    TestQuestionScreen(
                                        navController = navController,
                                        viewModel = viewModel
                                    )
                                }
                                composable("testResult/{testId}") { backStackEntry ->
                                    val viewModel = backStackEntry.sharedViewModel<TestInProgressViewModel>(navController)
                                    TestResultScreen(
                                        viewModel = viewModel,
                                        onContinue = {
                                            navController.navigate("home") { // Volta para a home
                                                popUpTo(navController.graph.findStartDestination().id) {
                                                    inclusive = true
                                                }
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// MUDANÇA 4: A função sharedViewModel agora está correta e funcional
@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(navController: NavController): T {
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return hiltViewModel(parentEntry)
}