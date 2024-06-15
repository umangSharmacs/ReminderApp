package com.umang.reminderapp.screens.main

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.umang.reminderapp.R
import com.umang.reminderapp.data.models.TodoViewModel
import com.umang.reminderapp.ui.components.TopAppBarScaffold
import com.umang.reminderapp.ui.components.dateTImeSelection
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun AdderScreenContent(
        modifier: Modifier = Modifier,
        todoViewModel: TodoViewModel,
        navController: NavHostController,
        optionalTitle: String = "",
        optionalDescription: String = "",
        paddingValues: PaddingValues = PaddingValues(0.dp,0.dp)) {

    val context = LocalContext.current
    var titleInputText by remember { mutableStateOf(optionalTitle) }
    var descriptionInputText by remember { mutableStateOf(optionalDescription) }

    // Due Date variables
    var selectedDateTime by remember { mutableStateOf<LocalDateTime?>(null) }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var selectedTime by remember { mutableStateOf(LocalTime.now()) }
    var dueDateSelectedCounter by remember {
        mutableIntStateOf(0)
    }
    val dueDateState = rememberMaterialDialogState()
    val dueTimeState = rememberMaterialDialogState()


    // Reminder Dates variables
    var remindersList = remember { mutableStateListOf<LocalDateTime>() }
    var reminderSelectedCounter by remember { mutableIntStateOf(0) }
    var reminderDateTime by remember { mutableStateOf<LocalDateTime?>(null) }
    var reminderDate by remember { mutableStateOf<LocalDate?>(null) }
    var reminderTime by remember { mutableStateOf<LocalTime?>(null) }

    var reminderDateState = rememberMaterialDialogState()
    var reminderTimeState = rememberMaterialDialogState()


        // UI

        Surface(
            modifier = Modifier
                .background(color = Color.White)
                .padding(top = paddingValues.calculateTopPadding())
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                // TITLE
                OutlinedTextField(
                    modifier = Modifier
                        .padding(top = 5.dp, start = 12.dp),
//                    .weight(1f),
                    value = titleInputText,
                    onValueChange = { titleInputText = it },
//                label = { Text(text = "Add a Title") },
                    placeholder = {
                        Text(
                            "Title",
                            fontSize = 25.sp
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                    ),
                    textStyle = TextStyle.Default.copy(fontSize = 25.sp)
                )

                HorizontalDivider(
                    modifier = Modifier
                        .height(2.dp)
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp),
                    thickness = 2.dp,
                    color = Color.Gray
                )
                // PROPERTIES
                // 1.  DUE DATE
                Row(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(start = 12.dp),
//                    .weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Due Date", modifier = Modifier.weight(1f))
                    TextButton(
                        onClick = {
                            dueDateState.show()
//                            dueDateTimeState = !dueDateTimeState
//                    dateTImeSelection()
                        },
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        Text(
                            text = if (dueDateSelectedCounter == 0) "Enter a Date"
                            else {
                                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm a")
                                val formattedString = selectedDate.atTime(selectedTime).format(formatter)
                                formattedString
                            },
                        )
                    }
                }

                val dueDateCheck by remember {
                    mutableStateOf(true)
                }
                if (dueDateSelectedCounter > 0 && selectedDate.atTime(selectedTime) < LocalDateTime.now() && dueDateCheck) {
                    Text(
                        modifier = Modifier
                            .padding(start = 12.dp),
                        fontSize = 10.sp,
                        fontFamily = FontFamily.Default,
                        text = "Selected Due date is in the past. You can still add the reminder.",
                    )
                }

                HorizontalDivider(
                    modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp),
                    thickness = 1.dp,
                    color = Color.Gray
                )
                // 2. TAGS (Placeholders)
                Row(
                    modifier = Modifier
                        .padding(start = 12.dp),
//                    .weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Tags", modifier = Modifier.weight(1f))
                    TextButton(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        Text(text = "Tags")
                    }
                }
                HorizontalDivider(
                    modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp),
                    thickness = 1.dp,
                    color = Color.Gray
                )
                // 3. REMINDERS (Placeholders)

                Row(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth()
                        .padding(start = 12.dp),
//                    .weight(1f),

                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(text = "Reminders", modifier = Modifier.weight(1f))

                    // List Reminders
                    Column(
                        modifier = Modifier
                            .weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        LazyColumn() {
                            items(remindersList.size) {
                                Row(){
                                    //TODO Format this date
                                    Text(text = remindersList[it].toString())
                                    //TODO Delete button
                                    IconButton(onClick = { remindersList.removeAt(it) }) {
                                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
                                    }

                                }

                            }
                        }
                        TextButton(
                            onClick = {
                                reminderDateState.show()
                            }
                        ) {
                            Text(text = "Add a Reminder")
                        }
                    }
                }

                HorizontalDivider(
                    modifier = Modifier
                        .height(2.dp)
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp),
                    thickness = 2.dp,
                    color = Color.Gray
                )

                // DESCRIPTION
                OutlinedTextField(
                    modifier = Modifier,
//                    .weight(5f),
                    value = descriptionInputText,
                    onValueChange = { descriptionInputText = it },
                    placeholder = {
                        Text(
                            "Description..."
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                    )

                )

                // Add button

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {

                    ElevatedButton(
                        onClick = {
                            if (titleInputText == "") {
                                Toast.makeText(context, "Please enter a title", Toast.LENGTH_SHORT)
                                    .show()
                            } else if (selectedDateTime == LocalDateTime.now()) {
                                Toast.makeText(
                                    context,
                                    "Please select a due date",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                todoViewModel.addTodoItem(
                                    title = titleInputText,
                                    description = descriptionInputText,
                                    dueDate = selectedDateTime.toString()
                                )
                                navController.popBackStack()
                            }

                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, end = 10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text(
                            text = "Add",
                            color = Color.Black,
                        )

                    }

                }
            }

        }


    // Due Date Dialog
    MaterialDialog(
        dialogState = dueDateState,
        buttons = {
            positiveButton(text = "Submit"){
                dueDateState.hide()
                dueTimeState.show()
            }
            negativeButton(text = "Cancel")
        }

    ) {
        datepicker(
            initialDate = LocalDate.now(),
            title = "Pick a Due date",

        ) {
            selectedDate = it
        }

    }
    // Due Time Dialog
    MaterialDialog(
        dialogState = dueTimeState,
        buttons = {
            positiveButton(text = "Submit"){
                dueDateSelectedCounter++
            }
            negativeButton(text = "Cancel")
        }

    ) {
        timepicker(
            initialTime = LocalTime.now(),
            title = "Pick a Due Time",

            ) {
            selectedTime = it
        }

    }

    // Reminder Date Dialog
    MaterialDialog(
        dialogState = reminderDateState,
        buttons = {
            positiveButton(text = "Submit"){
                reminderDateState.hide()
                reminderTimeState.show()
            }
            negativeButton(text = "Cancel")
        }

    ) {
        datepicker(
            initialDate = LocalDate.now(),
            title = "Pick a Reminder date",

            ) {
            reminderDate = it
        }

    }
    // Reminder Time Dialog
    MaterialDialog(
        dialogState = reminderTimeState,
        buttons = {
            positiveButton(text = "Submit"){
                // Make Reminder datetime
                reminderDateTime = reminderDate?.atTime(reminderTime)
                // Add to list
                reminderDateTime?.let { remindersList.add(it) }
            }
            negativeButton(text = "Cancel")
        }

    ) {
        timepicker(
            initialTime = LocalTime.now(),
            title = "Pick a reminder time",
            ) {
            reminderTime = it
        }

    }





}

@Composable
fun AdderScreen(
    modifier: Modifier = Modifier,
    todoViewModel: TodoViewModel,
    navController: NavHostController,
    optionalTitle: String = "",
    optionalDescription: String = ""
) {

    Scaffold(
        topBar = @androidx.compose.runtime.Composable {
            TopAppBarScaffold()
        })
    { paddingValues ->
        AdderScreenContent(
            modifier,
            todoViewModel,
            navController,
            optionalTitle,
            optionalDescription,
            paddingValues
        )
    }

}

@Preview(widthDp = 360, heightDp = 640)
@Composable
fun AdderScreenPreview() {
    AdderScreenContent(
        Modifier,
        TodoViewModel(),
        NavHostController(LocalContext.current)
    )
}