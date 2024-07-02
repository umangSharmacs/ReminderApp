package com.umang.reminderapp.ui.components.medicine

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.umang.reminderapp.alarm.AndroidAlarmSchedulerImpl
import com.umang.reminderapp.data.classes.MedicineIntakeTime
import com.umang.reminderapp.data.classes.MedicineItem
import com.umang.reminderapp.data.classes.MedicineMealType
import com.umang.reminderapp.data.classes.SubscriptionItem
import com.umang.reminderapp.data.models.MedicineViewModel
import com.umang.reminderapp.singletons.MedicineManager
import java.time.LocalDate
import kotlin.time.Duration.Companion.days


@Composable
fun MedicineList(
    modifier: Modifier = Modifier,
    medicineViewModel: MedicineViewModel,
    scheduler: AndroidAlarmSchedulerImpl,
    navController: NavHostController
) {


    val medicineList by medicineViewModel.medicineList.observeAsState()


    Column(modifier = modifier
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        medicineList.let {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally)
                    .padding(start = 15.dp, end = 15.dp),
                contentPadding = PaddingValues(top = 15.dp, bottom = 15.dp)
            ){
                if(it!=null){
                    itemsIndexed(it.toList()){index, item ->
                        MedicineCard(item = item, viewModel = medicineViewModel)
                    }
                }
            }
        }

        if(medicineList.isNullOrEmpty()){
            Text("No Medicines")
        }
    }


}