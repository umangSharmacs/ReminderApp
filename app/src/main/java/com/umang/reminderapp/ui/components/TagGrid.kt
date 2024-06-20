package com.umang.reminderapp.ui.components

import android.util.Log
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
import kotlin.math.ceil

@Composable
fun TagGrid(modifier: Modifier = Modifier, tags: MutableList<String> ) {

    val maxRows = ceil((tags.size.toFloat()/3))
    Log.d("Your Tags", tags.toList().toString())
    Log.d("Size",(maxRows).toString())
    while(tags.size<(maxRows*3)){
        tags.add("")
    }


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

@Preview
@Composable
fun TagGridPreview() {

    val tags = mutableListOf("tag","tag1","tag2","tag","tag")
    ReminderAppTheme {
        TagGrid(Modifier,tags)
    }

}