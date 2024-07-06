package com.umang.reminderapp.ui.components.swiping

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun EditAction(modifier: Modifier, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .clickable { onClick() },
        contentAlignment = Alignment.Center,

    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 4.dp)
                    .padding(horizontal = 20.dp),
                imageVector = Icons.Filled.Edit,
                contentDescription = null,
                tint = Color.White
            )

            Text(
                text = "Edit",
                color = Color.White,
                fontSize = 12.sp,
            )
        }
    }
}

@Composable
fun DeleteAction(modifier: Modifier, onClick: () -> Unit) {
    Box(
        modifier = modifier.clickable { onClick() },
        contentAlignment = Alignment.Center

    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 4.dp)
                    .padding(horizontal = 20.dp),
                imageVector = Icons.Filled.Delete,
                contentDescription = null,
                tint = Color.White
            )

            Text(
                text = "Delete",
                color = Color.White,
                fontSize = 12.sp,
            )
        }
    }
}