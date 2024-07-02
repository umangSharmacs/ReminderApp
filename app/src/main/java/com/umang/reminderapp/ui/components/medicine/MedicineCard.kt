package com.umang.reminderapp.ui.components.medicine

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.umang.reminderapp.R
import com.umang.reminderapp.data.classes.MedicineIntakeTime
import com.umang.reminderapp.data.classes.MedicineItem
import com.umang.reminderapp.data.classes.MedicineMealType
import com.umang.reminderapp.data.models.MedicineViewModel
import com.umang.reminderapp.ui.theme.ReminderAppTheme
import java.time.LocalTime

@Composable
fun MedicineCard(
    modifier: Modifier = Modifier,
    item: MedicineItem,
    viewModel: MedicineViewModel
) {

    val BREAKFAST_TIME = LocalTime.of(9, 0)
    val LUNCH_TIME = LocalTime.of(13, 0)
    val DINNER_TIME = LocalTime.of(21, 0)

    var medicineTaken = remember {
        mutableStateOf(true)
    }

    Card(
        modifier = Modifier.padding(top=10.dp, bottom = 10.dp),
        shape = MaterialTheme.shapes.extraLarge

    ){
        Row(
            modifier = modifier
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
                Text(
                    modifier = Modifier.padding(start = 16.dp, bottom = 5.dp),
                    text = "Ongoing Prescription - ${item.duration} left",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    modifier = Modifier.padding(start = 16.dp),
                    text = "Next dose in",
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
                if(medicineTaken.value){
                    Icon(
                        painter = painterResource(R.drawable.check_square_icon),
                        contentDescription = "Taken",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(75.dp)
                    )
                } else {
                    Icon(
                        painter = painterResource(R.drawable.exclamationmark_square),
                        contentDescription = "Not taken",
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(75.dp)
                    )
                }


            }
        }

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


//@Preview
//@Composable
//fun MedicineCardPreview() {
//
//    val item = MedicineItem()
//
//    item.whenToTake[MedicineMealType.BREAKFAST.value] = MedicineIntakeTime.BEFORE.value
//    item.whenToTake[MedicineMealType.LUNCH.value] = MedicineIntakeTime.AFTER.value
//    item.whenToTake[MedicineMealType.DINNER.value] = MedicineIntakeTime.AFTER.value
//
//    ReminderAppTheme {
//        MedicineCard(item = item)
//    }
//}