package com.example.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.example.viewmodel.AppViewModel
import kotlinx.coroutines.delay

@Composable
fun TimerScreen(viewModel: AppViewModel) {
    val totalTime = 50 * 60 // 50 minutes
    var timeLeft by remember { mutableIntStateOf(totalTime) }
    var isRunning by remember { mutableStateOf(false) }

    LaunchedEffect(isRunning) {
        while (isRunning && timeLeft > 0) {
            delay(1000L)
            timeLeft--
            if (timeLeft == 0) {
                isRunning = false
                viewModel.logStudySession("Deep Work Complete", 50)
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("TACTICAL DEEP WORK", style = MaterialTheme.typography.labelMedium, color = androidx.compose.ui.graphics.Color(0xFF64748B))
        Spacer(modifier = Modifier.height(8.dp))
        Text("50 MIN PUSH", style = MaterialTheme.typography.headlineLarge, color = MaterialTheme.colorScheme.onSurface)
        
        Spacer(modifier = Modifier.height(48.dp))
        
        Box(contentAlignment = Alignment.Center, modifier = Modifier.size(220.dp)) {
            val progress = timeLeft.toFloat() / totalTime.toFloat()
            val primaryColor = MaterialTheme.colorScheme.primary
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawArc(
                    color = androidx.compose.ui.graphics.Color(0xFFE2E8F0),
                    startAngle = -90f,
                    sweepAngle = 360f,
                    useCenter = false,
                    style = Stroke(width = 12.dp.toPx(), cap = StrokeCap.Round)
                )
                drawArc(
                    color = primaryColor,
                    startAngle = -90f,
                    sweepAngle = 360f * progress,
                    useCenter = false,
                    style = Stroke(width = 12.dp.toPx(), cap = StrokeCap.Round)
                )
            }
            val minutes = timeLeft / 60
            val seconds = timeLeft % 60
            Text(
                text = String.format("%02d:%02d", minutes, seconds),
                style = MaterialTheme.typography.displayLarge
            )
        }
        
        Spacer(modifier = Modifier.height(48.dp))
        
        Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
            IconButton(
                onClick = { isRunning = !isRunning }, 
                modifier = Modifier.size(56.dp).background(androidx.compose.ui.graphics.Color(0xFF0A192F), androidx.compose.foundation.shape.CircleShape)
            ) {
                Icon(
                    if (isRunning) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                    contentDescription = "Play/Pause", 
                    modifier = Modifier.size(32.dp),
                    tint = androidx.compose.ui.graphics.Color.White
                )
            }
            IconButton(
                onClick = { 
                    isRunning = false
                    timeLeft = totalTime 
                }, 
                modifier = Modifier.size(56.dp).background(androidx.compose.ui.graphics.Color(0xFFE2E8F0), androidx.compose.foundation.shape.CircleShape)
            ) {
                Icon(Icons.Filled.Refresh, contentDescription = "Reset", modifier = Modifier.size(32.dp), tint = androidx.compose.ui.graphics.Color(0xFF0F172A))
            }
        }
    }
}
