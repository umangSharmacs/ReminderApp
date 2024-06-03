package com.umang.reminderapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import com.umang.reminderapp.R

@Composable
fun EditorScreen(
    modifier: Modifier = Modifier,
    todoViewModel: TodoViewModel,
    navController: NavHostController,
    title: String = "",
    description: String = "",
    id: Int) {

    var titleInputText by remember {
        mutableStateOf(title)
    }

    var descriptionInputText by remember {
        mutableStateOf(description)
    }

    // Get todo_item from the manager

    val originalTodoItem = todoViewModel.getToDoItem(id)

    Surface( modifier = Modifier.background( color = Color.White)
    ){
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp, start = 12.dp)
        ) {
            OutlinedTextField(
                value = titleInputText,
                onValueChange = { titleInputText = it },
                label = { Text(text = "Add a Title") }
            )
            OutlinedTextField(
                value = descriptionInputText,
                onValueChange = { descriptionInputText = it },
                label = { Text(text = "Add a Description") }

            )

            ElevatedButton(
                onClick =  {
                    if (originalTodoItem != null) {
                        todoViewModel.updateTodoItem(
                            updatedTodoTitle = titleInputText,
                            updatedTodoDescription = descriptionInputText,
                            updatedTodoDueDate = originalTodoItem.dueDate,
                            updatedTodoTags = originalTodoItem.tags,
                            toUpdateTodoItemID = originalTodoItem.id
                        )
                    }
                    navController.popBackStack()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.Primary))
            ) {
                Text(text = "Edit",
                    color = Color.Black,
                )

            }
        }
    }

}

//
//@Preview(widthDp = 360, heightDp = 640)
//@Composable
//fun AdderScreenPreview() {
//    AdderScreen(Modifier)
//}