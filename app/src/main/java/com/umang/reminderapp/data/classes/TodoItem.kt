package com.umang.reminderapp.data.classes

import java.time.LocalDate


data class TodoItem(
        var id : Int,
        var title: String,
        var createdOn : String,
        var dueDate : String,
        var tags : List<String>,
        var description  : String
){
        constructor(): this(
                id = 0,
                title = "",
                createdOn = "1900-01-01",
                dueDate = "1900-01-01",
                tags = emptyList(),
                description = ""
        )
}
