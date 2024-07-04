package com.umang.reminderapp.ui.components.medicine

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedAssistChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.math.exp

@Composable
fun MedicineCard(
    modifier: Modifier = Modifier,
    item: MedicineItem,
    viewModel: MedicineViewModel
) {

    var expandedState by remember { mutableStateOf(false) }

    val BREAKFAST_TIME = LocalTime.of(9, 0)
    val LUNCH_TIME = LocalTime.of(13, 0)
    val DINNER_TIME = LocalTime.of(21, 0)

    var medicineTaken = rememberSaveable {
        mutableStateOf(true)
    }

    val dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy")

    Card(
        modifier = Modifier.padding(top=10.dp, bottom = 10.dp),
        shape = MaterialTheme.shapes.extraLarge,
        onClick = { expandedState = !expandedState }
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

        AnimatedVisibility(visible = expandedState) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                onClick = { expandedState = !expandedState },
                shape = MaterialTheme.shapes.large,
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 3.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
                    contentColor = MaterialTheme.colorScheme.onSurface,
                ),
            ){
                // Expiry Date
                Text(
                    modifier = Modifier.padding(10.dp),
                    text = "Expiry - ${LocalDate.parse(item.expiry).format(dateFormatter)}"
                )
                // Edit , Delete

                Row(modifier = modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ){
                    // EDIT
                    ElevatedAssistChip(
                        modifier = Modifier.padding(5.dp),
                        onClick = { } ,
                        leadingIcon = { Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit") },
                        label = { Text(text = "Edit") }
                    )
                    // DELETE
                    ElevatedAssistChip(
                        modifier = Modifier.padding(5.dp),
                        onClick = { } ,
                        leadingIcon = { Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete") },
                        label = { Text(text = "Delete") }
                    )
                }

                Text("hello World")
            }
        }

    }
}

