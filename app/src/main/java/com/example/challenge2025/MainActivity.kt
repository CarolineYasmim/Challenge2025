package com.example.challenge2025

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.challenge2025.ui.components.BottomBar
import com.example.challenge2025.ui.screens.MenuScreen
import com.example.challenge2025.ui.screens.dashboard.DashboardScreen
import com.example.challenge2025.ui.screens.home.CheckinScreen
import com.example.challenge2025.ui.screens.home.HomeScreen
import com.example.challenge2025.ui.screens.onboarding.OnboardingScreen1
import com.example.challenge2025.ui.screens.onboarding.OnboardingScreen2
import com.example.challenge2025.ui.screens.onboarding.OnboardingScreen3
import com.example.challenge2025.ui.screens.tests.TestDescriptionScreen
import com.example.challenge2025.ui.screens.tests.TestQuestionScreen
import com.example.challenge2025.ui.screens.tests.TestResultScreen
import com.example.challenge2025.ui.screens.tests.TestsScreen
import com.example.challenge2025.ui.theme.Challenge2025Theme
import com.example.challenge2025.viewmodel.CalendarViewModel
import com.example.challenge2025.viewmodel.CheckinViewModel
import com.example.challenge2025.viewmodel.TestViewModel
import com.example.challenge2025.viewmodel.UserViewModel
import java.time.LocalDate

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Challenge2025Theme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    val testViewModel: TestViewModel = viewModel()
                    val userViewModel: UserViewModel = viewModel()
                    val calendarViewModel: CalendarViewModel = viewModel()
                    val checkinViewModel: CheckinViewModel = viewModel() // <<< CORRIGIDO: ViewModel instanciado

                    // As rotas principais onde a BottomBar deve aparecer
                    val mainAppRoutes = listOf("home", "tests", "dashboard", "menu")
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentRoute = navBackStackEntry?.destination?.route

                    Scaffold(
                        bottomBar = {
                            // A BottomBar só aparece se a rota atual estiver na lista de rotas principais
                            if (currentRoute in mainAppRoutes) {
                                BottomBar(navController = navController)
                            }
                        }
                    ) { innerPadding ->
                        NavHost(
                            navController = navController,
                            startDestination = "onboarding1",
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            composable("onboarding1") { OnboardingScreen1(navController) }
                            composable("onboarding2") { OnboardingScreen2(navController) }
                            composable("onboarding3") { OnboardingScreen3(navController) }

                            /** Rotas principais com BottomBar **/
                            composable("home") {
                                // <<< CORRIGIDO: Chamada de função com sintaxe correta
                                HomeScreen(
                                    navController = navController,
                                    userViewModel = userViewModel,
                                    calendarViewModel = calendarViewModel

                                )
                            }

                            composable("checkin/{date}") { backStackEntry ->
                                val dateString = backStackEntry.arguments?.getString("date")
                                    ?: LocalDate.now().toString()
                                val date = LocalDate.parse(dateString)

                                CheckinScreen(
                                    date = date,
                                    viewModel = checkinViewModel, // <<< CORRIGIDO: Referência agora existe
                                    onClose = { navController.popBackStack() },
                                    onSubmit = { navController.popBackStack() }
                                )
                            }

                            composable("tests") {
                                TestsScreen(
                                    // 1. Passe a instância do ViewModel que você já tem
                                    userViewModel = userViewModel,
                                    // 2. Passe a função de clique para o parâmetro 'onTestClick'
                                    onTestClick = { testItem ->
                                        navController.navigate("testDescription/${testItem.id}")
                                    }
                                )
                            }

                            composable("dashboard") { DashboardScreen(navController) }
                            composable("menu") {
                                MenuScreen(
                                    navController,
                                    userViewModel = userViewModel
                                ) }

                            /** Rotas internas SEM BottomBar **/
                            composable("testDescription/{testId}") { backStackEntry ->
                                val testId = backStackEntry.arguments?.getString("testId") ?: ""
                                TestDescriptionScreen(
                                    testId = testId,
                                    onStartTest = { navController.navigate("testQuestion/$testId/0") },
                                    onExit = { navController.popBackStack() }
                                )
                            }

                            composable("testQuestion/{testId}/{questionIndex}") { backStackEntry ->
                                val testId = backStackEntry.arguments?.getString("testId") ?: ""
                                val questionIndex = backStackEntry.arguments?.getString("questionIndex")?.toInt() ?: 0

                                TestQuestionScreen(
                                    testId = testId,
                                    questionIndex = questionIndex,
                                    viewModel = testViewModel,
                                    onNextQuestion = { nextIndex ->
                                        navController.navigate("testQuestion/$testId/$nextIndex")
                                    },
                                    onExit = { navController.popBackStack("tests", inclusive = false) },
                                    onFinishTest = { result ->
                                        testViewModel.markTestAsDone(result.testId)
                                        navController.navigate("testResult/${result.testId}")
                                    }
                                )
                            }

                            composable("testResult/{testId}") { backStackEntry ->
                                val testId = backStackEntry.arguments?.getString("testId") ?: ""
                                val result = testViewModel.lastResult

                                if (result != null && result.testId == testId) {
                                    TestResultScreen(
                                        result = result,
                                        timeSpentMillis = 60000L,
                                        onSupportClick = { /* suporte */ },
                                        onMoreInfoClick = { /* info */ },
                                        onContinue = {
                                            navController.popBackStack("tests", inclusive = false)
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