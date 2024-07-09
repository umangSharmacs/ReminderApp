package com.umang.reminderapp.alarm

import com.umang.reminderapp.data.classes.MedicineItem
import com.umang.reminderapp.data.classes.SubscriptionItem
import com.umang.reminderapp.data.classes.TodoItem
import java.time.LocalDate
import java.time.LocalDateTime

interface AlarmScheduler {

    fun scheduleAlarm(item:TodoItem)

    fun cancelAlarm(item:TodoItem, hashcode: Int)

    fun cancelAllAlarms(item:TodoItem)

    fun scheduleSubscriptionAlarm(subscriptionItem: SubscriptionItem, alarmsList: List<LocalDate>)

    fun cancelAllSubscriptionAlarms(subscriptionItem: SubscriptionItem, alarmsList: List<LocalDate>)

    fun scheduleMedicineAlarm(medicineItem: MedicineItem, alarmsList: List<LocalDateTime>)
    fun cancelAllMedicineAlarms(medicineItem: MedicineItem, alarmsList: List<LocalDateTime>)

}

