package com.umang.reminderapp.ui.drawings

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.umang.reminderapp.ui.theme.ReminderAppTheme

@Composable
fun drawingTest(modifier: Modifier = Modifier) {

    Canvas(modifier = Modifier.fillMaxSize()) {
        val canvasQuadrantSize = size / 2F
        drawArc(
            startAngle = 0F,
            sweepAngle = 90F,
            useCenter = true,
            color = Color.Red,
            size = canvasQuadrantSize,
            alpha = 0.5f
        )
    }
}

@Preview
@Composable
fun previewDrawingTest() {

    ReminderAppTheme {
        drawingTest()
    }
}