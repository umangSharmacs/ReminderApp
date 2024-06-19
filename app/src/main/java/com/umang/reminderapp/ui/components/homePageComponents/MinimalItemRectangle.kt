package com.umang.reminderapp.ui.components.homePageComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.umang.reminderapp.data.classes.TodoItem
import com.umang.reminderapp.ui.theme.ReminderAppTheme


@Composable
fun MinimalItemRectangle(modifier: Modifier = Modifier, item: TodoItem) {

    Card(
        modifier = Modifier
            .aspectRatio(0.7f)
            .padding(8.dp)
            .height(150.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        shape = MaterialTheme.shapes.extraLarge,
        onClick = {/*TODO*/}
    ){
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,

        ){
            Text(
                text = item.title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 35.sp,
                overflow = TextOverflow.Ellipsis,
            )

            Text(
                text = "Due Today",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 20.sp,
                overflow = TextOverflow.Ellipsis
            )
        }
//        Box(
//            modifier = Modifier.padding(8.dp).align(Alignment.CenterHorizontally).fillMaxSize(),
//            contentAlignment = Alignment.Center,
//        ){
//
//
//        }
    }
}


@Preview
@Composable
fun MinimalItemRectanglePreview() {

    val item = TodoItem()

    ReminderAppTheme {
        MinimalItemRectangle(Modifier, item)
    }
}