package com.umang.reminderapp.ui.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun FloatingActionButton(modifier: Modifier = Modifier,
                         onClick: () -> Unit) {

    FloatingActionButton(
        onClick = onClick,
        modifier = modifier,
        containerColor = if(!isSystemInDarkTheme()) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.tertiary,
        contentColor = if(!isSystemInDarkTheme()) MaterialTheme.colorScheme.onTertiary else MaterialTheme.colorScheme.onTertiary

    ){
        Icon(Icons.Filled.Add, contentDescription = "Add")
    }
}