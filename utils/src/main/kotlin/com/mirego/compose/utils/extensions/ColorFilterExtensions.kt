package com.mirego.compose.utils.extensions

import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix

fun ColorFilter.Companion.blackAndWhite(): ColorFilter {
    val blackWhiteMatrix = ColorMatrix()
    blackWhiteMatrix.setToSaturation(0F)
    return colorMatrix(blackWhiteMatrix)
}
