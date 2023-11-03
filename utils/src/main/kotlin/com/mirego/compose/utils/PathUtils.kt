package com.mirego.compose.utils

import androidx.compose.ui.graphics.Path
import android.graphics.Matrix
import android.graphics.RectF
import androidx.compose.ui.graphics.asComposePath
import androidx.core.graphics.PathParser

object PathUtils {
    fun pathFromPathData(pathData: String, width: Float, height: Float): Path {
        val path = PathParser.createPathFromPathData(pathData)

        val rectF = RectF()
        path.computeBounds(rectF, true)

        val matrix = Matrix()
        val scale = minOf(width / rectF.width(), height / rectF.height())
        matrix.setScale(scale, scale)

        path.transform(matrix)

        return path.asComposePath()
    }
}
