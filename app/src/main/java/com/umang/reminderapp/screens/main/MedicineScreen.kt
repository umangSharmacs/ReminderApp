package com.umang.reminderapp.screens.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.umang.reminderapp.alarm.AndroidAlarmSchedulerImpl
import com.umang.reminderapp.data.models.MedicineViewModel
import com.umang.reminderapp.ui.components.BottomBarScaffold
import com.umang.reminderapp.ui.components.CustomFloatingActionButton
import com.umang.reminderapp.ui.components.TopAppBarScaffold
import com.umang.reminderapp.ui.components.medicine.MedicineList


@Composable
fun MedicineScreen(
    modifier: Modifier = Modifier,
    medicineViewModel: MedicineViewModel,
    scheduler: AndroidAlarmSchedulerImpl,
    navController: NavHostController
) {

    Scaffold(
        topBar = @Composable {
            TopAppBarScaffold(
                navigateBack = {navController.popBackStack()}
            )
        },
        floatingActionButton = {
//            FloatingActionButton(onClick = { navController.navigate("AdderScreen") })
            CustomFloatingActionButton(
                expandable = true,
                onFabClick = { /*TODO*/ },
                fabIcon = Icons.Filled.Add,
                onSubscriptionClick = { navController.navigate("SubscriptionAdderScreen") },
                onTagClick = { },
                onMedicineClick = { },
                onReminderClick = {navController.navigate("AdderScreen")}
            )
        },
        bottomBar = {
            BottomBarScaffold(
                navHost = navController
            )
        }
    ) { innerPadding ->
        MedicineList(
            modifier = modifier.padding(innerPadding),
            medicineViewModel = medicineViewModel,
            scheduler = scheduler,
            navController = navController
        )
    }


}