package com.umang.reminderapp.alarm

import com.umang.reminderapp.data.classes.SubscriptionItem
import com.umang.reminderapp.data.classes.TodoItem
import java.time.LocalDate

interface AlarmScheduler {

    fun scheduleAlarm(item:TodoItem)

    fun cancelAlarm(item:TodoItem, hashcode: Int)

    fun cancelAllAlarms(item:TodoItem)

    fun scheduleSubscriptionAlarm(subscriptionItem: SubscriptionItem, alarmsList: List<LocalDate>)

    fun cancelAllSubscriptionAlarms(subscriptionItem: SubscriptionItem, alarmsList: List<LocalDate>)

}

