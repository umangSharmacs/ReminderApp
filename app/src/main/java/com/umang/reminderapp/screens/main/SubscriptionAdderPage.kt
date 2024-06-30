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
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.umang.reminderapp.alarm.AndroidAlarmSchedulerImpl
import com.umang.reminderapp.data.classes.BillingPeriod
import com.umang.reminderapp.data.models.SubscriptionViewModel
import com.umang.reminderapp.data.models.TagViewModel
import com.umang.reminderapp.ui.components.DurationUnitBox
import com.umang.reminderapp.ui.components.tags.TagDialog
import com.umang.reminderapp.ui.components.TopAppBarScaffold
import com.umang.reminderapp.util.daysToYMWD
import com.umang.reminderapp.util.getAlarms
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Date
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days
import kotlin.time.DurationUnit
import kotlin.time.toDuration


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubscriptionAdder(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    navController: NavHostController,
    scheduler: AndroidAlarmSchedulerImpl,
    subscriptionViewModel: SubscriptionViewModel,
    tagViewModel: TagViewModel,
    optionalID: Int?,
    optionalName: String = "",
    optionalCost: Double?,
    optionalStartDate: LocalDate? = null,
    optionalEndDate: LocalDate?= null,
    optionalBillingPeriod: BillingPeriod?,
    optionalTags: List<String> = emptyList(),
    editMode: Boolean = false,
) {

    val context = LocalContext.current

    val dateFormatter = DateTimeFormatter.ofPattern("dd MMM yy")

    // Name
    var NameInputText by remember { mutableStateOf(optionalName) }

    // Cost

    var selectedCost by remember { mutableDoubleStateOf(0.0) }

    if(editMode){
        if (optionalCost != null) {
            selectedCost = optionalCost
        }
    }

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

    if(editMode){
        startDateSelectedCounter = 1
    }

    // End date
    var selectedEndDate by remember {
        mutableStateOf(optionalEndDate)
    }
    // Duration

    var durationYears by remember { mutableStateOf(0) }
    var durationMonths by remember { mutableStateOf(0) }
    var durationWeeks by remember { mutableStateOf(0) }
    var durationDays by remember { mutableStateOf(0) }

    var editDurationCalculation by remember {
        mutableStateOf(true)
    }

    if(editMode && selectedStartDate!=null && selectedEndDate!=null && editDurationCalculation){

        Log.d("StartDate",selectedStartDate.toString())
        Log.d("EndDate",selectedEndDate.toString())
        val durationInYMWD = daysToYMWD(selectedStartDate!!, selectedEndDate!!)

        durationYears = durationInYMWD[0].toInt()
        durationWeeks = durationInYMWD[1].toInt()
        durationDays = durationInYMWD[2].toInt()

        editDurationCalculation = false

        Log.d("Duration", "$durationYears $durationMonths $durationWeeks $durationDays")

    }

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

    // Billing Period

    var selectedBillingPeriod by remember {
        mutableStateOf(BillingPeriod.MONTHLY)
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
            // Cost
            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(start = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                // TODO FIX COST
                Text(text = "Cost", modifier = Modifier.weight(1f))
                OutlinedTextField(
                    modifier = Modifier
                        .weight(2f)
                        .padding(5.dp),
                    value = selectedCost.toString(),
                    onValueChange = { selectedCost = it.toDouble() },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }

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
                    (if (startDateSelectedCounter == 0) "Enter a Date"
                    else {
                        val formattedString = selectedStartDate?.format(dateFormatter)
                        formattedString
                    })?.let {
                        Text(
                            text = it,
                        )
                    }
                }
            }



            // Duration
            Column(modifier = Modifier.padding(12.dp)){
                Text("Enter a Duration")
                Row(
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ){
//                Text(text = "Duration", modifier = Modifier.weight(1f))
                    DurationUnitBox(
                        modifier = Modifier
                            .padding(5.dp)
                            .weight(1f),
                        unit = "Years",
                        initialValue = durationYears.toString()
                    ) {
                        durationYears = if (it!="") it.toInt() else 0
                    }
                    DurationUnitBox(
                        modifier = Modifier
                            .padding(5.dp)
                            .weight(1f),
                        unit = "Months",
                        initialValue = durationMonths.toString()
                    ) {
                        durationMonths = if (it!="") it.toInt() else 0
                    }
                    DurationUnitBox(
                        modifier = Modifier
                            .padding(5.dp)
                            .weight(1f),
                        unit = "Weeks",
                        initialValue = durationWeeks.toString()
                    ) {
                        durationWeeks = if (it!="") it.toInt() else 0
                    }
                    DurationUnitBox(
                        modifier = Modifier
                            .padding(5.dp)
                            .weight(1f),
                        unit = "Days",
                        initialValue = durationDays.toString()
                    ) {
                        durationDays = if (it!="") it.toInt() else 0
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
                Text("Subscription End Date ", modifier = Modifier.weight(1f))
                selectedEndDate = selectedStartDate
                    ?.plusYears(durationYears.toLong())
                    ?.plusMonths(durationMonths.toLong())
                    ?.plusWeeks(durationWeeks.toLong())
                    ?.plusDays(durationDays.toLong())
                    ?.plusDays(durationDays.toLong())

                selectedEndDate?.let { Text(it.format(dateFormatter), modifier = Modifier.weight(2f), textAlign = TextAlign.Center) }
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

            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(start = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Billing Period", modifier = Modifier.weight(1f))

                var expandedBillingDropdown by remember { mutableStateOf(false) }
                var selectedBillingText by remember { mutableStateOf("Select a Period") }
                var userInteractedBilling by remember { mutableStateOf(false) }
                if(editMode and !userInteractedBilling){
                    selectedBillingText = when(optionalBillingPeriod){
                        BillingPeriod.DAILY -> "Daily"
                        BillingPeriod.WEEKLY -> "Weekly"
                        BillingPeriod.MONTHLY -> "Monthly"
                        BillingPeriod.YEARLY -> "Yearly"
                        else -> "Select a Period"
                    }
                    userInteractedBilling = true
                }

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
                                selectedBillingPeriod = BillingPeriod.DAILY
                                expandedBillingDropdown = !expandedBillingDropdown
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Weekly") },
                            onClick = {
                                selectedBillingText = "Weekly"
                                selectedBillingPeriod = BillingPeriod.WEEKLY
                                expandedBillingDropdown = !expandedBillingDropdown
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Monthly") },
                            onClick = {
                                selectedBillingText = "Monthly"
                                selectedBillingPeriod = BillingPeriod.MONTHLY
                                expandedBillingDropdown = !expandedBillingDropdown
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Yearly") },
                            onClick = {
                                selectedBillingText = "Yearly"
                                selectedBillingPeriod = BillingPeriod.YEARLY
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

                                val selectedDuration =  ChronoUnit.DAYS.between(selectedEndDate,selectedStartDate).toDuration(DurationUnit.DAYS)

                                val subscriptionItem = subscriptionViewModel.addSubscriptionItem(
                                    subscriptionName = NameInputText,
                                    duration = selectedDuration.toString(),
                                    startDate = selectedStartDate.toString(),
                                    endDate = selectedEndDate.toString(),
                                    tags = selectedTagsList,
                                    billingPeriod = selectedBillingPeriod,
                                    cost = selectedCost
                                )

                                // Get all the alarms that need to be set
                                val alarmsList = getAlarms(selectedStartDate!!, selectedEndDate!!, selectedBillingPeriod)
                                scheduler.scheduleSubscriptionAlarm(subscriptionItem, alarmsList)
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
                        Text(text = "Add")
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

                                // Cancel all Current alarms for this item
                                val oldAlarmsList = getAlarms(optionalStartDate!!, optionalEndDate!!, optionalBillingPeriod!!)

                                if ( optionalID != null) {
                                    subscriptionViewModel.getSubscriptionItem(optionalID)
                                        ?.let { scheduler.cancelAllSubscriptionAlarms(it, oldAlarmsList) }
                                }

                                val selectedDuration =  ChronoUnit.DAYS.between(selectedEndDate,selectedStartDate).toDuration(DurationUnit.DAYS)

                                val updatedSubscriptionItem = optionalID?.let {
                                    subscriptionViewModel.updateSubscriptionItem(
                                        updatedSubscriptionName = NameInputText,
                                        updatedDuration = selectedDuration.toString(),
                                        updatedStartDate = selectedStartDate.toString(),
                                        updatedEndDate = selectedEndDate.toString(),
                                        updatedTags = selectedTagsList,
                                        updatedBillingPeriod = selectedBillingPeriod,
                                        updatedCost = selectedCost,
                                        toUpdateID = optionalID
                                    )
                                }
////                                // Set alarms for the reminders again
                                val newAlarmsList = getAlarms(selectedStartDate!!, selectedEndDate!!, selectedBillingPeriod!!)
                                if (updatedSubscriptionItem != null) {
                                    scheduler.scheduleSubscriptionAlarm(updatedSubscriptionItem, newAlarmsList)
                                }
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
    navController: NavHostController,
    subscriptionViewModel: SubscriptionViewModel,
    tagViewModel: TagViewModel,
    scheduler: AndroidAlarmSchedulerImpl,
    optionalID: Int? = null,
    editMode: Boolean
) {

    var optionalName = ""
    var optionalCost = 0.0
    var optionalStartDate = LocalDate.now()
    var optionalEndDate = LocalDate.now()
    var optionalTags = emptyList<String>()
    var optionalBillingPeriod = BillingPeriod.MONTHLY

    if (editMode && optionalID != null){
        val editSubscriptionItem = subscriptionViewModel.getSubscriptionItem(optionalID)
        optionalName = editSubscriptionItem?.subscriptionName.toString()
        optionalCost = editSubscriptionItem?.cost!!
        optionalStartDate = LocalDate.parse(editSubscriptionItem.startDate)
        optionalEndDate = LocalDate.parse(editSubscriptionItem.endDate)
        optionalTags = editSubscriptionItem.tags!!
        optionalBillingPeriod = editSubscriptionItem.billingPeriod
    }


    Scaffold(
        topBar = @Composable {
            TopAppBarScaffold(
                header = if(!editMode) "Add a Subscription" else "Edit your Subscription",
                navigateBack = {navController.popBackStack()}
            )

        })
    { paddingValues ->
        SubscriptionAdder(
            modifier = modifier,
            paddingValues = paddingValues,
            navController = navController,
            scheduler = scheduler,
            subscriptionViewModel = subscriptionViewModel,
            tagViewModel = tagViewModel,
            optionalID = optionalID,
            optionalName = optionalName,
            optionalCost = optionalCost,
            optionalStartDate = optionalStartDate,
            optionalEndDate = optionalEndDate,
            optionalTags = optionalTags,
            optionalBillingPeriod = optionalBillingPeriod,
            editMode = editMode
        )
    }
}
