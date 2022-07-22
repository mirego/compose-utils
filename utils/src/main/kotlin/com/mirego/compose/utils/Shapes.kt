package com.mirego.compose.utils

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun RoundedCornerShapeTop(
    top: Dp,
) = RoundedCornerShape(
    topStart = top,
    topEnd = top,
    bottomEnd = 0.dp,
    bottomStart = 0.dp
)

fun RoundedCornerShapeBottom(
    bottom: Dp,
) = RoundedCornerShape(
    topStart = 0.dp,
    topEnd = 0.dp,
    bottomEnd = bottom,
    bottomStart = bottom
)
