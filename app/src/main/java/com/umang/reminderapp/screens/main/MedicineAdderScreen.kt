package com.umang.reminderapp.screens.main

import android.graphics.Color
import android.util.Log
import android.widget.Toast
import android.widget.ToggleButton
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role.Companion.Switch
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.umang.reminderapp.alarm.AndroidAlarmSchedulerImpl
import com.umang.reminderapp.data.classes.BillingPeriod
import com.umang.reminderapp.data.classes.MedicineIntakeTime
import com.umang.reminderapp.data.classes.MedicineMealType
import com.umang.reminderapp.data.classes.NavigationItem
import com.umang.reminderapp.data.models.MedicineViewModel
import com.umang.reminderapp.ui.components.DurationUnitBox
import com.umang.reminderapp.ui.components.TopAppBarScaffold
import com.umang.reminderapp.ui.components.medicine.MedicineTrackerUIComponent
import com.umang.reminderapp.ui.theme.ReminderAppTheme
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import kotlin.math.exp
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicineAdderScreen(
    modifier: Modifier = Modifier,
    medicineViewModel: MedicineViewModel,
    scheduler: AndroidAlarmSchedulerImpl,
    navController: NavHostController,
    optionalID: Int? = null,
    optionalName: String? = null,
    optionDuration: Int? = null,
    optionPrescriptionStart: String? = null,
    optionPrescriptionEnd: String? = null,
    optionWhenToTake: MutableMap<String, String?>? = null,
    optionalIsActive: Boolean? = null,
    optionalExpiry: String? = null,
    editMode: Boolean = false
) {

    // Check Notifications Permission

    var hasNotificationPermission = false
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            hasNotificationPermission = isGranted
//            if(!isGranted){
//                shouldShowRequestPermissionRationale(MainActivity)
//            }
        }
    )

    val context = LocalContext.current

    // Name
    var medicineName by remember {
        if (optionalName == null) mutableStateOf("") else mutableStateOf(optionalName)
    }

    // Is active
    var isActive by remember {
        if (optionalIsActive == null) mutableStateOf(false) else mutableStateOf(optionalIsActive)
    }

    // Duration
    var duration by remember {
        if (optionDuration == null) mutableStateOf(0) else mutableStateOf(optionDuration)
    }

    // Start Date
    var showStartDatePicker by remember { mutableStateOf(false) }
    var startDatePickerState = rememberDatePickerState(
        initialSelectedDateMillis = Instant.now().toEpochMilli()
    )
    var startDatePickedCounter by remember { mutableIntStateOf(0) }
    var prescriptionStart by remember {
        if (optionPrescriptionStart == null || !editMode) mutableStateOf(
            LocalDate.now().toString()
        ) else mutableStateOf(optionPrescriptionStart)
    }

    // End date
    var prescriptionEnd by remember {
        if (optionPrescriptionEnd == null || !editMode) mutableStateOf("") else mutableStateOf(
            optionPrescriptionEnd
        )
    }

    // When to take
    var breakFastChecked by remember {
        mutableStateOf(false)
    }

    var lunchChecked by remember {
        mutableStateOf(false)
    }

    var dinnerChecked by remember {
        mutableStateOf(false)
    }
    var editModeOptionallyChecked by remember { mutableStateOf(true) }

    if(editMode && editModeOptionallyChecked){
        val currentBreakfastChecked = optionWhenToTake?.get(MedicineMealType.BREAKFAST.value)!=MedicineIntakeTime.NONE.value
        val currentLunchChecked = optionWhenToTake?.get(MedicineMealType.LUNCH.value)!=MedicineIntakeTime.NONE.value
        val currentDinnerChecked = optionWhenToTake?.get(MedicineMealType.DINNER.value)!=MedicineIntakeTime.NONE.value
        breakFastChecked = currentBreakfastChecked
        lunchChecked = currentLunchChecked
        dinnerChecked = currentDinnerChecked
        editModeOptionallyChecked = false
    }


    // Expiry
    var showExpiryDatePicker by remember { mutableStateOf(false) }
    var expiryDatePickerState = rememberDatePickerState(
        initialSelectedDateMillis = Instant.now().toEpochMilli()
    )
    var expiryDatePickedCounter by remember { mutableIntStateOf(0) }

    if(editMode){
        expiryDatePickedCounter+=1
    }

    var expiryDate by remember {
        if (optionalExpiry == null) mutableStateOf("") else mutableStateOf(optionalExpiry)
    }

    // Medicine Intake
    var medicineIntake by remember { mutableStateOf("Select") }
    var userInteractIntake by remember { mutableStateOf(true) }

    if(editMode && userInteractIntake){
        userInteractIntake = false
        if(breakFastChecked){
            medicineIntake = when(optionWhenToTake?.get(MedicineMealType.BREAKFAST.value)){
                MedicineIntakeTime.BEFORE.value -> MedicineIntakeTime.BEFORE.value
                MedicineIntakeTime.WITH.value -> MedicineIntakeTime.WITH.value
                MedicineIntakeTime.AFTER.value -> MedicineIntakeTime.AFTER.value
                else -> "Select"
            }
        } else if( lunchChecked ){
            medicineIntake = when(optionWhenToTake?.get(MedicineMealType.LUNCH.value)){
                MedicineIntakeTime.BEFORE.value -> MedicineIntakeTime.BEFORE.value
                MedicineIntakeTime.WITH.value -> MedicineIntakeTime.WITH.value
                MedicineIntakeTime.AFTER.value -> MedicineIntakeTime.AFTER.value
                else -> "Select"
            }
        } else if( dinnerChecked ){
            medicineIntake = when(optionWhenToTake?.get(MedicineMealType.DINNER.value)){
                MedicineIntakeTime.BEFORE.value -> MedicineIntakeTime.BEFORE.value
                MedicineIntakeTime.WITH.value -> MedicineIntakeTime.WITH.value
                MedicineIntakeTime.AFTER.value -> MedicineIntakeTime.AFTER.value
                else -> "Select"
            }
        }

    }

    val dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy")

    // UI
    Surface(
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()

        ) {

            OutlinedTextField(
                modifier = Modifier
                    .padding(top = 5.dp, start = 12.dp),
//                    .weight(1f),
                value = medicineName,
                onValueChange = { medicineName = it },
                placeholder = {
                    Text(
                        "Medicine Name",
                        fontSize = 25.sp
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = androidx.compose.ui.graphics.Color.Transparent,
                    unfocusedBorderColor = androidx.compose.ui.graphics.Color.Transparent,
                ),
                textStyle = TextStyle.Default.copy(fontSize = 25.sp)
            )

            HorizontalDivider(
                modifier = Modifier
                    .height(2.dp)
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp),
                thickness = 2.dp,
                color = androidx.compose.ui.graphics.Color.Gray
            )

            // isActive

            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(start = 12.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Active Prescription ", modifier = Modifier.weight(1f))
                Switch(
                    modifier = Modifier.weight(1f),
                    checked = isActive,
                    onCheckedChange = { isActive = it }
                )
            }


            // Expiry Date

            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(start = 12.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Expiry Date ", modifier = Modifier.weight(1f))
                TextButton(
                    onClick = {
                        showExpiryDatePicker = true
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = androidx.compose.ui.graphics.Color.Transparent,
                        contentColor = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.weight(2f)
                ) {
                    Text(
                        text = if (expiryDatePickedCounter == 0) "Enter a Date"
                        else {
                            val formattedString = expiryDate.format(dateFormatter)
                            formattedString
                        },
                    )
                }
            }

            AnimatedVisibility(isActive) {

                HorizontalDivider(
                    modifier = Modifier
                        .height(2.dp)
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp),
                    thickness = 2.dp,
                    color = androidx.compose.ui.graphics.Color.Gray
                )
                // Prescription Text
                Column() {
                    Text(
                        modifier = Modifier.padding(12.dp),
                        text = "Prescription Details",
                        style = MaterialTheme.typography.titleLarge
                    )

                    // Prescription Start date
                    Row(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(start = 12.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Start Date ", modifier = Modifier.weight(1f))
                        TextButton(
                            onClick = {
                                showStartDatePicker = true
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = androidx.compose.ui.graphics.Color.Transparent,
                                contentColor = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary
                            ),
                            modifier = Modifier.weight(2f)
                        ) {
                            Text(
                                text = if (startDatePickedCounter == 0 && !editMode ) "Enter a Date"
                                else {
                                    val formattedString = prescriptionStart.format(dateFormatter)
                                    formattedString
                                },
                            )
                        }
                    }

                    // Duration Picker
                    Row(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(start = 12.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Duration ", modifier = Modifier.weight(1f))
                        DurationUnitBox(
                            modifier = Modifier
                                .padding(5.dp)
                                .weight(1f),
                            unit = "Days",
                            initialValue = duration.toString()
                        ) {
                            duration = if (it != "") it.toInt() else 0
                        }

                    }

                    // Prescription End Date
                    Row(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(start = 12.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "End Date", modifier = Modifier.weight(1f))
                        prescriptionEnd =
                            LocalDate.parse(prescriptionStart).plusDays(duration.toLong())
                                .toString()

                        if (prescriptionEnd == prescriptionStart) {
                            prescriptionEnd =
                                LocalDate.parse(prescriptionStart).plusDays(1).toString()
                        }
                        Text(
                            text = prescriptionEnd.format(dateFormatter),
                            modifier = Modifier.weight(2f),
                            textAlign = TextAlign.Center
                        )

                    }

                    // When to take
                    Row(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(start = 12.dp, top = 12.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Timings",
                            modifier = Modifier.weight(1f),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {

                        // Breakfast Checkbox
                        Column() {
                            Checkbox(
                                checked = breakFastChecked,
                                onCheckedChange = { breakFastChecked = !breakFastChecked }
                            )
                            Text("Breakfast", style = MaterialTheme.typography.bodyMedium)
                        }


                        LinearProgressIndicator(
                            modifier = Modifier.weight(1f),
                            progress = { 1f }

                        )

                        // Lunch Checkbox
                        Column() {
                            Checkbox(
                                checked = lunchChecked,
                                onCheckedChange = { lunchChecked = !lunchChecked }
                            )
                            Text("Lunch", style = MaterialTheme.typography.bodyMedium)

                        }

                        LinearProgressIndicator(
                            modifier = Modifier.weight(1f),
                            progress = { 1f },
                        )

                        // Dinner Checkbox
                        Column() {
                            Checkbox(
                                checked = dinnerChecked,
                                onCheckedChange = { dinnerChecked = !dinnerChecked }
                            )
                            Text("Dinner", style = MaterialTheme.typography.bodyMedium)
                        }

                    }

                    // Med Timings

                    Text(
                        modifier = Modifier.padding(start = 16.dp),
                        text = "Medicine Intake",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Column{
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, top = 5.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Text("Before a Meal")
                            RadioButton(
                                selected = medicineIntake==MedicineIntakeTime.BEFORE.value,
                                onClick = { medicineIntake = MedicineIntakeTime.BEFORE.value }
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, top = 5.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Text("With a Meal")
                            RadioButton(
                                selected = medicineIntake==MedicineIntakeTime.WITH.value,
                                onClick = { medicineIntake = MedicineIntakeTime.WITH.value }
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, top = 5.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Text("After a Meal")
                            RadioButton(
                                selected = medicineIntake==MedicineIntakeTime.AFTER.value,
                                onClick = { medicineIntake = MedicineIntakeTime.AFTER.value }
                            )
                        }
                    }

                }

            }

            // Add/ Edit button

            // Add Button
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ){
                if(!editMode){
                    ElevatedButton(
                        onClick = {
                            // Checks
                            if( medicineName == "" ){
                                Toast.makeText(
                                    context,
                                    "Please enter a Name",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else if( isActive && prescriptionStart == "" ){
                                Toast.makeText(
                                    context,
                                    "Please select a start date",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else if( expiryDate == "" ){
                                Toast.makeText(
                                    context,
                                    "Please select an expiry date",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else if( isActive && duration==0 ){
                                Toast.makeText(
                                    context,
                                    "Please select a duration",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {

                                var whenToTakeMap = mutableMapOf<String, String?>()

                                if(medicineIntake!="Select" && isActive){
                                    whenToTakeMap = mutableMapOf(
                                        if(breakFastChecked){
                                            MedicineMealType.BREAKFAST.value to medicineIntake
                                        } else {
                                            MedicineMealType.BREAKFAST.value to MedicineIntakeTime.NONE.value
                                        },
                                        if(lunchChecked){
                                            MedicineMealType.LUNCH.value to medicineIntake
                                        } else {
                                            MedicineMealType.LUNCH.value to MedicineIntakeTime.NONE.value
                                        },
                                        if(dinnerChecked){
                                            MedicineMealType.DINNER.value to medicineIntake
                                        } else {
                                            MedicineMealType.DINNER.value to MedicineIntakeTime.NONE.value
                                        }
                                    )
                                } else {
                                    whenToTakeMap = mutableMapOf(
                                        MedicineMealType.BREAKFAST.value to MedicineIntakeTime.NONE.value,
                                        MedicineMealType.LUNCH.value to MedicineIntakeTime.NONE.value,
                                        MedicineMealType.DINNER.value to MedicineIntakeTime.NONE.value,
                                    )
                                }

                                val defaultTakenMap = mutableMapOf(
                                    MedicineMealType.BREAKFAST.value to false,
                                    MedicineMealType.LUNCH.value to false,
                                    MedicineMealType.DINNER.value to false,
                                )

                                // Add item
                                val medicineItem = medicineViewModel.addMedicineItem(
                                    name = medicineName,
                                    prescriptionStart = prescriptionStart,
                                    duration = duration.toDuration(DurationUnit.DAYS).toString(),
                                    prescriptionEnd = prescriptionEnd,
                                    whenToTake = whenToTakeMap,
                                    takenMap = defaultTakenMap,
                                    isActive = isActive,
                                    expiry = expiryDate
                                )
                                // Create alarms

                                // Navigate back
                                navController.navigate(NavigationItem.Medicines.navRoute)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, end = 10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ){
                        Text(text = "Add")
                    }
                } else {

                    ElevatedButton(
                        onClick = {
                            // Checks
                            if( medicineName == "" ){
                                Toast.makeText(
                                    context,
                                    "Please enter a Name",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else if( !isActive && prescriptionStart == "" ){
                                Toast.makeText(
                                    context,
                                    "Please select a start date",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else if( expiryDate == "" ){
                                Toast.makeText(
                                    context,
                                    "Please select an expiry date",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else if( !isActive && duration==0 ){
                                Toast.makeText(
                                    context,
                                    "Please select a duration",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {

                                var whenToTakeMap = mutableMapOf<String, String?>()

                                if(medicineIntake!="Select" && isActive){
                                    whenToTakeMap = mutableMapOf(
                                        if(breakFastChecked){
                                            MedicineMealType.BREAKFAST.value to medicineIntake
                                        } else {
                                            MedicineMealType.BREAKFAST.value to MedicineIntakeTime.NONE.value
                                        },
                                        if(lunchChecked){
                                            MedicineMealType.LUNCH.value to medicineIntake
                                        } else {
                                            MedicineMealType.LUNCH.value to MedicineIntakeTime.NONE.value
                                        },
                                        if(dinnerChecked){
                                            MedicineMealType.DINNER.value to medicineIntake
                                        } else {
                                            MedicineMealType.DINNER.value to MedicineIntakeTime.NONE.value
                                        }
                                    )
                                } else {
                                    whenToTakeMap = mutableMapOf(
                                        MedicineMealType.BREAKFAST.value to MedicineIntakeTime.BEFORE.value,
                                        MedicineMealType.LUNCH.value to MedicineIntakeTime.BEFORE.value,
                                        MedicineMealType.DINNER.value to MedicineIntakeTime.BEFORE.value,
                                    )
                                }

                                val defaultTakenMap = mutableMapOf(
                                    MedicineMealType.BREAKFAST.value to false,
                                    MedicineMealType.LUNCH.value to false,
                                    MedicineMealType.DINNER.value to false,
                                )

                                // Cancel all current alarms

                                // Update item
                                if(optionalID!=null){
                                    val updatedMedicineItem = medicineViewModel.updateMedicineItem(
                                        toUpdateName = medicineName,
                                        toUpdatePrescriptionStart = prescriptionStart,
                                        toUpdateDuration = duration.toDuration(DurationUnit.DAYS).toString(),
                                        toUpdatePrescriptionEnd = prescriptionEnd,
                                        toUpdateWhenToTake = whenToTakeMap,
                                        toUpdateTakenMap = defaultTakenMap,
                                        toUpdateIsActive = isActive,
                                        toUpdateExpiry = expiryDate,
                                        toUpdateMedicineItemID = optionalID
                                    )
                                }

                                // Create new alarms

                                // Navigate Back
                                navController.navigate(NavigationItem.Medicines.navRoute)

                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, end = 10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ){
                        Text(text = "Edit")
                    }




                }
            }
        }
    }

    // Expiry Date Picker
    if(showExpiryDatePicker){
        DatePickerDialog(
            onDismissRequest = { showExpiryDatePicker = false },
            dismissButton = {
                OutlinedButton(
                    onClick = { showExpiryDatePicker = false },
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.onBackground
                    )
                ) {
                    Text("Back")
                }
            },
            confirmButton = {
                Button(onClick = {
                    expiryDate = Date(expiryDatePickerState.selectedDateMillis!!)
                        .toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate().toString()
                    showExpiryDatePicker = false
                    expiryDatePickedCounter+=1
                }
                ) {
                    Text("Confirm")
                }
            }
        ) {

            DatePicker(
                state = expiryDatePickerState
            )
        }
    }

    // Start Date Picker
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
                    prescriptionStart = Date(startDatePickerState.selectedDateMillis!!)
                        .toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate().toString()
                    showStartDatePicker = false
                    startDatePickedCounter+=1
                }
                ) {
                    Text("Confirm")
                }
            }
        ) {

            DatePicker(
                state = startDatePickerState
            )
        }
    }
}


@Composable
fun MedicineAdderPage(
    modifier: Modifier,
    medicineViewModel: MedicineViewModel,
    scheduler: AndroidAlarmSchedulerImpl,
    navController: NavHostController,
    optionalID: Int? = null,
    editMode: Boolean
){

    var optionalName = ""
    var optionalDuration = 0
    var optionPrescriptionStart = ""
    var optionPrescriptionEnd = ""
    var optionWhenToTake = emptyMap<String, String?>().toMutableMap()
    var optionalIsActive = false
    var optionalExpiry = ""

    if(editMode && optionalID!=null){

        val medicineItem = medicineViewModel.getMedicineItem(optionalID)

        optionalName = medicineItem?.name.toString()
        optionalDuration = Duration.parse(medicineItem?.duration!!).toInt(DurationUnit.DAYS)
        optionPrescriptionStart = medicineItem.prescriptionStart.toString()
        optionPrescriptionEnd = medicineItem.prescriptionEnd.toString()
        optionWhenToTake = medicineItem.whenToTake
        optionalIsActive = medicineItem.isActive == true
        optionalExpiry = medicineItem.expiry.toString()
    }

    Scaffold(
        topBar = @Composable {
            TopAppBarScaffold(
                header = if(editMode) "Edit Medicine" else "Add a Medicine",
                navigateIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() }
                    ) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft , contentDescription = "Back")
                    }

                }
            )
        }
    ){
        paddingValues ->
        MedicineAdderScreen(
            modifier = modifier.padding( top = paddingValues.calculateTopPadding()),
            medicineViewModel = medicineViewModel,
            scheduler = scheduler,
            navController = navController,
            optionalID = optionalID,
            optionalName = optionalName,
            optionDuration = optionalDuration,
            optionPrescriptionStart = optionPrescriptionStart,
            optionPrescriptionEnd = optionPrescriptionEnd,
            optionWhenToTake = optionWhenToTake,
            optionalIsActive = optionalIsActive,
            optionalExpiry = optionalExpiry,
            editMode = editMode
        )
    }
}
