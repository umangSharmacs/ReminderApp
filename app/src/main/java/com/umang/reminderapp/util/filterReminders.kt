package com.umang.reminderapp.util

import com.umang.reminderapp.data.classes.TodoItem
import java.time.LocalDate
import java.time.LocalDateTime

fun filterReminders(items: List<TodoItem>): List<TodoItem> {

    val toReturnList = mutableListOf<TodoItem>()

    for (item in items) {
        if(LocalDateTime.parse(item.dueDate).toLocalDate() != LocalDate.now()){
            for( reminder in item.reminders){
                if(LocalDateTime.parse(reminder).toLocalDate() == LocalDate.now()){
                    toReturnList.add(item)
                    break
                }
            }
        }
    }
    return toReturnList
}