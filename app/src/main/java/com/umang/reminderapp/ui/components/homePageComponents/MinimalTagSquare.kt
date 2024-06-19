package com.umang.reminderapp.ui.components.homePageComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.umang.reminderapp.ui.theme.ReminderAppTheme

@Composable
fun MinimalTagSquare(modifier: Modifier = Modifier, tag: String) {

    val backgroundColor = if(tag!="") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary

    Card(
        modifier = Modifier
            .aspectRatio(1f)
            .padding(8.dp)
            .width(12.dp)
            .height(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        shape = MaterialTheme.shapes.extraLarge,
        onClick = {/*TODO*/}
    ){
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = tag,
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.bodySmall,
//                textAlign = TextAlign.Center
            )
        }

    }
}

@Preview
@Composable
fun MinimalTagSquarePreview() {
    ReminderAppTheme {
        MinimalTagSquare(tag = "Hello")
    }
}