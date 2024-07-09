package com.umang.reminderapp.ui.components.medicine

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedAssistChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.umang.reminderapp.R
import com.umang.reminderapp.data.classes.MealTiming
import com.umang.reminderapp.data.classes.MedicineIntakeTime
import com.umang.reminderapp.data.classes.MedicineItem
import com.umang.reminderapp.data.classes.MedicineMealType
import com.umang.reminderapp.data.models.MedicineViewModel
import com.umang.reminderapp.ui.theme.ReminderAppTheme
import com.umang.reminderapp.util.getNextIntakeTime
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import kotlin.math.exp
import kotlin.math.roundToInt
import kotlin.time.DurationUnit
import kotlin.time.toDuration
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MedicineCard(
    modifier: Modifier = Modifier,
    item: MedicineItem,
    viewModel: MedicineViewModel
) {

    var expandedState by remember { mutableStateOf(false) }

    val BREAKFAST_TIME = LocalTime.parse(MealTiming.BREAKFAST.time)
    val LUNCH_TIME = LocalTime.parse(MealTiming.LUNCH.time)
    val DINNER_TIME = LocalTime.parse(MealTiming.DINNER.time)

    var medicineTaken = rememberSaveable {
        mutableStateOf(true)
    }

    val dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy")

    var nextDose by remember {
        mutableLongStateOf(0)
    }

    val mealTimings = mutableMapOf(
        MedicineMealType.BREAKFAST.value to MealTiming.BREAKFAST.time,
        MedicineMealType.LUNCH.value to MealTiming.LUNCH.time,
        MedicineMealType.DINNER.value to MealTiming.DINNER.time
    )

    try { nextDose = getNextIntakeTime(item,mealTimings) } catch (_: Exception) {  }

    Card(
        modifier = modifier
            .padding(top=10.dp, bottom = 10.dp)
        ,
        shape = MaterialTheme.shapes.extraLarge,
        onClick = { expandedState = !expandedState }

    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .weight(3f)
            ){
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = item.name,
                    style = MaterialTheme.typography.titleLarge
                )
                if(item.isActive){
                    Text(
                        modifier = Modifier.padding(start = 16.dp, bottom = 5.dp),
                        text = "Ongoing Prescription - ${ ChronoUnit.DAYS.between( LocalDate.now(), LocalDate.parse(item.prescriptionEnd) ) } left",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        modifier = Modifier.padding(start = 16.dp),
                        text = "Next dose in ${nextDose} hours",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Text(
                    modifier = Modifier.padding(start = 16.dp),
                    text = "Expires on ${ LocalDate.parse(item.expiry).format(dateFormatter) }",
                    style = MaterialTheme.typography.bodyMedium
                )

            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                if(item.isActive && medicineTaken.value){
                    Icon(
                        painter = painterResource(R.drawable.check_square_icon),
                        contentDescription = "Taken",
                        tint = if(isSystemInDarkTheme()) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(75.dp)
                    )
                } else if(item.isActive && !medicineTaken.value){
                    Icon(
                        painter = painterResource(R.drawable.exclamationmark_square),
                        contentDescription = "Not taken",
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(75.dp)
                    )
                } else {
                    Column(
                        verticalArrangement = Arrangement.Center
                    ){
                        Icon(
                            painter = painterResource(R.drawable.pause_icon),
                            contentDescription = "Inactive",
                            tint = if(isSystemInDarkTheme()) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(75.dp)
                        )
                        Text(
                            text ="INACTIVE",
                            modifier = Modifier.padding(top = 5.dp),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                }

            }
        }

        if(item.isActive){
            MedicineTrackerUIComponent(
                modifier = Modifier,
                medicineItem = item,
                BREAKFAST_TIME = BREAKFAST_TIME,
                LUNCH_TIME = LUNCH_TIME,
                DINNER_TIME = DINNER_TIME,
                medicineTaken = medicineTaken,
                viewModel = viewModel
            )
        }
    }
}



