package com.mirego.compose.utils.extensions

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable

/**
 * Add items to a Column, using the specified Composable as a divider between each items.
 *
 * Usage:
 * ```
 * Column {
 *   items(
 *     items = textList,
 *     dividedBy = {
 *        Divider(color = Color.Red)
 *     }
 *   ) { item ->
 *     Text(item)
 *   }
 * }
 * ```
  */
@SuppressLint("ComposableNaming")
@Composable
fun <T> ColumnScope.items(
    items: List<T>,
    dividedBy: @Composable ColumnScope.() -> Unit,
    content: @Composable ColumnScope.(T) -> Unit
) {
    items.forEachIndexed { index, item ->
        content(item)
        if (index != items.lastIndex) {
            dividedBy()
        }
    }
}
