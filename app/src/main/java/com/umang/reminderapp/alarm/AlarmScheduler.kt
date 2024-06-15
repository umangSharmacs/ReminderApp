package com.umang.reminderapp.alarm

import com.umang.reminderapp.data.classes.TodoItem

interface AlarmScheduler {

    fun scheduleAlarm(item:TodoItem)

    fun cancelAlarm(item:TodoItem)
}

