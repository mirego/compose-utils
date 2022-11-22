package com.mirego.compose.utils.extensions

import android.app.Activity
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal

val ProvidableCompositionLocal<Context>.currentActivity
    @Composable get() = current as? Activity
