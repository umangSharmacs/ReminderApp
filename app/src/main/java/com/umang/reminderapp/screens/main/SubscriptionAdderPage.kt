package com.umang.reminderapp.screens.main

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.currentCompositionLocalContext
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.umang.reminderapp.data.models.TagViewModel
import com.umang.reminderapp.ui.components.TagDialog
import com.umang.reminderapp.ui.components.TopAppBarScaffold
import com.umang.reminderapp.ui.theme.ReminderAppTheme
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubscriptionAdder(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    tagViewModel: TagViewModel,
    optionalName: String = "",
    optionalStartDate: LocalDate = LocalDate.now(),
    optionalTags: List<String> = emptyList(),
    editMode: Boolean = false,
) {

    val context = LocalContext.current

    val dateFormatter = DateTimeFormatter.ofPattern("dd MMM yy")

    // Name
    var NameInputText by remember { mutableStateOf(optionalName) }

    // Start Date picker
    var showStartDatePicker by remember {
        mutableStateOf(false)
    }

    var selectedStartDate by remember {
        mutableStateOf(optionalStartDate)
    }

    var startDateState = rememberDatePickerState(
        initialSelectedDateMillis = Instant.now().toEpochMilli()
    )
    var startDateSelectedCounter by remember {
        mutableStateOf(0)
    }

    // Duration
    var selectedDuration by remember {
        mutableStateOf(Duration.ZERO)
    }
    var numDuration by remember { mutableStateOf(1) }
    var DurationUnit by remember { mutableStateOf("") }


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
        for (tag in data) {
            if (selectedTagsList.contains(tag)) continue
            else selectedTagsList.add(tag)
        }
    }


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
            // Name
            OutlinedTextField(
                modifier = Modifier
                    .padding(top = 5.dp, start = 12.dp),
//                    .weight(1f),
                value = NameInputText,
                onValueChange = { NameInputText = it },
//                label = { Text(text = "Add a Title") },
                placeholder = {
                    Text(
                        "Subscription Name",
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
            // Start Date
            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(start = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Start Date", modifier = Modifier.weight(1f))
                TextButton(
                    onClick = {
                        showStartDatePicker = true
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = if(isSystemInDarkTheme()) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.weight(2f)
                ) {
                    Text(
                        text = if (startDateSelectedCounter == 0) "Enter a Date"
                        else {
                            val formattedString = selectedStartDate.format(dateFormatter)
                            formattedString
                        },
                    )
                }
            }

            // Duration
            Row(
                modifier = Modifier
//                    .align(Alignment.CenterHorizontally)
                    .padding(start = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(text = "Duration", modifier = Modifier.weight(1f))
                Row(
                    modifier = Modifier.weight(2f),
                    horizontalArrangement = Arrangement.End
                ){
                    OutlinedTextField(
                        modifier = Modifier.width(50.dp),
                        value = numDuration.toString(),
                        onValueChange = {
                            if(it!="") {
                                numDuration = it.toInt()
                            } else {
                                // TODO Toast that duration cannot be less than 1.
                                numDuration = 1
                            }
                            Log.d("Subscription Adder", "numDuration: $numDuration")
                        }
                    )
                    var expandedDropdown by remember { mutableStateOf(false) }
                    var selectedText by remember { mutableStateOf("Duration") }

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
                                text = { Text("Day"+if(numDuration>1) "s" else "") },
                                onClick = {
                                    selectedText="Day"+if(numDuration>1) "s" else ""
                                    DurationUnit = "Days"
                                    selectedDuration = numDuration.days
                                    expandedDropdown = !expandedDropdown
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Week"+if(numDuration>1) "s" else "") },
                                onClick = {
                                    selectedText = "Week"+if(numDuration>1) "s" else ""
                                    selectedDuration = (numDuration*7).days
                                    expandedDropdown = !expandedDropdown
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Month"+if(numDuration>1) "s" else "") },
                                onClick = {
                                    selectedText = "Month"+if(numDuration>1) "s" else ""
                                    DurationUnit = "Months"
                                    selectedDuration = (numDuration*28).days
                                    expandedDropdown = !expandedDropdown
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Year"+if(numDuration>1) "s" else "") },
                                onClick = {
                                    selectedText = "Year"+if(numDuration>1) "s" else ""
                                    DurationUnit = "Years"
                                    selectedDuration = (numDuration*365).days
                                    expandedDropdown = !expandedDropdown
                                }
                            )
                        }
                    }
                }
            }

            // End Date

            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(start = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Text("End Date", modifier = Modifier.weight(1f))
                Text(selectedStartDate.plusDays(selectedDuration.inWholeDays).toString(), modifier = Modifier.weight(2f), textAlign = TextAlign.Center)

            }

            // Tags
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

            // Billing Period

            // Start Date
            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(start = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Billing Period", modifier = Modifier.weight(1f))

                var expandedBillingDropdown by remember { mutableStateOf(false) }
                var selectedBillingText by remember { mutableStateOf("Select a Period") }

                ExposedDropdownMenuBox(
                    modifier = Modifier
                        .weight(2f),
                    expanded = expandedBillingDropdown,
                    onExpandedChange = {expandedBillingDropdown = !expandedBillingDropdown}
                ) {
                    TextField(
                        value = selectedBillingText,
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
                        expanded = expandedBillingDropdown,
                        onDismissRequest = { expandedBillingDropdown = !expandedBillingDropdown}
                    ) {
                        DropdownMenuItem(
                            text = { Text("Daily" ) },
                            onClick = {
                                selectedBillingText = "Daily"
                                expandedBillingDropdown = !expandedBillingDropdown
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Weekly") },
                            onClick = {
                                selectedBillingText = "Weekly"
                                expandedBillingDropdown = !expandedBillingDropdown
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Monthly") },
                            onClick = {
                                selectedBillingText = "Monthly"
                                expandedBillingDropdown = !expandedBillingDropdown
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Yearly") },
                            onClick = {
                                selectedBillingText = "Yearly"
                                expandedBillingDropdown = !expandedBillingDropdown
                            }
                        )
                    }
                }

            }

            Spacer(modifier = Modifier.height(12.dp))

            // Add/Edit Button

            // Add button

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                if(!editMode){
                    ElevatedButton(
                        onClick = {

                            // Checks
                            if ( NameInputText == "") {
                                Toast.makeText(
                                    context,
                                    "Please enter a Name",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else if (selectedStartDate == null) {
                                Toast.makeText(
                                    context,
                                    "Please select a start date",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {

//                                val todoItem = todoViewModel.addTodoItem(
//                                    title = titleInputText,
//                                    description = descriptionInputText,
//                                    dueDate = selectedDateTime.toString(),
//                                    reminders = remindersList.map{it.toString()},
//                                    tags = selectedTagsList,
//                                    priority = selectedPriority
//                                )
//                                println(todoItem)
//                                // Add alarms for the reminders
//                                todoItem.let(scheduler::scheduleAlarm)
//                                Log.d("AdderPage","ScheduleAlarm called")
////                            todoItem.let(scheduler::scheduleAlarm)
//
//                                navController.popBackStack()
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
                            // Checks
                            if (NameInputText == "") {
                                Toast.makeText(
                                    context,
                                    "Please enter a Name",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else if (selectedStartDate == null) {
                                Toast.makeText(
                                    context,
                                    "Please select a start date",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                // Edit TodoItem

                                // Cancel all Current alarms for this item
//                                if (optionalID != null) {
//                                    todoViewModel.getToDoItem(optionalID)
//                                        ?.let { scheduler.cancelAllAlarms(it) }
//                                }
//
//                                val updatedTodoItem = optionalID?.let {
//                                    todoViewModel.updateTodoItem(
//                                        updatedTodoTitle = titleInputText,
//                                        updatedTodoDescription = descriptionInputText,
//                                        updatedTodoDueDate = selectedDateTime.toString(),
//                                        updatedReminders = remindersList.map{it.toString()},
//                                        updatedTodoTags = selectedTagsList,
//                                        updatedPriority = selectedPriority,
//                                        toUpdateTodoItemID = it
//                                    )
//                                }
////                                // Set alarms for the reminders again
//
//                                if (updatedTodoItem != null) {
//                                    scheduler.scheduleAlarm(updatedTodoItem)
//                                }
//                                Log.d("EditorPage","ScheduleAlarm called")
//                                navController.popBackStack()
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

    // Start date picker

    if(showStartDatePicker){
        DatePickerDialog(
            onDismissRequest = { showStartDatePicker = false },
            dismissButton = {
                OutlinedButton(
                    onClick = { showStartDatePicker = false },
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.onBackground
                    )

                ) {
                    Text("Back")
                }
            },
            confirmButton = {
                Button(onClick = {
                    selectedStartDate = Date(startDateState.selectedDateMillis!!)
                        .toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()

                    showStartDatePicker = false
                    startDateSelectedCounter++
                }
                ) {
                    Text("Confirm")
                }
            }
        ) {
            DatePicker(
                state = startDateState
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
fun SubscriptionAdderScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {

    Scaffold(
        topBar = @Composable {
            TopAppBarScaffold(
                header = "Add a Subscription",
                navigateBack = {navController.popBackStack()}
            )

        })
    { paddingValues ->
        SubscriptionAdder(
            modifier = modifier,
            paddingValues = paddingValues,
            TagViewModel()
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewSubscriptionAdder() {

    ReminderAppTheme {
        SubscriptionAdderScreen(Modifier,NavHostController(context = LocalContext.current))
    }
}