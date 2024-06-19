package com.umang.reminderapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.umang.reminderapp.ui.components.homePageComponents.MinimalTagSquare
import com.umang.reminderapp.ui.theme.ReminderAppTheme

@Composable
fun TagGrid(modifier: Modifier = Modifier, tags: List<String> ) {



    Column(modifier = modifier) {
        tags.chunked(3).forEach{chunkedList ->
            Row(modifier = Modifier.fillMaxWidth()) {
                 chunkedList.forEach {tag ->
                     Box(
                         modifier = Modifier
                             .weight(1f) // Makes each box occupy equal space
                             .padding(4.dp),
                     ){
                         MinimalTagSquare(tag = tag)
                     }


                 }
            }


        }
        for (row in 0..2) {

        }
    }
}

//        Row {
//            Text("Item 1")
//            Text("Item 2")
//            Text("Item 3")
//        }
//        Row {
//            Text("Item 4")
//            Text("Item 5")
//            Text("Item 6")
//        }
//        Row {
//            Text("Item 7")
//            Text("Item 8")
//            Text("Item 9")
//        }
//    }

//    LazyVerticalGrid(
//        columns = GridCells.Fixed(3),
//        modifier = modifier,
//        contentPadding = PaddingValues(8.dp,8.dp,8.dp,8.dp),
//
//    ) {
//        items(tags.take(9).size) { tag ->
//
//            MinimalTagSquare(tag = tags[tag])
//        }
//
//    }




//}


@Preview
@Composable
fun TagGridPreview() {

    val tags = listOf("tag","tag1","tag2","tag","","","","","")
    ReminderAppTheme {
        TagGrid(Modifier,tags)
    }

}