package com.example.reminderapp

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

@Composable
fun AdderScreen(
    modifier: Modifier = Modifier,
    todoViewModel: TodoViewModel,
    navController: NavHostController,
    optionalTitle: String = "",
    optionalDescription: String = "") {

    var titleInputText by remember {
        mutableStateOf(optionalTitle)
    }

    var descriptionInputText by remember {
        mutableStateOf(optionalDescription)
    }

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
                    todoViewModel.addTodoItem(titleInputText, descriptionInputText)
                    navController.popBackStack()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.Primary))
            ) {
                Text(text = "Add",
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