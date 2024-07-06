package com.umang.reminderapp.ui.components.subscription

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AssistChip
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.umang.reminderapp.data.classes.BillingPeriod
import com.umang.reminderapp.data.classes.SubscriptionItem
import com.umang.reminderapp.ui.theme.ReminderAppTheme
import com.umang.reminderapp.util.getNextBillingAlarmInDays
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.exp


@Composable
fun subscriptionCard(
    modifier: Modifier = Modifier,
    subscriptionItem: SubscriptionItem,
) {

    val dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy")

    val Ndays = getNextBillingAlarmInDays(subscriptionItem)

    var expandedState by remember {
        mutableStateOf(false)
    }


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        onClick = { expandedState = !expandedState },
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {

        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(3f)
            ) {
                Text(
                    text = subscriptionItem.subscriptionName,
                    modifier = Modifier.padding(
                        top = 5.dp,
                        bottom = 5.dp,
                        end = 5.dp,
                        start = 10.dp
                    ),
                    style = MaterialTheme.typography.titleLarge,
                    overflow = TextOverflow.Ellipsis,
                )

                Text(
                    text = "Next payment in $Ndays days",
                    modifier = Modifier.padding(
                        top = 5.dp,
                        bottom = 5.dp,
                        end = 5.dp,
                        start = 10.dp
                    ),
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = "Ends on ${
                        LocalDate.parse(subscriptionItem.endDate).format(dateFormatter)
                    }",
                    modifier = Modifier.padding(
                        top = 5.dp,
                        bottom = 5.dp,
                        end = 5.dp,
                        start = 10.dp
                    ),
                    style = MaterialTheme.typography.bodyMedium,
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "₹ ${subscriptionItem.cost}",
                    style = MaterialTheme.typography.titleLarge
                )
            }

        }

        AnimatedVisibility(expandedState){
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
            ) {
                // Start Date
                Text(
                    modifier = Modifier.padding(10.dp),
                    text = "Start date - ${
                        LocalDate.parse(subscriptionItem.startDate).format(dateFormatter)
                    }",
                    style = MaterialTheme.typography.bodyMedium,
                )

                // Tags
                Row() {
                    // Tags row
                    LazyRow(
                        contentPadding = PaddingValues(
                            top = 10.dp,
                            end = 12.dp,
                            start = 10.dp
                        )
                    ) {
                        items(subscriptionItem.tags.size) { index ->
                            AssistChip(
                                modifier = Modifier.padding(2.dp),
                                onClick = { },
                                label = { Text(text = subscriptionItem.tags[index]) }
                            )
                        }
                    }
                }
            }
        }
    }
}





@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO)
@Composable
fun subscriptionCardPreview() {

    val item = SubscriptionItem()
    item.subscriptionName = "Testing"
    item.startDate = "2024-01-01"
    item.endDate = "2025-01-01"
    item.cost = 100.0

    item.tags = listOf("Tag1", "Tag2", "Tag3")

    ReminderAppTheme {
        subscriptionCard(
            subscriptionItem = item,
        )
    }

}