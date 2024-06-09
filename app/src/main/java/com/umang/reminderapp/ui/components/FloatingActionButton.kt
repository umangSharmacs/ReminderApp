package com.umang.reminderapp.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun FloatingActionButton(modifier: Modifier = Modifier,
                         onClick: () -> Unit) {

    FloatingActionButton(onClick = onClick, modifier = modifier){
        Icon(Icons.Filled.Add, contentDescription = "Add")
    }
}