package com.example.challenge2025.ui.components.tests

import androidx.compose.foundation.layout.Arrangement // Importe o Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.challenge2025.model.tests.TestItem

@Composable
fun TestListContainer(
    title: String,
    tests: List<TestItem>,
    onTestClick: (TestItem) -> Unit
) {
    // A Column principal já existe, não precisa de outra.
    // Vamos adicionar o espaçamento a ela.
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Esta Column interna agrupará os cards e aplicará o espaçamento
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            tests.forEach { test ->
                TestCard(testItem = test, onClick = onTestClick)
            }
        }
    }
}