package com.umang.reminderapp.ui.components.subscription

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.ViewModelProvider
import com.umang.reminderapp.data.classes.BillingPeriod
import com.umang.reminderapp.data.models.SubscriptionViewModel
import com.umang.reminderapp.ui.theme.ReminderAppTheme

@Composable
fun SubscriptionCostCard(
    modifier: Modifier = Modifier,
    subscriptionViewModel: SubscriptionViewModel
) {

    var costPeriod by remember {
       mutableStateOf(BillingPeriod.MONTHLY)
    }

    var settingsDialogShow by remember {
        mutableStateOf(false)
    }

    val costPeriodString = when (costPeriod) {
        BillingPeriod.MONTHLY -> "monthly"
        BillingPeriod.YEARLY -> "yearly"
        BillingPeriod.WEEKLY -> "weekly"
        BillingPeriod.DAILY -> "daily"
        else -> "unspecified"
    }

    var cost = subscriptionViewModel.getSubscriptionsCost(costPeriod)
    Row(
        modifier = Modifier.padding(start = 30.dp, top=10.dp, bottom = 10.dp, end = 30.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ){
        Column(
            modifier = Modifier.weight(3f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text("Your approx. ${costPeriodString} subscription cost is")
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .weight(2f),
            shape = MaterialTheme.shapes.large,
        ){
            Column(){
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(35.dp)
                        .padding(2.dp),
                    horizontalArrangement = Arrangement.End
                ){
                    IconButton(onClick = { settingsDialogShow = true }) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings"
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, bottom = 10.dp)
                    ,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){

                    Column(
                        modifier = Modifier.weight(2f),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "₹${cost}",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }
        }
    }




    // Settings dialog
    if(settingsDialogShow){

        val optionsList = listOf(
            BillingPeriod.DAILY,
            BillingPeriod.WEEKLY,
            BillingPeriod.MONTHLY,
            BillingPeriod.YEARLY
        )

        Dialog(
            onDismissRequest = {settingsDialogShow = false}
        ){

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.large,
                color = MaterialTheme.colorScheme.background
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalAlignment = Alignment.Start
                ){

                    Text("Select a Period:")
                    Spacer(modifier = Modifier.padding(5.dp))

                    LazyColumn {
                        items(optionsList){option ->

                            Row(verticalAlignment = Alignment.CenterVertically){
                                RadioButton(
                                    modifier = Modifier.padding(5.dp),
                                    selected = costPeriod == option,
                                    onClick = {
                                        costPeriod = option
                                        cost = subscriptionViewModel.getSubscriptionsCost(costPeriod)
                                        settingsDialogShow = false
                                    }
                                )
                                Text(text = option.name)
                            }

                        }
                    }
                }
            }

        }

    }

}


@Preview
@Composable
fun SubscriptionCostCardPreview() {

    ReminderAppTheme {
//        SubscriptionCostCard(Modifier)
    }
}