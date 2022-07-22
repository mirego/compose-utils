package com.mirego.compose.utils.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned

@Composable
fun Modifier.onShown(
    firstTimeOnly: Boolean = true,
    onShown: () -> Unit,
): Modifier {
    return composed {
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
}
