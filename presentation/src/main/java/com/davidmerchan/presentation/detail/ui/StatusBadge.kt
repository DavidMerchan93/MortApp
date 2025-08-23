package com.davidmerchan.presentation.detail.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
internal fun StatusBadge(status: String) {
    val color = when (status.lowercase()) {
        "alive" -> MaterialTheme.colorScheme.primary
        "dead" -> MaterialTheme.colorScheme.error
        else -> MaterialTheme.colorScheme.outline
    }
    AssistChip(
        onClick = {},
        label = { Text(status) },
        leadingIcon = {
            Box(
                Modifier
                    .size(10.dp)
                    .clip(CircleShape)
                    .background(color)
            )
        }
    )
}
