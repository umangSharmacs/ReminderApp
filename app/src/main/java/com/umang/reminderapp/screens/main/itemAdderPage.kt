package com.umang.reminderapp.screens.main

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.umang.reminderapp.ui.components.tags.TagDialog
import com.umang.reminderapp.ui.components.TimePickerDialog
import com.umang.reminderapp.ui.components.TopAppBarScaffold
import kotlinx.coroutines.launch
//import com.vanpra.composematerialdialogs.MaterialDialog
//import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
//import com.vanpra.composematerialdialogs.datetime.date.datepicker
//import com.vanpra.composematerialdialogs.datetime.time.TimePickerDefaults
//import com.vanpra.composematerialdialogs.datetime.time.timepicker
//import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdderScreenContent(
    modifier: Modifier = Modifier,
    todoViewModel: TodoViewModel,
    tagViewModel: TagViewModel,
    navController: NavHostController,
    scheduler: AndroidAlarmSchedulerImpl,
    optionalID: Int? = null,
    optionalTitle: String = "",
    optionalDescription: String = "",
    optionalPriority: Int = 3,
    optionalDueDate: LocalDateTime? = null,
    optionalReminders: List<LocalDateTime> = emptyList(),
    optionalTags: List<String> = emptyList(),
    editMode: Boolean = false,
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

    // Priority
    var selectedPriority by remember { mutableIntStateOf(optionalPriority) }

    // Due Date variables
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = Instant.now().toEpochMilli()
    )

    val dueTimePickerState = rememberTimePickerState(
        initialHour = 12,
        initialMinute = 0
    )

    var selectedDateTime by remember { mutableStateOf(optionalDueDate) }
    if (selectedDateTime==null){
        selectedDateTime = LocalDateTime.now()
    }

    var selectedDate by remember { mutableStateOf(selectedDateTime!!.toLocalDate()) }

    var selectedTime by remember { mutableStateOf(selectedDateTime!!.toLocalTime()) }

    var dueDateSelectedCounter by remember {
        mutableIntStateOf(0)
    }
    if (editMode){
        dueDateSelectedCounter = 1
    }


    // Due date variables

    var showDueDatePicker by remember {
        mutableStateOf(false)
    }

    var showDueTimePicker by remember {
        mutableStateOf(false)
    }

    // Reminder Dates variables

    var showReminderDatePicker by remember {
        mutableStateOf(false)
    }

    var showReminderTimePicker by remember {
        mutableStateOf(false)
    }

    val reminderDatePickerState = rememberDatePickerState(
        initialSelectedDateMillis = Instant.now().toEpochMilli()
    )

    val reminderTimePickerState = rememberTimePickerState(
        initialHour = 12,
        initialMinute = 0
    )

    var remindersList = remember { mutableStateListOf<LocalDateTime>() }

    var remindersSelectedCounter by remember {
        mutableIntStateOf(0)
    }

    var reminderInteracted by remember { mutableStateOf(false) }

    if( optionalReminders.isNotEmpty() && !reminderInteracted ){
        for(reminder in optionalReminders){
            if(reminder !in remindersList){
                remindersList.add(reminder)
            }
        }
//        optionalReminders = emptyList()
        Log.d("Editor Page","Optional Reminders")
    }



    var reminderDateTime by remember { mutableStateOf<LocalDateTime?>(null) }
    var reminderDate by remember { mutableStateOf<LocalDate?>(null) }
    var reminderTime by remember { mutableStateOf<LocalTime?>(null) }


    // Tags variables
    LaunchedEffect(Unit) {
        tagViewModel.getAllTags()
    }

//    val tagList by tagViewModel.tagList.observeAsState()

    var tagDialogState by remember { mutableStateOf(false)}
    var tagSelectedCounter by remember {
        mutableIntStateOf(0)
    }

    var selectedTagsList = remember { mutableStateListOf<String>() }

    fun onDialogConfirm(data: List<String>) {
        selectedTagsList.clear()
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
            .verticalScroll(rememberScrollState())
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
                              // TODO Uncomment
//                        dueDateState.show()
                              showDueDatePicker = true
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
                    .padding(start = 12.dp)
                    .height(100.dp),
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

                        // If in edit mode
                        if(editMode){
                            // If optionalTags are available and selectedTagsList is empty
                            if(optionalTags.isNotEmpty() && tagSelectedCounter==0){
                                items(optionalTags.size) {index ->
                                    AssistChip(
                                        modifier = Modifier.padding(5.dp),
                                        onClick = { },
                                        label = { Text(optionalTags[index]) }
                                    )
                                }
                            }
                            // Else if selectedTags are not empty
                            else if(selectedTagsList.isNotEmpty()) {
                                items(selectedTagsList.size) {index ->
                                    AssistChip(
                                        modifier = Modifier.padding(5.dp),
                                        onClick = { },
                                        label = { Text(selectedTagsList[index]) })
                                }
                            }
                        } else {
                            items(selectedTagsList.size) {index ->
                                AssistChip(
                                    modifier = Modifier.padding(5.dp),
                                    onClick = { },
                                    label = { Text(selectedTagsList[index]) })
                            }
                        }

                    }
                    TextButton(
                        onClick = { tagDialogState=true },
                        modifier = Modifier,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = if(isSystemInDarkTheme()) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary
                        )
                    ) {

                        if(editMode){
                            if(optionalTags.isNotEmpty() && tagSelectedCounter==0){
                                Text(text = "Change Tags")
                            } else if(optionalTags.isEmpty() && tagSelectedCounter==0){
                                Text(text = "Add a Tag")
                            } else if(tagSelectedCounter>0 && selectedTagsList.isEmpty()){
                                Text(text = "Add a Tag")
                            } else if(tagSelectedCounter>0 && selectedTagsList.isNotEmpty()){
                                Text(text = "Change Tags")
                            }
                        } else {
                            if(selectedTagsList.isEmpty()){
                                Text(text = "Add a Tag")
                            } else if(selectedTagsList.isNotEmpty()){
                                Text(text = "Change Tags")
                            }
                        }
                    }
                }

            }

            // 3. REMINDERS
            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .padding(start = 12.dp)
                    .height(150.dp),

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
                                IconButton(onClick = {
                                    remindersList.removeAt(it)
                                    reminderInteracted = true
                                }
                                ) {
                                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
                                }
                            }
                        }
                    }
                    TextButton(
                        onClick = {
                            showReminderDatePicker = true
                            reminderInteracted = true
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
                                selectedPriority = 1
                                expandedDropdown = !expandedDropdown
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Medium") },
                            onClick = {
                                selectedText = "Medium"
                                selectedPriority = 2
                                expandedDropdown = !expandedDropdown
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Low") },
                            onClick = {
                                selectedText = "Low"
                                selectedPriority = 3
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

            Spacer(modifier = Modifier.height(50.dp))

            // Add button
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                if(!editMode){
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
                                    priority = selectedPriority
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
                } else{
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
                                // Edit TodoItem

                                // Cancel all Current alarms for this item
                                if (optionalID != null) {
                                    todoViewModel.getToDoItem(optionalID)
                                        ?.let { scheduler.cancelAllAlarms(it) }
                                }

                                val updatedTodoItem = optionalID?.let {
                                    todoViewModel.updateTodoItem(
                                        updatedTodoTitle = titleInputText,
                                        updatedTodoDescription = descriptionInputText,
                                        updatedTodoDueDate = selectedDateTime.toString(),
                                        updatedReminders = remindersList.map{it.toString()},
                                        updatedTodoTags = selectedTagsList,
                                        updatedPriority = selectedPriority,
                                        toUpdateTodoItemID = it
                                    )
                                }
//                                // Set alarms for the reminders again

                                if (updatedTodoItem != null) {
                                    scheduler.scheduleAlarm(updatedTodoItem)
                                }
                                Log.d("EditorPage","ScheduleAlarm called")
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
                            text = "Edit",
                        )

                    }

                }


            }
        }

    }



    if(showDueDatePicker){
        DatePickerDialog(
            onDismissRequest = { showDueDatePicker = false },
            dismissButton = {
                OutlinedButton(
                    onClick = { showDueDatePicker = false },
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.onBackground
                    )

                ) {
                    Text("Back")
                }
            },
            confirmButton = {
                Button(onClick = {
                    selectedDate = Date(datePickerState.selectedDateMillis!!)
                        .toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()

                    showDueDatePicker = false
                    showDueTimePicker = true
                    dueDateSelectedCounter++
                }
                ) {
                    Text("Confirm")
                }
            }
        ) {

            DatePicker(
                state = datePickerState
            )

        }
    }

    if(showDueTimePicker){
        TimePickerDialog(
            title = "Select Time",
            onCancel = { showDueTimePicker = false },
            onConfirm = {
                selectedTime = LocalTime.of(dueTimePickerState.hour, dueTimePickerState.minute)
                showDueTimePicker = false
            }
        ) {
            TimePicker(
                state = dueTimePickerState,
                colors = TimePickerDefaults.colors(

                    periodSelectorSelectedContainerColor = MaterialTheme.colorScheme.primary,
                    periodSelectorSelectedContentColor = MaterialTheme.colorScheme.onPrimary,
                    periodSelectorUnselectedContainerColor = if(isSystemInDarkTheme()) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.secondary,
                    periodSelectorUnselectedContentColor = if(isSystemInDarkTheme()) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSecondary,
                    timeSelectorSelectedContainerColor = MaterialTheme.colorScheme.primary,
                    timeSelectorSelectedContentColor = MaterialTheme.colorScheme.onPrimary,
                    timeSelectorUnselectedContainerColor = if(isSystemInDarkTheme()) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.secondary,
                    timeSelectorUnselectedContentColor = if(isSystemInDarkTheme()) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSecondary
                )
            )
        }
    }

    // Reminder Date Dialog

    if(showReminderDatePicker){
        DatePickerDialog(
            onDismissRequest = { showReminderDatePicker = false },
            dismissButton = {
                OutlinedButton(
                    onClick = { showReminderDatePicker = false },
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.onBackground
                    )

                ) {
                    Text("Back")
                }
            },
            confirmButton = {
                Button(onClick = {
                    reminderDate = Date(reminderDatePickerState.selectedDateMillis!!)
                        .toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()
                    showReminderDatePicker = false
                    showReminderTimePicker = true
                }
                ) {
                    Text("Confirm")
                }
            }
        ) {

            DatePicker(
                state = reminderDatePickerState
            )
        }
    }

    if(showReminderTimePicker){
        TimePickerDialog(
            title = "Select Reminder Time",
            onCancel = { showReminderTimePicker = false },
            onConfirm = {
                reminderTime = LocalTime.of(reminderTimePickerState.hour, reminderTimePickerState.minute)
                reminderDateTime = reminderDate?.atTime(reminderTime)
                // Add to List
                reminderDateTime?.let { remindersList.add(it) }
                showReminderTimePicker = false
            }
        ) {
            TimePicker(
                state = reminderTimePickerState,
                colors = TimePickerDefaults.colors(
                    periodSelectorSelectedContainerColor = MaterialTheme.colorScheme.primary,
                    periodSelectorSelectedContentColor = MaterialTheme.colorScheme.onPrimary,
                    periodSelectorUnselectedContainerColor = if(isSystemInDarkTheme()) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.secondary,
                    periodSelectorUnselectedContentColor = if(isSystemInDarkTheme()) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSecondary,
                    timeSelectorSelectedContainerColor = MaterialTheme.colorScheme.primary,
                    timeSelectorSelectedContentColor = MaterialTheme.colorScheme.onPrimary,
                    timeSelectorUnselectedContainerColor = if(isSystemInDarkTheme()) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.secondary,
                    timeSelectorUnselectedContentColor = if(isSystemInDarkTheme()) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSecondary
                )
            )
        }
    }

    // Tag Dialog

    if(tagDialogState){
        TagDialog(
            onDismissRequest = { tagDialogState = false },
            tagViewModel = tagViewModel,
            onConfirmation = ::onDialogConfirm ,
            selectedTagsList = selectedTagsList
        )
        tagSelectedCounter+=1
    }

}

@Composable
fun AdderScreen(
    modifier: Modifier = Modifier,
    todoViewModel: TodoViewModel,
    tagViewModel: TagViewModel,
    navController: NavHostController,
    scheduler: AndroidAlarmSchedulerImpl,
    optionalID: Int? = null,
    editMode: Boolean = false
) {

    var optionalTitle: String = ""
    var optionalDescription: String = ""
    var optionalPriority: Int = 3
    var optionalDueDate: LocalDateTime? = null
    var optionalReminders: List<LocalDateTime> = emptyList()
    var optionalTags: List<String> = emptyList()
    if(editMode && optionalID != null) {

        val todoItem = optionalID.let { todoViewModel.getToDoItem(it) }
        todoItem?.let {
            optionalTitle = it.title
            optionalDescription = it.description
            optionalPriority = it.priority
            optionalDueDate = LocalDateTime.parse(it.dueDate)
            optionalReminders = it.reminders.map{ reminder -> LocalDateTime.parse(reminder) }
            optionalTags = it.tags
        }
    }

    Scaffold(
        topBar = @Composable {
            TopAppBarScaffold(
                header = if(editMode) "Edit" else "Add",
                navigateIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() }
                    ) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft , contentDescription = "Back")
                    }

                }
            )
        }
    )
    { paddingValues ->
        AdderScreenContent(
            modifier,
            todoViewModel,
            tagViewModel,
            navController,
            scheduler,
            optionalID,
            optionalTitle,
            optionalDescription,
            optionalPriority,
            optionalDueDate,
            optionalReminders,
            optionalTags,
            editMode,
            paddingValues
        )
    }

}
