package com.example.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Notes
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ui.screens.CalendarScreen
import com.example.ui.screens.ChatScreen
import com.example.ui.screens.NotesScreen
import com.example.ui.screens.QuizScreen
import com.example.ui.screens.TimerScreen
import com.example.viewmodel.AppViewModel

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Chat : Screen("chat", "Core", Icons.Filled.Chat)
    object Notes : Screen("notes", "Notes", Icons.Filled.Notes)
    object Quiz : Screen("quiz", "Quiz", Icons.Filled.Quiz)
    object Calendar : Screen("calendar", "Routine", Icons.Filled.List)
    object Timer : Screen("timer", "Timer", Icons.Filled.Timer)
}

val items = listOf(
    Screen.Chat,
    Screen.Notes,
    Screen.Quiz,
    Screen.Calendar,
    Screen.Timer
)

@Composable
fun AppNavigation(viewModel: AppViewModel) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { AppBottomNavigation(navController = navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Chat.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Chat.route) { ChatScreen(viewModel) }
            composable(Screen.Notes.route) { NotesScreen(viewModel) }
            composable(Screen.Quiz.route) { QuizScreen(viewModel) }
            composable(Screen.Calendar.route) { CalendarScreen(viewModel) }
            composable(Screen.Timer.route) { TimerScreen(viewModel) }
        }
    }
}

@Composable
fun AppBottomNavigation(navController: NavController) {
    NavigationBar(
        containerColor = androidx.compose.ui.graphics.Color.White,
        contentColor = androidx.compose.ui.graphics.Color(0xFF64748B),
        tonalElevation = 8.dp
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { screen ->
            val selected = currentRoute == screen.route
            NavigationBarItem(
                icon = { Icon(screen.icon, contentDescription = screen.title) },
                label = { Text(screen.title.uppercase(), style = androidx.compose.material3.MaterialTheme.typography.labelSmall) },
                selected = selected,
                colors = androidx.compose.material3.NavigationBarItemDefaults.colors(
                    selectedIconColor = androidx.compose.ui.graphics.Color(0xFF00B4D8),
                    selectedTextColor = androidx.compose.ui.graphics.Color(0xFF00B4D8),
                    indicatorColor = androidx.compose.ui.graphics.Color(0xFFF1F5F9),
                    unselectedIconColor = androidx.compose.ui.graphics.Color(0xFF94A3B8),
                    unselectedTextColor = androidx.compose.ui.graphics.Color(0xFF94A3B8)
                ),
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
