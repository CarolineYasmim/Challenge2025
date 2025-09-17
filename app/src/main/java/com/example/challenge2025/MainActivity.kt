package com.example.challenge2025

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.challenge2025.ui.screens.TestDescriptionScreen
import com.example.challenge2025.ui.screens.TestsScreen
import com.example.challenge2025.ui.theme.Challenge2025Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Challenge2025Theme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "tests"
                    ) {
                        composable("tests") {
                            TestsScreen(
                                onTestClick = { testItem ->
                                    navController.navigate("testDescription/${testItem.id}")
                                }
                            )
                        }

                        composable("testDescription/{testId}") { backStackEntry ->
                            val testId = backStackEntry.arguments?.getString("testId") ?: ""
                            TestDescriptionScreen(
                                testId = testId,
                                onStartTest = { navController.navigate("testQuestions/$testId/0") },
                                onExit = { navController.popBackStack() }
                            )
                        }

//                        composable("testQuestions/{testId}/{questionIndex}") { backStackEntry ->
//                            val testId = backStackEntry.arguments?.getString("testId") ?: ""
//                            val questionIndex =
//                                backStackEntry.arguments?.getString("questionIndex")?.toInt() ?: 0
//                            TestQuestionScreen(
//                                testId = testId,
//                                questionIndex = questionIndex,
//                                onAnswer = { nextIndex ->
//                                    navController.navigate("testQuestions/$testId/$nextIndex")
//                                },
//                                onFinish = {
//                                    navController.navigate("testResult/$testId")
//                                },
//                                onExit = { navController.popBackStack("tests", inclusive = false) }
//                            )
//                        }

//                        composable("testResult/{testId}") { backStackEntry ->
//                            val testId = backStackEntry.arguments?.getString("testId") ?: ""
////                            TestResultScreen(
////                                testId = testId,
////                                onContinue = {
////                                    navController.popBackStack(
////                                        "tests",
////                                        inclusive = false
////                                    )
////                                }
//                            )
//                        }
                    }
                }
            }
        }
    }
}
