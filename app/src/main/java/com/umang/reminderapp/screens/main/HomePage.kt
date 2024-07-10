package com.umang.reminderapp.screens.main

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.umang.reminderapp.alarm.AndroidAlarmSchedulerImpl
import com.umang.reminderapp.data.classes.NavigationItem
import com.umang.reminderapp.data.models.AuthViewModel
import com.umang.reminderapp.data.models.TagViewModel
import com.umang.reminderapp.data.models.TodoViewModel
import com.umang.reminderapp.ui.components.CustomFloatingActionButton
import com.umang.reminderapp.ui.components.MinimalToDoList
import com.umang.reminderapp.ui.components.tags.TagGrid
import com.umang.reminderapp.ui.components.TopAppBarScaffold
import com.umang.reminderapp.ui.components.homePageComponents.TodayItemList
import com.umang.reminderapp.util.filterReminders
import com.umang.reminderapp.ui.components.NavDrawerContent
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime

@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    todoViewModel: TodoViewModel,
    tagViewModel: TagViewModel,
    navController: NavHostController,
    scheduler: AndroidAlarmSchedulerImpl,
    authViewModel: AuthViewModel
) {

    // Nav Drawer Items
    val navigationItems = listOf(
        NavigationItem.Home,
        NavigationItem.AllReminders,
        NavigationItem.Subscriptions,
        NavigationItem.Medicines,
        NavigationItem.Profile
    )

    // Nav Drawer State
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedIndex by rememberSaveable {
        mutableIntStateOf(navigationItems.indexOf(navigationItems.find {it == NavigationItem.Home}))
    }
    ModalNavigationDrawer(
        drawerContent = { NavDrawerContent(navigationItems, selectedIndex, navController, scope, drawerState) },
        drawerState = drawerState,
    ) {
        Scaffold(
            topBar = @Composable {
                TopAppBarScaffold(
                    header = "Memento",
                    navigateIcon = {

                        IconButton(
                            onClick = { scope.launch { drawerState.open() } }
                        ) {
                            Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                        }

                    }
                )
            },
            floatingActionButton = {
//            FloatingActionButton(onClick = { navController.navigate("AdderScreen") } )
                CustomFloatingActionButton(
                    expandable = true,
                    onFabClick = { /*TODO*/ },
                    fabIcon = Icons.Filled.Add,
                    onSubscriptionClick = { navController.navigate("SubscriptionAdderScreen") },
                    onTagClick = { },
                    onMedicineClick = { navController.navigate("MedicineAdderScreen") },
                    onReminderClick = {navController.navigate("AdderScreen")}
                )
            },
//            bottomBar = {
//                BottomBarScaffold(
//                    navHost = navController
//                )
//            }
        ) {
                innerPadding ->

            val todoList by todoViewModel.todoList.observeAsState()
            // Filter for this month's items
            val thisMonthsItems = todoList?.filter { LocalDateTime.parse(it.dueDate).month == LocalDateTime.now().month }

            val tags by tagViewModel.tagList.observeAsState()
            val tagsList = tags?.toMutableList()

            Column(
                modifier = Modifier
                    .padding(
                        top = innerPadding.calculateTopPadding(),
                        bottom = innerPadding.calculateBottomPadding()
                    )
                    .verticalScroll(rememberScrollState())
            ){
                // Today's Due items and Reminders
                var dueTodayList = todoList?.filter { LocalDateTime.parse(it.dueDate).toLocalDate() == LocalDate.now() }
                // convert dueTodayList to mutable
                dueTodayList = dueTodayList?.toMutableStateList()

                val remindersTodayList = todoList?.let { filterReminders(it) }
                Log.d("Reminders",remindersTodayList.toString())
                if (remindersTodayList != null) {

                    dueTodayList?.addAll(remindersTodayList)
                    Log.d("Today's Items-",dueTodayList.toString())
                }

                if (dueTodayList != null && remindersTodayList != null) {
                    TodayItemList(Modifier,  dueTodayList, navController)
                }

                if (dueTodayList != null && remindersTodayList != null) {
                    if(dueTodayList.isEmpty() && remindersTodayList.isEmpty()){
                        Text(
                            text = "Relax. No reminders or deadlines today.",
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.padding( 15.dp ),
                            textAlign = TextAlign.Center
                        )
                    }
                }

                // Divider
                HorizontalDivider(
                    thickness = 2.dp,
                    modifier = Modifier.padding(
                        start=10.dp, end = 10.dp
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )

                // This months Due Dates
                Column(
                    modifier = Modifier.padding(
                        start = 15.dp,
                        end = 15.dp
                    )
                ) {
                    Column(modifier = Modifier.height(200.dp)){
                        Text(
                            text = "This month's due items",
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.padding(
                                top = 5.dp,
                                start = 15.dp
                            )
                        )
                        thisMonthsItems?.let {
                            MinimalToDoList(
                                modifier = Modifier,
                                viewModel = todoViewModel,
                                navHost = navController,
                                scheduler = scheduler,
                                paddingValues = PaddingValues(0.dp,0.dp,0.dp,0.dp),
                                authViewModel = authViewModel,
                                items = it
                            )
                        }
                    }
                }

                // Divider
                HorizontalDivider(
                    thickness = 2.dp,
                    modifier = Modifier.padding(
                        start=10.dp, end = 10.dp
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )

                // Your Tags

//            if (tagsList != null) {
//                while(tagsList.size < 9) tagsList.add("")
//            }

                Column(
                    modifier = Modifier.padding(
                        start = 15.dp,
                        end = 15.dp
                    )
//                    .height(100.dp)
                ) {
                    Text(
                        text = "Your Tags",
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(
                            top = 5.dp,
                            start = 15.dp
                        )
                    )
                    tagsList?.let { TagGrid(Modifier, it) }
                }

                // Divider
                HorizontalDivider(
                    thickness = 2.dp,
                    modifier = Modifier.padding(
                        start=10.dp, end = 10.dp
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )

            }
        }
    }



}
