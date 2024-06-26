package com.umang.reminderapp.ui.components

import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.umang.reminderapp.R

enum class MultiFloatingActionButtonState{
    Expanded,
    Collapsed
}

@Composable
fun FloatingActionButton(modifier: Modifier = Modifier,
                         onClick: () -> Unit) {

    var multiFloatingActionButtonState by remember {
        mutableStateOf(MultiFloatingActionButtonState.Collapsed)
    }

    val transition = updateTransition(targetState = multiFloatingActionButtonState, label = "transition")
    val rotate by transition.animateFloat(label = "rotate") {
        if(it == MultiFloatingActionButtonState.Expanded) 315f else 0f
    }


    FloatingActionButton(
        onClick = {
            if(multiFloatingActionButtonState == MultiFloatingActionButtonState.Collapsed) {
                multiFloatingActionButtonState = MultiFloatingActionButtonState.Expanded
            } else {
                multiFloatingActionButtonState = MultiFloatingActionButtonState.Collapsed
            }
         },
        modifier = modifier.rotate(rotate),
        containerColor = if(!isSystemInDarkTheme()) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.tertiary,
        contentColor = if(!isSystemInDarkTheme()) MaterialTheme.colorScheme.onTertiary else MaterialTheme.colorScheme.onTertiary

    ){
        Icon(Icons.Filled.Add, contentDescription = "Add")
    }
}


// Custom fab that allows for displaying extended content
@Composable
fun CustomFloatingActionButton(
    expandable: Boolean,
    onFabClick: () -> Unit,
    fabIcon: ImageVector,
    onSubscriptionClick: () -> Unit,
    onMedicineClick: () -> Unit,
    onTagClick: () -> Unit,
    onReminderClick: () -> Unit,
) {
    var isExpanded by remember { mutableStateOf(false) }
    if (!expandable) { // Close the expanded fab if you change to non expandable nav destination
        isExpanded = false
    }

    val fabSize = 64.dp
    val expandedFabWidth by animateDpAsState(
        targetValue = if (isExpanded) 250.dp else fabSize,
//        animationSpec = spring(dampingRatio = 3f)
    )
    val expandedFabHeight by animateDpAsState(
        targetValue = if (isExpanded) 58.dp else fabSize,
//        animationSpec = spring(dampingRatio = 3f)
    )

    val expandedBoxHeight by animateDpAsState(
        targetValue = if(!isExpanded) 0.dp else 225.dp,
//        animationSpec = spring(dampingRatio = 1f)
    )


    Column {

        // ExpandedBox over the FAB
        Box(
            modifier = Modifier
                .offset(y = (25).dp)
                .size(
                    width = expandedFabWidth,
                    height = expandedBoxHeight
                )
                .background(
                    MaterialTheme.colorScheme.tertiaryContainer,
                    shape = RoundedCornerShape(18.dp)
                )
                .align(Alignment.Start)
        ) {
            // Customize the content of the expanded box as needed
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                        .clickable { onSubscriptionClick() },
                    horizontalArrangement = Arrangement.Start,
                ){
                    Icon(
                        modifier = Modifier.padding(5.dp),
                        painter = painterResource(R.drawable.baseline_subscription_filled),
                        contentDescription = "Subscription",
                        tint = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                    Text("Add Subscription", color = MaterialTheme.colorScheme.onTertiaryContainer)
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                        .clickable { },
                    horizontalArrangement = Arrangement.Start,
                ){
                    Icon(
                        modifier = Modifier.padding(5.dp),
                        painter = painterResource(R.drawable.filled_medication),
                        contentDescription = "Medicine",
                        tint = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                    Text("Add Medicine (WIP)",color = MaterialTheme.colorScheme.onTertiaryContainer)
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                        .clickable {  },
                    horizontalArrangement = Arrangement.Start,
                ){
                    Icon(
                        modifier = Modifier.padding(5.dp),
                        painter = painterResource(R.drawable.baseline_subscription_filled),
                        contentDescription = "Subscription",
                        tint = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                    Text("Add Tag (WIP)", color = MaterialTheme.colorScheme.onTertiaryContainer)
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                        .clickable { onReminderClick() },
                    horizontalArrangement = Arrangement.Start,
                ){
                    Icon(
                        modifier = Modifier.padding(5.dp),
                        painter = painterResource(R.drawable.tag_filled),
                        contentDescription = "Reminder",
                        tint = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                    Text("Add Reminder", color = MaterialTheme.colorScheme.onTertiaryContainer)
                }
            }
        }

        FloatingActionButton(
            onClick = {
                onFabClick()
                if (expandable) {
                    isExpanded = !isExpanded
                }
            },
            modifier = Modifier
                .width(expandedFabWidth)
                .height(expandedFabHeight),
            shape = RoundedCornerShape(18.dp),
            containerColor = MaterialTheme.colorScheme.tertiary

        ) {

            Icon(
                imageVector = fabIcon,
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .offset(
                        x = animateDpAsState(
                            if (isExpanded) -80.dp else 0.dp,
                            animationSpec = spring(dampingRatio = 3f)
                        ).value
                    )
                    .rotate(
                        degrees = animateFloatAsState(
                            if (isExpanded) 315f else 0f,
                            animationSpec = spring(dampingRatio = 3f)
                        ).value
                    )
            )

//            Text(
//                text = "Create Reminder",
//                softWrap = false,
//                modifier = Modifier
//                    .offset(
//                        x = animateDpAsState(
//                            if (isExpanded) 10.dp else 50.dp,
//                            animationSpec = spring(dampingRatio = 3f)
//                        ).value
//                    )
//                    .alpha(
//                        animateFloatAsState(
//                            targetValue = if (isExpanded) 1f else 0f,
//                            animationSpec = tween(
//                                durationMillis = if (isExpanded) 350 else 100,
//                                delayMillis = if (isExpanded) 100 else 0,
//                                easing = EaseIn
//                            )
//                        ).value
//                    )
//            )

        }
    }
}