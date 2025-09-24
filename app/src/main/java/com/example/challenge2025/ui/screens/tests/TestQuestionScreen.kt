package com.example.challenge2025.ui.screens.tests

import android.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.challenge2025.model.data.MockData
import com.example.challenge2025.model.tests.Question
import com.example.challenge2025.model.user.UserAnswer
import com.example.challenge2025.model.user.UserTestResult
import com.example.challenge2025.ui.components.assets.BackButton
import com.example.challenge2025.viewmodel.TestViewModel

@Composable
fun TestQuestionScreen(
    testId: String,
    questionIndex: Int,
    onNextQuestion: (nextIndex: Int) -> Unit,
    onExit: () -> Unit,
    viewModel: TestViewModel = viewModel(),
    onFinishTest: (result: UserTestResult) -> Unit
) {
    val questions: List<Question> = MockData.getQuestionsForTest(testId)
    if (questionIndex >= questions.size) return
    val question = questions[questionIndex]

    val isDark = isSystemInDarkTheme()
    val buttonColor = if (isDark) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.primary
    val buttonTextColor = if (isDark) Color.Black else Color.White

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.systemBars.asPaddingValues())
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        BackButton(onClick = onExit)

        Spacer(modifier = Modifier.height(16.dp))

        LinearProgressIndicator(
        progress = { (questionIndex + 1) / questions.size.toFloat() },
        modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(MaterialTheme.shapes.small),
        color = MaterialTheme.colorScheme.primary,
        trackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
        strokeCap = ProgressIndicatorDefaults.LinearStrokeCap,
        )

        Spacer(modifier = Modifier.height(32.dp))

        Image(
            painter = painterResource(id = R.drawable.ic_menu_help),
            contentDescription = "Ilustração da pergunta",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
                .clip(RoundedCornerShape(16.dp))
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = question.text,
            style = MaterialTheme.typography.headlineSmall.copy(
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(modifier = Modifier.height(48.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            question.options.forEach { option ->
                Button(
                    onClick = {
                        // Salva a resposta do usuário
                        viewModel.addAnswer(
                            UserAnswer(
                                testId = testId,
                                questionId = question.id,
                                selectedOption = option.id.toIntOrNull() ?: 0,
                                value = option.value
                            )
                        )

                        val nextIndex = questionIndex + 1
                        if (nextIndex < questions.size) {
                            // Próxima pergunta
                            onNextQuestion(nextIndex)
                        } else {
                            // Última pergunta → envia todas as respostas e retorna resultado
                            viewModel.submitTest { result ->
                                viewModel.markTestAsDone(result.testId)
                                onFinishTest(result)
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = buttonColor,
                        contentColor = buttonTextColor
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = option.text,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
