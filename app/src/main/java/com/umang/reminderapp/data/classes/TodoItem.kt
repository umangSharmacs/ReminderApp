package com.umang.reminderapp.data.classes

import java.time.LocalDate
import java.util.Date

data class TodoItem(
        var id : Int,
        var title: String,
        var createdOn : Date,
        var dueDate : LocalDate,
        var tags : List<String>,
        var description  : String
)
