package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.components.MathTexView
import com.example.viewmodel.AppViewModel

@Composable
fun QuizScreen(viewModel: AppViewModel) {
    var topic by remember { mutableStateOf("") }
    val response by viewModel.aiResponse.collectAsState()
    val isGenerating by viewModel.isGenerating.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text("AI QUIZ ENGINE", style = MaterialTheme.typography.labelMedium, color = androidx.compose.ui.graphics.Color(0xFF64748B))
        Spacer(modifier = Modifier.height(4.dp))
        Text("Mock Test Generator", style = MaterialTheme.typography.headlineMedium)
        Text("LATEX MATHEMATICS SUPPORT", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.height(16.dp))
        
        OutlinedTextField(
            value = topic,
            onValueChange = { topic = it },
            label = { Text("Topic (e.g. Trigonometry)", style = MaterialTheme.typography.labelSmall) },
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.bodyMedium,
            shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        
        Button(
            onClick = { viewModel.generateQuiz(topic) },
            enabled = !isGenerating && topic.isNotBlank(),
            modifier = Modifier.fillMaxWidth().height(48.dp),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = androidx.compose.ui.graphics.Color(0xFF0A192F))
        ) {
            Text(if (isGenerating) "GENERATING..." else "START QUIZ", style = MaterialTheme.typography.labelMedium)
        }
        
        Spacer(modifier = Modifier.height(20.dp))
        
        if (response != null && !isGenerating) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    MathTexView(latex = response!!)
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = { viewModel.logStudySession(topic, durationMinutes = 15, score = 3) },
                modifier = Modifier.fillMaxWidth().height(48.dp),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("COMPLETE & SYNC TO CORE", style = MaterialTheme.typography.labelMedium)
            }
        }
    }
}
