package com.umang.reminderapp.ui.components.medicine

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.umang.reminderapp.data.classes.MedicineIntakeTime
import com.umang.reminderapp.data.classes.MedicineItem
import com.umang.reminderapp.data.classes.MedicineMealType
import com.umang.reminderapp.data.models.MedicineViewModel
import com.umang.reminderapp.ui.theme.ReminderAppTheme
import java.time.LocalTime

@Composable
fun MedicineTrackerUIComponent(
    modifier: Modifier = Modifier,
    medicineItem: MedicineItem,
    BREAKFAST_TIME: LocalTime,
    LUNCH_TIME: LocalTime,
    DINNER_TIME: LocalTime,
    medicineTaken: MutableState<Boolean>,
    viewModel: MedicineViewModel
) {

    val current_time = LocalTime.now()

    // Check medicine times
    val breakfastMealType = medicineItem.whenToTake[MedicineMealType.BREAKFAST.value]
    val lunchMealType = medicineItem.whenToTake[MedicineMealType.LUNCH.value]
    val dinnerMealType = medicineItem.whenToTake[MedicineMealType.DINNER.value]

    // Calculate Progress
    val breakfastToLunchProgress =  if(current_time<BREAKFAST_TIME) 0 else (current_time.hour.toFloat() - BREAKFAST_TIME.hour.toFloat()) / (LUNCH_TIME.hour.toFloat() - BREAKFAST_TIME.hour.toFloat())
    val lunchToDinnerProgress = if(current_time<LUNCH_TIME) 0 else (current_time.hour.toFloat() - LUNCH_TIME.hour.toFloat()) / (DINNER_TIME.hour.toFloat() - LUNCH_TIME.hour.toFloat())


    // Current medicine Status
    var breakFastChecked by remember {
        mutableStateOf(medicineItem.takenMap[MedicineMealType.BREAKFAST.value])
    }

    var lunchChecked by remember {
        mutableStateOf(medicineItem.takenMap[MedicineMealType.LUNCH.value])
    }

    var dinnerChecked by remember {
        mutableStateOf(medicineItem.takenMap[MedicineMealType.DINNER.value])
    }
    
    // Ideal medicine Status
    val breakfastIdeal by remember {
        if(current_time<BREAKFAST_TIME){
            mutableStateOf(false)
        }
        else{
            mutableStateOf(true)
        }
    }

    val lunchIdeal by remember {
        if(current_time<LUNCH_TIME){
            mutableStateOf(false)
        }
        else{
            mutableStateOf(true)
        }
    }

    val dinnerIdeal by remember {
        if(current_time<DINNER_TIME){
            mutableStateOf(false)
        }
        else{
            mutableStateOf(true)
        }
    }


    if((breakfastMealType==MedicineIntakeTime.NONE.value || breakfastIdeal == breakFastChecked)
        && (lunchMealType==MedicineIntakeTime.NONE.value || lunchIdeal == lunchChecked)
        && (dinnerMealType==MedicineIntakeTime.NONE.value || dinnerIdeal == dinnerChecked )){
        medicineTaken.value = true
    } else {
        medicineTaken.value = false
    }

    Column(){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            // Breakfast Radio Button

            RadioButton(
                selected = breakFastChecked!!,
                onClick = {
                    breakFastChecked = !breakFastChecked!!
                    medicineItem.takenMap[MedicineMealType.BREAKFAST.value] = !medicineItem.takenMap[MedicineMealType.BREAKFAST.value]!!

                    // Update Medicine item
                    viewModel.updateMedicineItem(
                        toUpdateMedicineItemID = medicineItem.id,
                        toUpdateName = medicineItem.name,
                        toUpdatePrescriptionStart = medicineItem.prescriptionStart,
                        toUpdatePrescriptionEnd = medicineItem.prescriptionEnd,
                        toUpdateDuration = medicineItem.duration,
                        toUpdateIsActive = medicineItem.isActive,
                        toUpdateTakenMap = medicineItem.takenMap,
                        toUpdateWhenToTake = medicineItem.whenToTake,
                        toUpdateExpiry = medicineItem.expiry,
                    )
                          },
                enabled = breakfastMealType != MedicineIntakeTime.NONE.value,
                colors = RadioButtonDefaults.colors(
                    unselectedColor = if(breakfastIdeal && !breakFastChecked!!) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
                )

            )

            LinearProgressIndicator(
                modifier = Modifier.weight(1f),
                progress = { breakfastToLunchProgress.toFloat() }

            )

            // Lunch Radio Button
            RadioButton(
                selected = lunchChecked!!,
                onClick = {
                    lunchChecked = !lunchChecked!!
                    medicineItem.takenMap[MedicineMealType.LUNCH.value] = !medicineItem.takenMap[MedicineMealType.LUNCH.value]!!
                    // Update Medicine item
                    viewModel.updateMedicineItem(
                        toUpdateMedicineItemID = medicineItem.id,
                        toUpdateName = medicineItem.name,
                        toUpdatePrescriptionStart = medicineItem.prescriptionStart,
                        toUpdatePrescriptionEnd = medicineItem.prescriptionEnd,
                        toUpdateDuration = medicineItem.duration,
                        toUpdateIsActive = medicineItem.isActive,
                        toUpdateTakenMap = medicineItem.takenMap,
                        toUpdateWhenToTake = medicineItem.whenToTake,
                        toUpdateExpiry = medicineItem.expiry,
                    )
                          },
                enabled = lunchMealType != MedicineIntakeTime.NONE.value,
                colors = RadioButtonDefaults.colors(
                    unselectedColor = if(lunchIdeal && !lunchChecked!!) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
                )

            )
            LinearProgressIndicator(
                modifier = Modifier.weight(1f),
                progress = { lunchToDinnerProgress.toFloat() },
            )

            // Dinner Radio Button
            RadioButton(
                selected = dinnerChecked!!,
                onClick = {
                    dinnerChecked = !dinnerChecked!!
                    medicineItem.takenMap[MedicineMealType.DINNER.value] = !medicineItem.takenMap[MedicineMealType.DINNER.value]!!
                    // Update Medicine item
                    viewModel.updateMedicineItem(
                        toUpdateMedicineItemID = medicineItem.id,
                        toUpdateName = medicineItem.name,
                        toUpdatePrescriptionStart = medicineItem.prescriptionStart,
                        toUpdatePrescriptionEnd = medicineItem.prescriptionEnd,
                        toUpdateDuration = medicineItem.duration,
                        toUpdateIsActive = medicineItem.isActive,
                        toUpdateTakenMap = medicineItem.takenMap,
                        toUpdateWhenToTake = medicineItem.whenToTake,
                        toUpdateExpiry = medicineItem.expiry,
                    )
                          },
                enabled = dinnerMealType != MedicineIntakeTime.NONE.value,
                colors = RadioButtonDefaults.colors(
                    unselectedColor = if(dinnerIdeal && !dinnerChecked!!) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
                )
            )
        }

    }
}

//@Preview(showBackground = true)
//@Composable
//fun MedicineTrackerUIComponentPreview() {
//
//    val item = MedicineItem()
//
//    item.whenToTake[MedicineMealType.BREAKFAST.value] = MedicineIntakeTime.BEFORE.value
//    item.whenToTake[MedicineMealType.LUNCH.value] = MedicineIntakeTime.AFTER.value
//    item.whenToTake[MedicineMealType.DINNER.value] = MedicineIntakeTime.AFTER.value
//
//    val BREAKFAST_TIME = LocalTime.of(9, 0)
//    val LUNCH_TIME = LocalTime.of(13, 0)
//    val DINNER_TIME = LocalTime.of(21, 0)
//
//    val medicineTaken = remember { mutableStateOf(false) }
//
//    ReminderAppTheme {
//        MedicineTrackerUIComponent(
//            Modifier,
//            item,
//            BREAKFAST_TIME,
//            LUNCH_TIME,
//            DINNER_TIME,
//            medicineTaken
//        )
//    }
//
//}

