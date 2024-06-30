package com.umang.reminderapp.ui.components.tags

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.umang.reminderapp.data.models.TagViewModel


@Composable
fun TagDialog(
    onDismissRequest: () -> Unit,
    tagViewModel: TagViewModel,
    onConfirmation: (List<String>) -> Unit,
    selectedTagsList: SnapshotStateList<String>
) {
    LaunchedEffect(Unit) {
        tagViewModel.getAllTags()
    }
    val tagList by tagViewModel.tagList.observeAsState()

//    var selectedTags = remember { mutableStateListOf<String>()}
    var selectedTags = selectedTagsList.distinct().toMutableList()

    Log.d("TagDialog-parameterList",selectedTagsList.toList().toString())
    var newTag by remember { mutableStateOf("") }

    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .wrapContentSize(Alignment.Center)
            ) {
                Text(text = "Select Tags", style = MaterialTheme.typography.titleMedium)
                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    tagList?.let {
                        items(it.toList()) { tag ->
                            var selected = tagCheckbox(
                                tagTitle = tag
                            )
                            if(selected){
                                selectedTags.add(tag)
                            }else{
                                selectedTags.remove(tag)
                            }
                        }
                    }
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ){
                    IconButton(
                        onClick = {
                            if (newTag != "") {
                                tagViewModel.addTag(newTag)
                                newTag = ""
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Add")
                    }
                    OutlinedTextField(
                        value = newTag,
                        onValueChange = {newTag = it},
                        label = { Text(text = "or Add a Tag") },
                    )
                }
                Button(
                    onClick = {
                        Log.d("TagDialog", "${selectedTags.toList()}")
                        onConfirmation(selectedTags)
                        onDismissRequest()
                              },
                    modifier = Modifier.padding(8.dp),
                ) {
                    Text("Confirm")
                }
            }
        }
    }
}

@Composable
fun tagCheckbox(
    modifier: Modifier = Modifier,
    tagTitle: String
):Boolean {
    var checkBoxState by remember { mutableStateOf(false) }
//    Log.d("CheckBoxState","$checkBoxState")
    Row(modifier = Modifier.padding(vertical = 8.dp)) {
        Checkbox(
            checked = checkBoxState,
            onCheckedChange = {
                checkBoxState = !checkBoxState
            }
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = tagTitle)

    }
    return checkBoxState
}



