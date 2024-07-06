package com.umang.reminderapp.ui.components.swiping

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.umang.reminderapp.ui.components.swiping.DeleteAction
import com.umang.reminderapp.ui.components.swiping.DragAnchors
import com.umang.reminderapp.ui.components.swiping.DraggableItem
import com.umang.reminderapp.ui.components.swiping.EditAction

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BehindMotionSwipe(
    content: @Composable BoxScope.() -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
    ) {

    val density = LocalDensity.current

    val defaultActionSize = 80.dp

    val endActionSizePx = with(density) { (defaultActionSize * 2).toPx() }
//    val startActionSizePx = with(density) { defaultActionSize.toPx() }

    val state = remember {
        AnchoredDraggableState(
            initialValue = DragAnchors.Center,
            anchors = DraggableAnchors {
//                DragAnchors.Start at -startActionSizePx
                DragAnchors.Center at 0f
                DragAnchors.End at endActionSizePx
            },
            positionalThreshold = { distance: Float -> distance * 0.5f },
            velocityThreshold = { with(density) { 100.dp.toPx() } },
            animationSpec = tween(),
        )
    }

    DraggableItem(
        state = state,
        content = content,
        endAction = {
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .align(Alignment.CenterEnd),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                EditAction(
                    modifier = Modifier
                        .width(defaultActionSize)
                        .fillMaxHeight(),
                    onClick = onEdit
                )
                DeleteAction(
                    Modifier
                        .width(defaultActionSize)
                        .fillMaxHeight(),
                    onClick = onDelete
                )
            }
        }
    )
}