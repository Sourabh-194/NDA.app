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
fun NotesScreen(viewModel: AppViewModel) {
    var topic by remember { mutableStateOf("") }
    val response by viewModel.aiResponse.collectAsState()
    val isGenerating by viewModel.isGenerating.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text("FLASHCARDS", style = MaterialTheme.typography.labelMedium, color = androidx.compose.ui.graphics.Color(0xFF64748B))
        Spacer(modifier = Modifier.height(4.dp))
        Text("AI Notes Synthesizer", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        
        OutlinedTextField(
            value = topic,
            onValueChange = { topic = it },
            label = { Text("Topic (e.g., Vector Algebra)", style = MaterialTheme.typography.labelSmall) },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 3,
            textStyle = MaterialTheme.typography.bodyMedium,
            shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        
        Button(
            onClick = { viewModel.generateNotes(topic) },
            enabled = !isGenerating && topic.isNotBlank(),
            modifier = Modifier.fillMaxWidth().height(48.dp),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = androidx.compose.ui.graphics.Color(0xFF0A192F))
        ) {
            Text(if (isGenerating) "SYNTHESIZING..." else "GENERATE NOTES", style = MaterialTheme.typography.labelMedium)
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
        }
    }
}
