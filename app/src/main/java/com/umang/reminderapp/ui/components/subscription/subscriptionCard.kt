package com.umang.reminderapp.ui.components.subscription

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.umang.reminderapp.data.classes.BillingPeriod
import com.umang.reminderapp.data.classes.SubscriptionItem
import com.umang.reminderapp.ui.theme.ReminderAppTheme
import com.umang.reminderapp.util.getNextBillingAlarmInDays
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@Composable
fun subscriptionCard(
    modifier: Modifier = Modifier,
    subscriptionItem: SubscriptionItem,
    onClick: () -> Unit
) {

    val dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy")

    val Ndays = getNextBillingAlarmInDays(subscriptionItem)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        onClick = { onClick() },
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ){

        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Column(
                modifier = Modifier
                    .weight(3f)
            ){
                Text(
                    text = subscriptionItem.subscriptionName,
                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp, end = 5.dp, start = 10.dp),
                    style = MaterialTheme.typography.titleLarge,
                    overflow = TextOverflow.Ellipsis,
                )

                Text(
                    text = "Next payment in $Ndays days",
                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp, end = 5.dp, start = 10.dp),
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = "Ends on ${LocalDate.parse(subscriptionItem.endDate).format(dateFormatter)}",
                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp, end = 5.dp, start = 10.dp),
                    style = MaterialTheme.typography.bodyMedium,
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    text = "INR ${subscriptionItem.cost}",
                    style = MaterialTheme.typography.titleLarge
                )
            }

        }
    }

}

@Preview
@Composable
fun subscriptionCardPreview() {

    val item = SubscriptionItem()
    item.subscriptionName = "Testing"
    item.startDate = "2024-01-01"
    item.endDate = "2025-01-01"
    item.cost = 100.0

    ReminderAppTheme {
        subscriptionCard(
            subscriptionItem = item,
            onClick = {}
        )
    }

}