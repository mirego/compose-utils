package com.mirego.compose.utils.extensions

import android.app.Activity
import android.content.Context
import android.view.WindowManager

var Context.screenBrightness: Float?
    get() = (this as? Activity)?.window?.attributes?.screenBrightness
    set(value) {
        (this as? Activity)?.window?.let { window ->
            val attributes = window.attributes
            attributes?.screenBrightness = value
            window.attributes = attributes
        }
    }

fun Context.setKeepScreenOn(keepScreenOn: Boolean) {
    (this as? Activity)?.window?.let { window ->
        if (keepScreenOn) {
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
    }
}
