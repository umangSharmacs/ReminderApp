package com.example.reminderapp

import android.service.autofill.OnClickAction
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow

import java.util.Date
import kotlin.math.exp
import kotlin.math.max

data class todo_item(
    var id : Int,
    var title: String,
    var createdOn : Date,
    var dueDate : LocalDate,
    var tags : List<String>,
    var description  : String
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Item(modifier: Modifier = Modifier, todoItem: todo_item) {

    var expandedState by remember {
        mutableStateOf(false)
    }

    if (!expandedState){
        Box(
            modifier = modifier
                .requiredWidth(width = 331.dp)
                .requiredHeight(height = 79.dp)
                .clip(shape = RoundedCornerShape(15.dp))
                .clickable { expandedState = !expandedState }
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
//            Box(
//                modifier = Modifier
//                    .requiredWidth(width = 9.dp)
//                    .requiredHeight(height = 79.dp)
//                    .clip(shape = RoundedCornerShape(topStart = 15.dp, bottomStart = 15.dp))
//                    .background(color = Color(0xff1b998b))  // TODO: Make this dynamic
//            )
        }

    } else {
        Box(
            modifier = modifier
                .requiredWidth(width = 331.dp)
                .clip(shape = RoundedCornerShape(15.dp))
                .clickable { expandedState = !expandedState }
        ){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(shape = RoundedCornerShape(15.dp))
                    .background(color = Color(0xffd9d9d9)) // TODO: Make this dynamic
            ){
                Column(
                    modifier = Modifier
                        .padding(15.dp)
                )
                {
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

                    Divider(modifier = Modifier
                        .height(1.dp)
                        .width(500.dp),
                        color = Color.Gray,
                        thickness = 2.dp
                    )
                    Text(
                        text = todoItem.description,
                        color = Color.Black,
                        style = TextStyle(
                            fontSize = 15.sp),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 3)
                    ElevatedButton(onClick = { /*TODO*/ }) {
                        Icon(painter = painterResource(id = R.drawable.edit),
                            contentDescription = "edit",
                            modifier = Modifier
                                .background(color = Color(0xff1b998b)))
                    }

                }

            }

            // This is for the line on the left hand side.
//            Box(
//                modifier = Modifier
//                    .requiredWidth(width = 9.dp)
//                    .fillMaxHeight()
//                    .clip(shape = RoundedCornerShape(topStart = 15.dp, bottomStart = 15.dp))
//                    .background(color = Color(0xff1b998b))  // TODO: Make this dynamic
//            )


        }


    }

}

//@Preview(widthDp = 331, heightDp = 79)
//@Composable
//private fun ItemPreview() {
//    val todoItems = createDummyTodo()
//    val item = todoItems[0]
//    Item(Modifier, item)
//
//}



