package com.umang.reminderapp.data.classes


data class TodoItem(
        var id: Int,
        var title: String,
        var createdOn: String,
        var dueDate: String,
        var tags: List<String>,
        var description: String,
        var completed: Boolean,
        var completedOn: String,
        var reminders: List<String>,
        var priority: Int
){
        constructor(): this(
                id = 0,
                title = "Todo",
                createdOn = "1900-01-01",
                dueDate = "1900-01-01",
                tags = emptyList(),
                description = "",
                completed = false,
                completedOn = "1900-01-01",
                reminders = emptyList(),
                priority = 3
        )
}

