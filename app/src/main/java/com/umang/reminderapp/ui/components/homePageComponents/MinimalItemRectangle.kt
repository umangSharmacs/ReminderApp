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
import java.time.LocalDate
import java.time.LocalDateTime


@Composable
fun MinimalItemRectangle(
    modifier: Modifier = Modifier,
    item: TodoItem,
    onClick: () -> Unit = {}
) {

    Card(
        modifier = Modifier
            .padding(8.dp)
            .height(150.dp)
            .width(150.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = if(LocalDateTime.parse(item.dueDate).toLocalDate() == LocalDate.now()) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.primary
        ),
        shape = MaterialTheme.shapes.extraLarge,
        onClick = onClick
    ){
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,

        ){
            Text(
                text = item.title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 20.sp,
                overflow = TextOverflow.Visible,
            )

            Text(
                text = if(LocalDateTime.parse(item.dueDate).toLocalDate() == LocalDate.now()) "Due Today" else "Reminder Today",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 15.sp,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}


@Preview
@Composable
fun MinimalItemRectanglePreview() {

    val item = TodoItem()
    item.dueDate = LocalDateTime.now().toString()

    ReminderAppTheme {
        MinimalItemRectangle(Modifier, item)
    }
}