package com.umang.reminderapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.umang.reminderapp.data.classes.TodoItem

@Composable
fun TodoCategoryHeader(
    modifier: Modifier = Modifier,
    text: String,
) {

    var expandedState by remember {
        mutableStateOf(false)
    }

    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = text,
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 16.dp),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
        )

//        IconButton(
//            onClick = {expandedState = !expandedState},
//            modifier = Modifier.padding(end = 16.dp)
//        ) {
//            Icon(
//                imageVector = if (expandedState) Icons.Filled.KeyboardArrowDown  else Icons.AutoMirrored.Filled.KeyboardArrowRight,
//                contentDescription = if (expandedState) "Collapse" else "Expand",
//                tint = MaterialTheme.colorScheme.onSurface
//            )
//        }

    }
}
