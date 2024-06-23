package com.umang.reminderapp.ui.components.subscription

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.umang.reminderapp.data.classes.SubscriptionItem
import com.umang.reminderapp.ui.theme.ReminderAppTheme

@Composable
fun SubscriptionList(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    subscriptionItemList: List<SubscriptionItem>
) {


    Column(modifier = modifier
        .fillMaxWidth()
        .padding(top = paddingValues.calculateTopPadding()-15.dp, bottom = paddingValues.calculateBottomPadding()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        subscriptionItemList.let {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally)
                    .padding(start = 15.dp, end = 15.dp),
                contentPadding = PaddingValues(top = 15.dp, bottom = 15.dp)
            ) {
                itemsIndexed(it) { index: Int, item: SubscriptionItem ->

                    subscriptionCard(Modifier, item)
                }
            }
        }

        if(subscriptionItemList.isEmpty()){
            Text(text = "No items")
        }
    }
}

@Preview
@Composable
fun SubscriptionListPreview() {

    val itemList = listOf(
        SubscriptionItem(System.currentTimeMillis().toInt()),
        SubscriptionItem(System.currentTimeMillis().toInt()),
        SubscriptionItem(System.currentTimeMillis().toInt()),
        SubscriptionItem(System.currentTimeMillis().toInt()),
        SubscriptionItem(System.currentTimeMillis().toInt())
    )
    ReminderAppTheme {
        SubscriptionList(
            paddingValues = PaddingValues(15.dp),
            subscriptionItemList = itemList
        )
    }

}