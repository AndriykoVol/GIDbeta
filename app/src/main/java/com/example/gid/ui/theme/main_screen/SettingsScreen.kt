package com.example.gid.ui.theme.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.foundation.clickable

@Composable
fun SettingsScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Налаштування",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Theme Setting
        var darkMode by remember { mutableStateOf(false) }
        SettingItem(
            title = "Тема",
            subtitle = if (darkMode) "Темний режим" else "Світлий режим",
            endContent = {
                Switch(
                    checked = darkMode,
                    onCheckedChange = { darkMode = it }
                )
            }
        )

        HorizontalDivider()

        // Notification Settings
        var notificationsEnabled by remember { mutableStateOf(true) }
        SettingItem(
            title = "Сповіщення",
            subtitle = "Увімкнути сповіщення",
            endContent = {
                Switch(
                    checked = notificationsEnabled,
                    onCheckedChange = { notificationsEnabled = it }
                )
            }
        )

        HorizontalDivider()

        // Language Selection
        SettingItem(
            title = "Мова",
            subtitle = "Українська",
            endContent = {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "Обрати мову"
                )
            },
            onClick = { /* Open language selection dialog */ }
        )

        HorizontalDivider()

        // About the app
        SettingItem(
            title = "Про додаток",
            subtitle = "Версія 1.0.0",
            endContent = {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "Про додаток"
                )
            },
            onClick = { /* Navigate to About screen */ }
        )

        HorizontalDivider()

        // Help & Support
        SettingItem(
            title = "Допомога та підтримка",
            subtitle = "Напишіть нам",
            endContent = {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "Допомога та підтримка"
                )
            },
            onClick = { /* Navigate to Help screen */ }
        )

        HorizontalDivider()

        // Privacy Policy
        SettingItem(
            title = "Політика конфіденційності",
            subtitle = "Прочитати політику конфіденційності",
            endContent = {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "Політика конфіденційності"
                )
            },
            onClick = { /* Open privacy policy */ }
        )
    }
}

@Composable
fun SettingItem(
    title: String,
    subtitle: String,
    endContent: @Composable () -> Unit,
    onClick: () -> Unit = {}
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Box {
                endContent()
            }
        }
    }
}