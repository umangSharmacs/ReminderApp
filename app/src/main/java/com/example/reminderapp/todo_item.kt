package com.example.reminderapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.Instant
import java.time.LocalDate


import java.util.Date

data class todo_item(
    var title: String,
    var createdOn : Date,
    var dueDate : LocalDate,
    var tags : List<String>,
    var description  : String
)

fun CreateDummyTodo():List<todo_item> {

    return listOf(
        todo_item(
            "First todo",
            Date.from(Instant.now()),
            LocalDate.parse("2024-06-24"),
            listOf("tag1","tag2"),
            "This is my first todo"
        ),
        todo_item(
            "Second todo",
            Date.from(Instant.now()),
            LocalDate.parse("2024-07-24"),
            listOf("tag1","tag2","tag3"),
            "This is my second todo"
        ),
        todo_item(
            "Third todo",
            Date.from(Instant.now()),
            LocalDate.parse("2025-06-24"),
            listOf("tag3","tag2"),
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                    "Phasellus vitae enim id dolor rhoncus vehicula vel nec ligula." +
                    " Donec mollis leo interdum, condimentum dui a, pulvinar nulla." +
                    " Mauris gravida neque non est convallis, viverra efficitur velit porta." +
                    " Nunc ut feugiat nulla, eget auctor erat. Donec eleifend, mi in bibendum suscipit," +
                    " ex enim cursus felis, vitae luctus velit ex id dolor. Phasellus in lectus volutpat," +
                    " scelerisque dui ut, porttitor risus. Morbi non augue augue."
        )
    )
}

//@Composable
//fun Todo_item(modifier: Modifier = Modifier) {
//
//    val todo_items = create_dummy_todo()
//    val item = todo_items[0]
//
//    Surface (
//        color = Color.Gray
//
//    ){
//        Row {
//            Text(item.title)
//
//            Column {
//                Text("Due on:")
//                Text(item.dueDate.toString())
//            }
//
//        }
//
//
//    }
//
//
//
//}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Item(modifier: Modifier = Modifier, todoItem: todo_item) {
    Box(
        modifier = modifier
            .requiredWidth(width = 331.dp)
            .requiredHeight(height = 79.dp)
            .clip(shape = RoundedCornerShape(15.dp))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(shape = RoundedCornerShape(15.dp))
                .background(color = Color(0xffd9d9d9)) // TODO: Make this dynamic
        )
        Row(
            modifier = Modifier
                .align(Alignment.Center)
                .matchParentSize()
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 15.dp)
                    .weight(1f)
            ) {
                Text(
                    text = todoItem.title,
                    color = Color.Black,
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold))

                Text(
                    text = "Due by "+todoItem.dueDate.toString(),
                    color = Color.DarkGray,
                    style = TextStyle(
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold))
            }
            Divider(modifier = Modifier
                .height(50.dp)
                .width(1.dp),
                color = Color.Gray,
                thickness = 2.dp
            )
            LazyRow(
                modifier = Modifier
                    .padding(start = 25.dp)
                    .weight(1f)
            ) {
                items(todoItem.tags) { tag ->
                    Text(text = tag,
                        modifier = Modifier
                            .padding(start = 10.dp))
                }
            }
        }

        // This is for the line on the left hand side.
        Box(
            modifier = Modifier
                .requiredWidth(width = 9.dp)
                .requiredHeight(height = 79.dp)
                .clip(shape = RoundedCornerShape(topStart = 15.dp, bottomStart = 15.dp))
                .background(color = Color(0xff1b998b)))  // TODO: Make this dynamic
    }
}

@Preview(widthDp = 331, heightDp = 79)
@Composable
private fun ItemPreview() {
    val todoItems = CreateDummyTodo()
    val item = todoItems[0]
    Item(Modifier, item)

}



