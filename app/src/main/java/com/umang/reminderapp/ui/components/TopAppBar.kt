package com.umang.reminderapp.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.umang.reminderapp.R
import com.umang.reminderapp.ui.theme.ReminderAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarScaffold(modifier: Modifier = Modifier) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(0.dp),
            scrolledContainerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(6.dp),
            titleContentColor = MaterialTheme.colorScheme.onSurface
        ),
        title = {
            Text(text = "Reminder App")
        }
    )
}

@Preview
@Composable
fun TopAppBarPreview() {
    ReminderAppTheme {
        TopAppBarScaffold()
    }
}