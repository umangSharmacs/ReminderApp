package com.umang.reminderapp.screens.main

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AssistChip
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
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
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.umang.reminderapp.alarm.AndroidAlarmSchedulerImpl
import com.umang.reminderapp.data.models.TagViewModel
import com.umang.reminderapp.data.models.TodoViewModel
import com.umang.reminderapp.ui.components.TagDialog
import com.umang.reminderapp.ui.components.TopAppBarScaffold
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.TimePickerDefaults
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdderScreenContent(
        modifier: Modifier = Modifier,
        todoViewModel: TodoViewModel,
        tagViewModel: TagViewModel,
        navController: NavHostController,
        scheduler: AndroidAlarmSchedulerImpl,
        optionalTitle: String = "",
        optionalDescription: String = "",
        paddingValues: PaddingValues = PaddingValues(0.dp,0.dp)
) {

    // Check Notifications Permission
    var hasNotificationPermission = false
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = {isGranted ->
            hasNotificationPermission = isGranted
//            if(!isGranted){
//                shouldShowRequestPermissionRationale(MainActivity)
//            }
        }
    )

    val context = LocalContext.current
    var titleInputText by remember { mutableStateOf(optionalTitle) }
    var descriptionInputText by remember { mutableStateOf(optionalDescription) }
    var priority by remember { mutableStateOf(3) }

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
    //  var reminderSelectedCounter by remember { mutableIntStateOf(0) }
    var reminderDateTime by remember { mutableStateOf<LocalDateTime?>(null) }
    var reminderDate by remember { mutableStateOf<LocalDate?>(null) }
    var reminderTime by remember { mutableStateOf<LocalTime?>(null) }

    var reminderDateState = rememberMaterialDialogState()
    var reminderTimeState = rememberMaterialDialogState()


    // Tags variables
    LaunchedEffect(Unit) {
        tagViewModel.getAllTags()
    }
    val tagList by tagViewModel.tagList.observeAsState()
    Log.d("AdderPage", "TagList: $tagList")

    var tagDialogState = remember { mutableStateOf(false)}
    var selectedTagsList = remember { mutableStateListOf<String>() }

    fun onDialogConfirm(data: List<String>) {
//        selectedTagsList.clear()
        for(tag in data){
            if (selectedTagsList.contains(tag)) continue
            else selectedTagsList.add(tag)
        }

    }

    val dateFormatter = DateTimeFormatter.ofPattern("dd MMM yy, h:mma")

    // UI

    Surface(
        modifier = Modifier
//            .background(color = Color.White)
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
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Due Date", modifier = Modifier.weight(1f))
                TextButton(
                    onClick = {
                        dueDateState.show()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = if(isSystemInDarkTheme()) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.weight(2f)
                ) {
                    Text(
                        text = if (dueDateSelectedCounter == 0) "Enter a Date"
                        else {
                            val formattedString = selectedDate.atTime(selectedTime).format(dateFormatter)
                            selectedDateTime = selectedDate.atTime(selectedTime)
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

            // 2. TAGS
            Row(
                modifier = Modifier
                    .padding(start = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "Tags", modifier = Modifier.weight(1f))

                Column(
                    modifier = Modifier.weight(2f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2)
                    ) {
                        items(selectedTagsList.size) {index ->
                            AssistChip(
                                modifier = Modifier.padding(5.dp),
                                onClick = { },
                                label = { Text(selectedTagsList[index]) })
                        }
                    }
                    TextButton(
                        onClick = { tagDialogState.value=true },
                        modifier = Modifier,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = if(isSystemInDarkTheme()) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary
                        )
                    ) {
                        if(selectedTagsList.isEmpty()){
                            Text(text = "Add a Tag")
                        }else{
                            Text(text = "Change Tags")
                        }
                    }
                }

            }

            // 3. REMINDERS (Placeholders)
            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .padding(start = 12.dp),

                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = "Reminders", modifier = Modifier.weight(1f))

                // List Reminders
                Column(
                    modifier = Modifier
                        .weight(2f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    LazyColumn {
                        items(remindersList.size) {
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ){
                                Text(
                                    text = remindersList[it].format(dateFormatter),
                                    fontSize = 15.sp,
                                    color = when {
                                        (selectedDateTime != null && remindersList[it] > selectedDateTime )-> MaterialTheme.colorScheme.error
                                        (remindersList[it] < LocalDateTime.now()) -> MaterialTheme.colorScheme.errorContainer
                                        else -> MaterialTheme.colorScheme.onBackground
                                    },
                                    style = if( remindersList[it] < LocalDateTime.now()) {
                                        TextStyle(
                                            textDecoration = TextDecoration.LineThrough
                                        )
                                    } else {
                                        TextStyle.Default
                                    }
                                )
                                IconButton(onClick = { remindersList.removeAt(it) }) {
                                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
                                }
                            }
                        }
                    }
                    TextButton(
                        onClick = {
                            reminderDateState.show()
                            if(!hasNotificationPermission){
                                permissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = if(isSystemInDarkTheme()) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary
                        )
                    ) {
                        if ( remindersList.size==0){
                            Text(text = "Add a Reminder")
                        } else Text("Add another Reminder")
                    }
                }
            }
            // 4. Priority
            Row(
                modifier = Modifier
                    .padding(start = 12.dp),
//                    .weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Priority", modifier = Modifier.weight(1f))

                var expandedDropdown by remember { mutableStateOf(false) }
                var selectedText by remember { mutableStateOf("Select a Priority") }

                ExposedDropdownMenuBox(
                    modifier = Modifier
                        .weight(2f),
                    expanded = expandedDropdown,
                    onExpandedChange = {expandedDropdown = !expandedDropdown}
                ) {
                    TextField(
                        value = selectedText,
                        onValueChange = {},
                        readOnly = true,
                        modifier = Modifier
                            .menuAnchor()
                            .weight(2f),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        textStyle = TextStyle.Default.copy(
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.SemiBold
                        )
                    )

                    ExposedDropdownMenu(
                        expanded = expandedDropdown,
                        onDismissRequest = { expandedDropdown = !expandedDropdown}
                    ) {
                        DropdownMenuItem(
                            text = { Text("High") },
                            onClick = {
                                selectedText="High"
                                priority = 1
                                expandedDropdown = !expandedDropdown
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Medium") },
                            onClick = {
                                selectedText = "Medium"
                                priority = 2
                                expandedDropdown = !expandedDropdown
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Low") },
                            onClick = {
                                selectedText = "Low"
                                priority = 3
                                expandedDropdown = !expandedDropdown
                            }
                        )

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
                        val reminderCheck = remindersList.filter{it-> it > selectedDateTime}
                        // Checks
                        if (titleInputText == "") {
                            Toast.makeText(
                                context,
                                "Please enter a title",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else if (selectedDateTime == null) {
                            Toast.makeText(
                                context,
                                "Please select a due date",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else if (remindersList.isEmpty()) {
                            Toast.makeText(
                                context,
                                "Please add a reminder",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        else if (
                            reminderCheck.isNotEmpty()
                        ){
                            Toast.makeText(
                                context,
                                "Reminder is after the due date",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {

                            val todoItem = todoViewModel.addTodoItem(
                                title = titleInputText,
                                description = descriptionInputText,
                                dueDate = selectedDateTime.toString(),
                                reminders = remindersList.map{it.toString()},
                                tags = selectedTagsList,
                                priority = priority
                            )
                            println(todoItem)
                            // Add alarms for the reminders
                            todoItem.let(scheduler::scheduleAlarm)
                            Log.d("AdderPage","ScheduleAlarm called")
//                            todoItem.let(scheduler::scheduleAlarm)

                            navController.popBackStack()
                        }

                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text(
                        text = "Add",
                    )

                }

            }
        }

    }

    // Due Date Dialog
    MaterialDialog(
        dialogState = dueDateState,
        buttons = {
            positiveButton(
                text = "Submit"
            ){
                dueDateState.hide()
                dueTimeState.show()
            }
            negativeButton(text = "Cancel")
        },
        backgroundColor = MaterialTheme.colorScheme.background
    ) {
        datepicker(
            initialDate = LocalDate.now(),
            title = "Pick a Due date",
            colors = DatePickerDefaults.colors(
                headerBackgroundColor = MaterialTheme.colorScheme.primaryContainer,
                headerTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                dateActiveBackgroundColor = MaterialTheme.colorScheme.primaryContainer
            )

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
        },
        backgroundColor = MaterialTheme.colorScheme.background

    ) {
        timepicker(
            initialTime = LocalTime.now(),
            title = "Pick a Due Time",
            colors = TimePickerDefaults.colors(
                selectorColor = MaterialTheme.colorScheme.primaryContainer,
                selectorTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                headerTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                activeBackgroundColor = MaterialTheme.colorScheme.primaryContainer,
                inactiveBackgroundColor = MaterialTheme.colorScheme.background
            )
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
        },
        backgroundColor = MaterialTheme.colorScheme.background

    ) {
        datepicker(
            initialDate = LocalDate.now(),
            allowedDateValidator = { it > LocalDate.now() },
            title = "Pick a Reminder date",
            colors = DatePickerDefaults.colors(
                headerBackgroundColor = MaterialTheme.colorScheme.primaryContainer,
                headerTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                dateActiveBackgroundColor = MaterialTheme.colorScheme.primaryContainer
            )
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
        },
        backgroundColor = MaterialTheme.colorScheme.background

    ) {
        timepicker(
            initialTime = LocalTime.now(),
            title = "Pick a reminder time",
            colors = TimePickerDefaults.colors(
                selectorColor = MaterialTheme.colorScheme.primaryContainer,
                selectorTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                headerTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                activeBackgroundColor = MaterialTheme.colorScheme.primaryContainer,
                inactiveBackgroundColor = MaterialTheme.colorScheme.background
            )
            ) {
            reminderTime = it
        }

    }

    // Tag Dialog

    if(tagDialogState.value){
        TagDialog(
            onDismissRequest = { tagDialogState.value = false },
            tagViewModel = tagViewModel,
            onConfirmation = ::onDialogConfirm ,
            selectedTagsList = selectedTagsList
        )
    }

}

@Composable
fun AdderScreen(
    modifier: Modifier = Modifier,
    todoViewModel: TodoViewModel,
    tagViewModel: TagViewModel,
    navController: NavHostController,
    scheduler: AndroidAlarmSchedulerImpl,
    optionalTitle: String = "",
    optionalDescription: String = ""
) {

    Scaffold(
        topBar = @Composable {
            TopAppBarScaffold()
        })
    { paddingValues ->
        AdderScreenContent(
            modifier,
            todoViewModel,
            tagViewModel,
            navController,
            scheduler,
            optionalTitle,
            optionalDescription,
            paddingValues
        )
    }

}

//@Preview(widthDp = 360, heightDp = 640)
//@Composable
//fun AdderScreenPreview() {
//    AdderScreenContent(
//        Modifier,
//        TodoViewModel(),
//        NavHostController(LocalContext.current),
//        scheduler: AndroidAlarmSchedulerImpl
//    )
//}