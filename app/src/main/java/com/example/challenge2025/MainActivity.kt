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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.challenge2025.ui.components.BottomBar
import com.example.challenge2025.ui.screens.MenuScreen
import com.example.challenge2025.ui.screens.auth.LoginScreen
import com.example.challenge2025.ui.screens.auth.SignUpScreen
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
import com.example.challenge2025.viewmodel.AuthViewModel
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
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ){
                    val navController = rememberNavController()
                    // ViewModels
                    val testViewModel: TestViewModel = viewModel()
                    val userViewModel: UserViewModel = viewModel()
                    val checkinViewModel: CheckinViewModel = viewModel()
                    val authViewModel: AuthViewModel = viewModel()

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
                            startDestination = "onboarding1",
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            /** Rotas de Onboarding, Auth e principais **/
                            composable("onboarding1") { OnboardingScreen1(navController) }
                            composable("onboarding2") { OnboardingScreen2(navController) }
                            composable("onboarding3") { OnboardingScreen3(navController) }
                            composable("login") { LoginScreen(navController, authViewModel) }
                            composable("signup") { SignUpScreen(navController, authViewModel) }

                            composable("home") {
                                HomeScreen(
                                    navController = navController,
                                    userViewModel = userViewModel
                                )
                            }

                            composable("tests") {
                                TestsScreen(
                                    userViewModel = userViewModel,
                                    onTestClick = { testItem ->
                                        navController.navigate("testDescription/${testItem.id}")
                                    }
                                )
                            }

                            composable("dashboard") { DashboardScreen(navController) }

                            composable("menu") {
                                MenuScreen(
                                    navController = navController,
                                    userViewModel = userViewModel
                                )
                            }

                            /** Rotas internas **/
                            // CORREÇÃO: Bloco "checkin" preenchido para usar o checkinViewModel
                            composable("checkin/{date}") { backStackEntry ->
                                val dateString = backStackEntry.arguments?.getString("date") ?: LocalDate.now().toString()
                                val date = LocalDate.parse(dateString)
                                CheckinScreen(
                                    date = date,
                                    viewModel = checkinViewModel,
                                    onClose = { navController.popBackStack() },
                                    onSubmit = { navController.popBackStack() }
                                )
                            }

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