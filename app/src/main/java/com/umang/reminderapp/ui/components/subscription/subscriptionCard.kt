package com.umang.reminderapp.ui.components.subscription

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.umang.reminderapp.data.classes.SubscriptionItem
import com.umang.reminderapp.ui.theme.ReminderAppTheme


@Composable
fun subscriptionCard(modifier: Modifier = Modifier, subscriptionItem: SubscriptionItem) {

    Card(modifier = Modifier.fillMaxWidth().padding(10.dp)){

        Text(
            text = subscriptionItem.subscriptionName,
            modifier = Modifier.padding(5.dp),
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            text = "Next payment in N days",
            modifier = Modifier.padding(5.dp),
            style = MaterialTheme.typography.bodySmall,
        )
        Text(
            text = "Ends on " + subscriptionItem.endDate,
            modifier = Modifier.padding(5.dp),
            style = MaterialTheme.typography.bodySmall,
        )

    }

}

@Preview
@Composable
fun subscriptionCardPreview() {

    ReminderAppTheme {
        subscriptionCard(subscriptionItem = SubscriptionItem(System.currentTimeMillis().toInt()))
    }

}