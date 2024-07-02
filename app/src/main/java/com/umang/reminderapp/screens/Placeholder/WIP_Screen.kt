package com.umang.reminderapp.screens.Placeholder

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.umang.reminderapp.data.classes.MedicineIntakeTime
import com.umang.reminderapp.data.classes.MedicineItem
import com.umang.reminderapp.data.classes.MedicineMealType
import com.umang.reminderapp.ui.components.BottomBarScaffold
import com.umang.reminderapp.ui.components.FloatingActionButton
import com.umang.reminderapp.ui.components.TopAppBarScaffold
import com.umang.reminderapp.ui.components.medicine.MedicineCard

@Composable
fun ComingSoon(modifier: Modifier = Modifier,navController: NavHostController) {

    Scaffold(
        topBar = @Composable {
            TopAppBarScaffold(
                navigateBack = {navController.popBackStack()}
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("AdderScreen") })
        },
        bottomBar = {
            BottomBarScaffold(
                navHost = navController
            )
        }

    ) {innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = modifier.padding(innerPadding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "COMING SOON")

                val item = MedicineItem()
                item.whenToTake[MedicineMealType.BREAKFAST.value] = MedicineIntakeTime.BEFORE.value
                item.whenToTake[MedicineMealType.LUNCH.value] = MedicineIntakeTime.BEFORE.value
                item.whenToTake[MedicineMealType.DINNER.value] = MedicineIntakeTime.BEFORE.value
//                MedicineCard(item = item)




            }
        }
    }
}