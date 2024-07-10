package com.umang.reminderapp.ui.components.medicine

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.umang.reminderapp.alarm.AndroidAlarmSchedulerImpl
import com.umang.reminderapp.data.models.MedicineViewModel
import com.umang.reminderapp.ui.components.swiping.BehindMotionSwipe


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
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
                state = rememberLazyListState(),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally)
                    .padding(start = 15.dp, end = 15.dp)
                    .animateContentSize(),
                contentPadding = PaddingValues(top = 15.dp, bottom = 15.dp)
            ){
                if(it!=null){
                    itemsIndexed(
                        items = it.toList(),
                        key = { index, item -> item.id }
                    ){index, item ->

                        BehindMotionSwipe(
                            content = {
                                MedicineCard(
                                    modifier = Modifier.animateItemPlacement(),
                                    item = item,
                                    viewModel = medicineViewModel
                                )
                            },
                            onEdit = { navController.navigate("MedicineEditorScreen?id=${item.id}") },
                            onDelete = { medicineViewModel.deleteMedicineItem(item.id) }
                        )
                    }
                }
            }
        }

        if(medicineList.isNullOrEmpty()){
            Text("No Medicines")
        }
    }
}