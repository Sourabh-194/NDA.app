package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.viewmodel.AppViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun CalendarScreen(viewModel: AppViewModel) {
    val logs by viewModel.allLogs.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(12.dp)) {
        Text("ROUTINE LOGS", style = MaterialTheme.typography.labelMedium, color = androidx.compose.ui.graphics.Color(0xFF64748B))
        Spacer(modifier = Modifier.height(4.dp))
        Text("Automated Daily Schedule", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            Text("AI RECOMMENDED BLOCKS", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
        }
        Spacer(modifier = Modifier.height(8.dp))
        
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = androidx.compose.ui.graphics.Color(0xFF0A192F)),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("09:00 - 11:00 : Mathematics (Calculus)", color = androidx.compose.ui.graphics.Color.White, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(4.dp))
                Text("11:30 - 13:00 : Physics (Mechanics)", color = androidx.compose.ui.graphics.Color.White, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(4.dp))
                Text("14:00 - 16:00 : Mock Test Review", color = androidx.compose.ui.graphics.Color.White, style = MaterialTheme.typography.titleMedium)
            }
        }
        
        Spacer(modifier = Modifier.height(20.dp))
        Text("RECENT LOGS SYNCED TO CORE", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.height(8.dp))
        
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(logs) { log ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                            Text(log.topic, style = MaterialTheme.typography.titleMedium)
                            Text("${log.durationMinutes}M", style = MaterialTheme.typography.labelSmall)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        val dateString = SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault()).format(Date(log.timestamp))
                        Text(dateString.uppercase(), style = MaterialTheme.typography.labelMedium, color = androidx.compose.ui.graphics.Color(0xFF64748B))
                        if (log.score != null) {
                            Text("SCORE: ${log.score}/3", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
                        }
                    }
                }
            }
        }
    }
}
