package com.umang.reminderapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import com.umang.reminderapp.alarm.AndroidAlarmSchedulerImpl
import com.umang.reminderapp.data.classes.TodoItem
import java.time.LocalDateTime


@Composable
fun AlarmPage(
    modifier: Modifier = Modifier,
    scheduler: AndroidAlarmSchedulerImpl,
) {
    var secondsText by remember{
        mutableStateOf("")
    }
    var message by remember{
        mutableStateOf("")
    }
    val todoItem = TodoItem()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ){
        OutlinedTextField(
            value = secondsText,
            onValueChange = { secondsText = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {Text("Alarm Trigger in seconds")}
        )
        OutlinedTextField(
            value = message,
            onValueChange = { message = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {Text("Message")}
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ){
            Button(onClick = {
                todoItem.dueDate = (LocalDateTime.now().plusSeconds(secondsText.toLong())).toString()
                todoItem.title = message
                todoItem.let(scheduler::scheduleAlarm)
                secondsText = ""
                message = ""
            }) {
                Text(text = "Set Alarm")
            }
            Button(onClick = { scheduler.cancelAllAlarms(todoItem) }) {
                Text(text = "Cancel Alarm")
            }
        }
    }
}