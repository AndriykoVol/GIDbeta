package com.example.gid.ui.theme.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.foundation.clickable

// Define custom green color scheme
val darkBlue = Color(0xFF1B3A2F)       // Темно-зелений (замість темно-синього)
val midBlue = Color(0xFF0F5711)        // Глибокий сіро-зелений
val lightBlue = Color(0xFF4F7751)      // Світліший зелений
val accentBlue = Color(0xFF88B04B)     // Акцентний зелений (лайм/оливковий)
val pureWhite = Color(0xFFFFFFFF)      // Білий без змін
val softWhite = Color(0xFFF5F5F5)      // М'який сірий замість білого
val darkBackground = Color(0xFF1C1C1C) // Темний сірий для фону
val cardBackground = Color(0xFF2E2E2E) // Карточний фон

@Composable
fun SettingsScreen(navController: NavController) {
    var darkMode by remember { mutableStateOf(false) }
    var notificationsEnabled by remember { mutableStateOf(true) }

    val backgroundColor = if (darkMode) darkBackground else softWhite
    val textColor = if (darkMode) pureWhite else darkBlue
    val surfaceColor = if (darkMode) cardBackground else pureWhite

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Налаштування",
                style = MaterialTheme.typography.headlineMedium,
                color = textColor,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Theme Setting
            SettingItem(
                title = "Тема",
                subtitle = if (darkMode) "Темний режим" else "Світлий режим",
                surfaceColor = surfaceColor,
                textColor = textColor,
                endContent = {
                    Switch(
                        checked = darkMode,
                        onCheckedChange = { darkMode = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = accentBlue,
                            checkedTrackColor = lightBlue,
                            uncheckedThumbColor = Color.Gray,
                            uncheckedTrackColor = Color.LightGray
                        )
                    )
                }
            )

            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider(color = if (darkMode) Color(0xFF3E3E3E) else Color(0xFFE0E0E0))
            Spacer(modifier = Modifier.height(8.dp))

            // Notification Settings
            SettingItem(
                title = "Сповіщення",
                subtitle = "Увімкнути сповіщення",
                surfaceColor = surfaceColor,
                textColor = textColor,
                endContent = {
                    Switch(
                        checked = notificationsEnabled,
                        onCheckedChange = { notificationsEnabled = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = accentBlue,
                            checkedTrackColor = lightBlue,
                            uncheckedThumbColor = Color.Gray,
                            uncheckedTrackColor = Color.LightGray
                        )
                    )
                }
            )

            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider(color = if (darkMode) Color(0xFF3E3E3E) else Color(0xFFE0E0E0))
            Spacer(modifier = Modifier.height(8.dp))

            // Language Selection
            SettingItem(
                title = "Мова",
                subtitle = "Українська",
                surfaceColor = surfaceColor,
                textColor = textColor,
                endContent = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "Обрати мову",
                        tint = if (darkMode) lightBlue else midBlue
                    )
                },
                onClick = { /* Open language selection dialog */ }
            )

            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider(color = if (darkMode) Color(0xFF3E3E3E) else Color(0xFFE0E0E0))
            Spacer(modifier = Modifier.height(8.dp))

            // About the app
            SettingItem(
                title = "Про додаток",
                subtitle = "Версія 1.0.0",
                surfaceColor = surfaceColor,
                textColor = textColor,
                endContent = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "Про додаток",
                        tint = if (darkMode) lightBlue else midBlue
                    )
                },
                onClick = { /* Navigate to About screen */ }
            )

            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider(color = if (darkMode) Color(0xFF3E3E3E) else Color(0xFFE0E0E0))
            Spacer(modifier = Modifier.height(8.dp))

            // Help & Support
            SettingItem(
                title = "Допомога та підтримка",
                subtitle = "Напишіть нам",
                surfaceColor = surfaceColor,
                textColor = textColor,
                endContent = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "Допомога та підтримка",
                        tint = if (darkMode) lightBlue else midBlue
                    )
                },
                onClick = { /* Navigate to Help screen */ }
            )

            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider(color = if (darkMode) Color(0xFF3E3E3E) else Color(0xFFE0E0E0))
            Spacer(modifier = Modifier.height(8.dp))

            // Privacy Policy
            SettingItem(
                title = "Політика конфіденційності",
                subtitle = "Прочитати політику конфіденційності",
                surfaceColor = surfaceColor,
                textColor = textColor,
                endContent = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "Політика конфіденційності",
                        tint = if (darkMode) lightBlue else midBlue
                    )
                },
                onClick = { /* Open privacy policy */ }
            )
        }
    }
}

@Composable
fun SettingItem(
    title: String,
    subtitle: String,
    surfaceColor: Color,
    textColor: Color,
    endContent: @Composable () -> Unit,
    onClick: () -> Unit = {}
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        color = surfaceColor,
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = textColor
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = textColor.copy(alpha = 0.7f)
                )
            }
            Box {
                endContent()
            }
        }
    }
}