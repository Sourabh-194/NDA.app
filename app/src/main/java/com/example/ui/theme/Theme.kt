package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val AppColorScheme =
  lightColorScheme(
    primary = TacticalBlue, 
    onPrimary = DeepNavy,
    secondary = DeepNavy,
    onSecondary = PlainWhite,
    background = PlainWhite,
    surface = PlainWhite,
    onBackground = Slate900,
    onSurface = Slate900,
    surfaceVariant = Slate50,
    onSurfaceVariant = Slate900,
    outline = Slate200
  )


@Composable
fun MyApplicationTheme(
  content: @Composable () -> Unit,
) {
  MaterialTheme(colorScheme = AppColorScheme, typography = Typography, content = content)
}
