package com.umang.reminderapp.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarScaffold(
    modifier: Modifier = Modifier,
    header: String = "Memento",
    navigateIcon: () -> Unit
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(0.dp),
            scrolledContainerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(6.dp),
            titleContentColor = MaterialTheme.colorScheme.onSurface
        ),

        title = {
            Text(text = header)
        },
        navigationIcon = {
            IconButton(onClick =  navigateIcon ) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
            }
        }
    )
}
