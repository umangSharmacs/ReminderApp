package com.umang.reminderapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.umang.reminderapp.ui.theme.ReminderAppTheme

@Composable
fun DurationUnitBox(
    modifier: Modifier = Modifier,
    unit: String,
    initialValue: String,
    onValueChange: (String) -> Unit
) {

    var durationUnitValue by remember { mutableStateOf(initialValue) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {

        OutlinedTextField(
//            modifier = Modifier.padding(1.dp),
            value = durationUnitValue,
            onValueChange = {
                durationUnitValue = it
                onValueChange(it)
            },
//            label = {Text("-")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Text(unit)

    }

}

@Preview
@Composable
fun DurationUnitBoxPreview() {
    ReminderAppTheme {
        DurationUnitBox(Modifier,"Days","0") {}
    }
}