package com.umang.reminderapp.ui.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.umang.reminderapp.data.classes.NavigationItem
import com.umang.reminderapp.ui.theme.Shapes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun NavDrawerContent(
    navigationItems: List<NavigationItem>,
    currentIndex: Int,
    navController: NavHostController,
    scope: CoroutineScope,
    drawerState: DrawerState
) {
    var selectedIndex by rememberSaveable {
        mutableStateOf(currentIndex)
    }

    var isClicked by remember {
        mutableStateOf(false)
    }

    ModalDrawerSheet(
        modifier = Modifier
            .width(300.dp),
        drawerShape = Shapes.extraLarge,
    ) {

        Text(
            text = "Memento",
            modifier = Modifier
                .padding(16.dp)
                .drawBehind {
                            //drawCircle(Color.White, radius = 100f)
                    drawArc(
                        color = Color.Red,
                        startAngle = 180f, // Start from the left
                        sweepAngle = 90f, // Sweep 90 degrees upwards
                        useCenter = false,
                        topLeft = Offset(x = size.width/4,y=size.height/2),
                        size = Size(size.width / 2, size.height / 2)
                    )
                    drawArc(
                        color = Color.Blue,
                        startAngle = 270f, // Start where the first arc ends
                        sweepAngle = 90f, // Sweep another 90 degrees upwards
                        useCenter = false,
                        topLeft = Offset(x = size.width / 4, y = size.height / 2),
                        size = Size(size.width / 2, size.height / 2)
                    )
                },
            style = MaterialTheme.typography.titleLarge,
            color = if(!isSystemInDarkTheme()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimary
        )
        Spacer(modifier = Modifier.padding(16.dp))
        navigationItems.forEachIndexed { index, item ->
            NavigationDrawerItem(
                label = { Text(item.title) },
                selected = index == selectedIndex,
                onClick = {
                    navController.navigate(item.navRoute)
                    selectedIndex = index
                    scope.launch { drawerState.close() }
                    },
                icon = {
                    Icon(
                        painter = if (selectedIndex == index) painterResource(id = item.selectedIcon) else painterResource(
                            item.unselectedIcon
                        ),
                        contentDescription = item.title
                    )
                },
                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
            )
        }

        HorizontalDivider(thickness = 2.dp, modifier = Modifier.padding(16.dp))



    }
}