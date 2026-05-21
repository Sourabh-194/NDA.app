package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.viewmodel.AppViewModel
import com.example.components.MathTexView

@Composable
fun ChatScreen(viewModel: AppViewModel) {
    var message by remember { mutableStateOf("") }
    val response by viewModel.aiResponse.collectAsState()
    val isGenerating by viewModel.isGenerating.collectAsState()
    
    // We'll store history locally for the session.
    var history by remember { mutableStateOf(listOf<Pair<String, String>>()) }

    LaunchedEffect(response) {
        if (response != null && message.isNotEmpty()) {
            history = history + (message to response!!)
            message = ""
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(12.dp)) {
        Text(text = "Commander Chat", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(12.dp))
        
        LazyColumn(modifier = Modifier.weight(1f).fillMaxWidth()) {
            items(history) { (userMsg, aiMsg) ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("YOU:", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
                        Text(userMsg, style = MaterialTheme.typography.bodyMedium)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("COMMANDER:", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
                        MathTexView(latex = aiMsg, modifier = Modifier.fillMaxWidth())
                    }
                }
            }
            if (isGenerating) {
                item {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally).padding(16.dp))
                }
            }
        }
        
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = message,
                onValueChange = { message = it },
                placeholder = { Text("Ask for schedule changes or reviews...") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(
                onClick = { 
                    if (message.isNotBlank()) viewModel.sendChatMessage(message)
                },
                enabled = !isGenerating && message.isNotBlank()
            ) {
                Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Send")
            }
        }
    }
}
