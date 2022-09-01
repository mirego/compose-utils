package com.mirego.compose.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

/**
 * Lets you get values that uses breakpoints on screen width or height
 *
 * val margin = sizeWithWidthThresholds(
 *    1000.dp to 128.dp,
 *    600.dp to 64.dp,
 *    0.dp to 12.dp
 * )
 */

@Composable
fun sizeWithHeightThresholds(vararg thresholds: Pair<Dp, Dp>): Dp = valueWithThresholds(LocalConfiguration.current.screenHeightDp, *thresholds)

@Composable
fun sizeWithWidthThresholds(vararg thresholds: Pair<Dp, Dp>): Dp = valueWithThresholds(LocalConfiguration.current.screenWidthDp, *thresholds)

@Composable
fun textStyleWithHeightThresholds(vararg thresholds: Pair<Dp, TextStyle>) = valueWithThresholds(LocalConfiguration.current.screenHeightDp, *thresholds)

@Composable
fun textStyleWithWidthThresholds(vararg thresholds: Pair<Dp, TextStyle>) = valueWithThresholds(LocalConfiguration.current.screenWidthDp, *thresholds)

@Composable
fun intWithHeightThresholds(vararg thresholds: Pair<Dp, Int>): Int = valueWithThresholds(LocalConfiguration.current.screenHeightDp, *thresholds)

@Composable
fun intWithWidthThresholds(vararg thresholds: Pair<Dp, Int>): Int = valueWithThresholds(LocalConfiguration.current.screenWidthDp, *thresholds)

private fun <T> valueWithThresholds(
    comparatorInDp: Int,
    vararg thresholds: Pair<Dp, T>
): T {
    thresholds.forEach {
        if (comparatorInDp > it.first.value) {
            return it.second
        }
    }
    throw IllegalStateException("Unmapped value $comparatorInDp")
}
