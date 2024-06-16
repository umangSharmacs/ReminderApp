package com.umang.reminderapp.alarm

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.umang.reminderapp.data.classes.TodoItem
import java.time.LocalDateTime
import java.time.ZoneId

class AndroidAlarmSchedulerImpl(
    private val context: Context
) : AlarmScheduler {

    // Get reference to alarm Manager
    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    @SuppressLint("ScheduleExactAlarm", "MissingPermission")
    override fun scheduleAlarm(item: TodoItem) {

        Log.d("AlarmScheduler", "Scheduling alarm for ${item.title}")

        val intent = Intent(
            context,
            AlarmReceiver::class.java
        ).apply {
            putExtra("EXTRA_MESSAGE", item.title)
                .putExtra("EXTRA_DUEDATE", item.dueDate)
        }

        val reminders = item.reminders

        // Schedule the alarm for all reminders

        for (reminder in reminders){
            // Set alarm
            Log.d("AlarmScheduler", "Scheduling reminder for ${item.title} at ${LocalDateTime.parse(reminder).atZone(ZoneId.systemDefault()).toEpochSecond() * 1000}")
            alarmManager.setExactAndAllowWhileIdle(

                AlarmManager.RTC_WAKEUP,
                LocalDateTime.parse(reminder).atZone(ZoneId.systemDefault()).toEpochSecond() * 1000,
                PendingIntent.getBroadcast(
                    context,
                    item.hashCode()+reminder.hashCode(),
                    intent,
                    PendingIntent.FLAG_IMMUTABLE
                )
            )
        }


        // Due Date Alarm
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            LocalDateTime.parse(item.dueDate).atZone(ZoneId.systemDefault()).toEpochSecond() * 1000,
            PendingIntent.getBroadcast(
                context,
                item.hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE

            )
        )


    }

    override fun cancelAlarm(item: TodoItem) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                item.hashCode(),
                Intent(context, AlarmReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE

            )

        )
    }
}