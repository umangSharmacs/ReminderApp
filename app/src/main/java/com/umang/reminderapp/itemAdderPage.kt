package com.umang.reminderapp

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import com.umang.reminderapp.R
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdderScreenContent(
    modifier: Modifier = Modifier,
    todoViewModel: TodoViewModel,
    navController: NavHostController,
    optionalTitle: String = "",
    optionalDescription: String = "",
    paddingValues: PaddingValues = PaddingValues(0.dp,0.dp)) {

    var titleInputText by remember {
        mutableStateOf(optionalTitle)
    }

    var descriptionInputText by remember {
        mutableStateOf(optionalDescription)
    }

    var selectedDueDate by remember {
        mutableStateOf(LocalDate.now())
    }

    var dueDateSelectedCounter by remember {
        mutableIntStateOf(0)
    }

    val calendarState = rememberSheetState()
    
    CalendarDialog(
        state = calendarState,
        config = CalendarConfig(
            monthSelection = true,
            yearSelection = true
        ),
        selection = CalendarSelection.Date { date ->
            Log.d("DATE", date.toString())
            selectedDueDate = date
            dueDateSelectedCounter+=1
        }
    )

    Surface( modifier = Modifier
        .background(color = Color.White)
        .padding(top = paddingValues.calculateTopPadding())
    ){
        Column (
            modifier = Modifier
                .fillMaxWidth()

        ) {
            // TITLE
            OutlinedTextField(
                modifier = Modifier
                    .padding(top = 12.dp, start = 12.dp)
                    .weight(2f),
                value = titleInputText,
                onValueChange = { titleInputText = it },
//                label = { Text(text = "Add a Title") },
                placeholder = {
                    Text(
                        "Title",
                        fontSize = 25.sp
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                ),
                textStyle = TextStyle.Default.copy(fontSize = 25.sp)
            )

            Divider(modifier = Modifier
                .height(2.dp)
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp),
                color = Color.Gray,
                thickness = 2.dp
            )

            // PROPERTIES
            //1.  DUE DATE
            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(start = 12.dp)
                    .weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(text = "Due Date", modifier = Modifier.weight(1f))
                TextButton(onClick = { calendarState.show() },
                    modifier = Modifier
                        .weight(1f)) {
                    Text(
                        text = if(dueDateSelectedCounter==0) "Enter a Date"
                        else selectedDueDate.toString(),
                    )
                }
            }
            Divider(modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp),
                color = Color.Gray,
                thickness = 1.dp
            )
            // 2. TAGS (Placeholders)
            Row(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(text = "Tags", modifier = Modifier.weight(1f))
                TextButton(onClick = { /*TODO*/ },
                    modifier = Modifier
                        .weight(1f)) {
                    Text(text = "Tags")
                }
            }
            Divider(modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp),
                color = Color.Gray,
                thickness = 1.dp
            )
            // 3. REMINDERS (Placeholders)
            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .padding(start = 12.dp)
                    .weight(1f),

                verticalAlignment = Alignment.CenterVertically,){
                Text(text = "Reminders", modifier = Modifier.weight(1f))
                TextButton(onClick = { /*TODO*/ },
                    modifier = Modifier
                        .weight(1f)) {
                    Text(text = "Reminders")
                }
            }

            Divider(modifier = Modifier
                .height(2.dp)
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp),
                color = Color.Gray,
                thickness = 2.dp
            )

            // DESCRIPTION
            OutlinedTextField(
                modifier = Modifier
                    .weight(5f),
                value = descriptionInputText,
                onValueChange = { descriptionInputText = it },
                placeholder = {
                    Text(
                        "Description..."
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
            )

            )

            // Add button

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
            ){

                ElevatedButton(
                    onClick =  {
                        todoViewModel.addTodoItem(
                            title = titleInputText,
                            description =  descriptionInputText,
                            dueDate = selectedDueDate)
                        navController.popBackStack()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.Primary))
                ) {
                    Text(text = "Add",
                        color = Color.Black,
                    )

                }

            }
        }

    }

}

@Composable
fun AdderScreen(modifier: Modifier = Modifier,
                todoViewModel: TodoViewModel,
                navController: NavHostController,
                optionalTitle: String = "",
                optionalDescription: String = "") {

    Scaffold(
        topBar = @androidx.compose.runtime.Composable {
            TopAppBarScaffold()
        })
    {paddingValues ->
            AdderScreenContent(
                modifier,
            todoViewModel,
            navController,
            optionalTitle,
            optionalDescription,
                paddingValues)
        }

}

@Preview(widthDp = 360, heightDp = 640)
@Composable
fun AdderScreenPreview() {
    AdderScreenContent(
        Modifier,
        TodoViewModel(),
        NavHostController(LocalContext.current)
    )
}