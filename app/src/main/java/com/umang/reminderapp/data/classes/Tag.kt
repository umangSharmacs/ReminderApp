package com.umang.reminderapp.data.classes

import androidx.compose.ui.graphics.Color

data class Tag(
    var name: String,
    var backgroundColor: Color,
    var contentColor: Color
){
    constructor(): this(
        name = "Tag",
        backgroundColor = Color.White,
        contentColor = Color.Black
    )
}
