package com.mirego.compose.utils

import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import kotlin.math.absoluteValue

@Composable
fun AutoResizeText(
    text: String,
    fontSizeRange: FontSizeRange,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    style: TextStyle = LocalTextStyle.current,
    onValueComputed: (TextUnit) -> Unit = {}
) {
    var fontSizeValue by remember { mutableStateOf(fontSizeRange.max.value) }
    var readyToDraw by remember { mutableStateOf(false) }
    var smallestValue by remember { mutableStateOf(fontSizeRange.min.value) }
    var largestValue by remember { mutableStateOf(fontSizeRange.max.value) }

    Text(
        text = text,
        color = color,
        maxLines = maxLines,
        fontStyle = fontStyle,
        fontWeight = fontWeight,
        fontFamily = fontFamily,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = lineHeight,
        overflow = overflow,
        softWrap = softWrap,
        style = style,
        fontSize = fontSizeValue.sp,
        onTextLayout = {
            if (it.didOverflowHeight && !readyToDraw) {
                largestValue = fontSizeValue
                val nextFontSizeValue = arrayOf(smallestValue, largestValue).average().toFloat()
                if (nextFontSizeValue <= fontSizeRange.min.value) {
                    fontSizeValue = fontSizeRange.min.value
                    readyToDraw = true
                    onValueComputed(fontSizeValue.sp)
                } else {
                    fontSizeValue = nextFontSizeValue
                }
            } else {
                smallestValue = fontSizeValue
                val isCloseEnough = (smallestValue - largestValue).absoluteValue < fontSizeRange.tolerance.value
                if (!isCloseEnough) {
                    val nextFontSizeValue = arrayOf(smallestValue, largestValue).average().toFloat()
                    fontSizeValue = nextFontSizeValue
                } else {
                    readyToDraw = true
                    onValueComputed(fontSizeValue.sp)
                }
            }
        },
        modifier = modifier.drawWithContent { if (readyToDraw) drawContent() }
    )
}

data class FontSizeRange(
    val min: TextUnit,
    val max: TextUnit,
    val tolerance: TextUnit = DEFAULT_TOLERANCE,
) {
    init {
        require(min < max) { "min should be less than max, $this" }
        require(tolerance.value > 0) { "tolerance should be greater than 0, $this" }
    }

    val middle: Float
        get() = arrayOf(min.value, max.value).average().toFloat()

    companion object {
        private val DEFAULT_TOLERANCE = 1.sp
    }
}
