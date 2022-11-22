package com.mirego.compose.utils.extensions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import java.util.Calendar
import kotlin.time.Duration.Companion.seconds

fun Modifier.onShown(
    firstTimeOnly: Boolean = true,
    onShown: () -> Unit,
): Modifier = composed {
    var shownInvokeNeeded by remember { mutableStateOf(true) }
    onGloballyPositioned { coordinates ->
        val isVisible = !coordinates.boundsInWindow().isEmpty
        if (isVisible && shownInvokeNeeded) {
            if (firstTimeOnly) {
                shownInvokeNeeded = false
            }
            onShown()
        }
    }
}

fun Modifier.clickableMultiple(
    tapNeeded: Int = 5,
    maxDelay: Long = 10.seconds.inWholeMilliseconds,
    onClick: () -> Unit
): Modifier = composed {
    var count by remember { mutableStateOf(0) }
    val firstTapTime = remember { mutableStateOf(Calendar.getInstance().timeInMillis) }

    this.then(
        Modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null
        ) {
            val delaySinceFirstTap = Calendar.getInstance().timeInMillis - firstTapTime.value
            if (count == 0 || delaySinceFirstTap > maxDelay) {
                count = 0
                firstTapTime.value = Calendar.getInstance().timeInMillis
            }
            count += 1
            if (count >= tapNeeded && (delaySinceFirstTap < maxDelay)) {
                onClick()
                count = 0
            }
        }
    )
}

fun Modifier.clickable(onClick: (() -> Unit)?): Modifier =
    run {
        onClick?.let {
            clickable(onClick = it)
        } ?: this
    }

fun Modifier.swallowClicks() =
    composed {
        clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null
        ) {}
    }
