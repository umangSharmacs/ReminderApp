package com.umang.reminderapp.ui.components.subscription

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.umang.reminderapp.alarm.AndroidAlarmSchedulerImpl
import com.umang.reminderapp.data.classes.SubscriptionItem
import com.umang.reminderapp.data.models.SubscriptionViewModel
import com.umang.reminderapp.ui.components.swiping.BehindMotionSwipe
import com.umang.reminderapp.ui.theme.ReminderAppTheme
import com.umang.reminderapp.util.getAlarms
import java.time.LocalDate

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SubscriptionList(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    navHost: NavHostController,
    subscriptionViewModel : SubscriptionViewModel,
    scheduler: AndroidAlarmSchedulerImpl
) {

    val subscriptionItemList by subscriptionViewModel.subscriptionList.observeAsState()

    Column(modifier = modifier
        .fillMaxWidth()
        .padding(
            top = paddingValues.calculateTopPadding(),
            bottom = paddingValues.calculateBottomPadding()
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Show subscriptions
        subscriptionItemList.let {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally)
                    .padding(start = 15.dp, end = 15.dp),
                contentPadding = PaddingValues(top = 15.dp, bottom = 15.dp)
            ) {

                if (it != null) {
                    itemsIndexed(
                        items = it.toList(),
                        key = { index, item -> item.id }
                    ) { index: Int, item: SubscriptionItem ->

                        BehindMotionSwipe(
                            content = {
                                subscriptionCard(
                                    modifier = Modifier.animateItemPlacement(),
                                    subscriptionItem = item
                                )
                            },
                            onEdit = { navHost.navigate("SubscriptionEditorScreen?id=${item.id}") },
                            onDelete = {
                                // Delete alarms
                                val alarmsList = getAlarms(
                                    LocalDate.parse(item.startDate),
                                    LocalDate.parse(item.endDate),
                                    item.billingPeriod
                                )
                                scheduler.cancelAllSubscriptionAlarms(item, alarmsList)
                                // Delete items
                                subscriptionViewModel.deleteSubscriptionItem(item.id)
                            }
                        )


                    }
                }
            }
        }

        if(subscriptionItemList?.isEmpty() == true){
            Text(text = "No subscriptions")
        }
    }
}
