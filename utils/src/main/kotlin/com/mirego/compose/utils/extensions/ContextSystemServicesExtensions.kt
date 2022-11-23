package com.mirego.compose.utils.extensions

import android.content.Context
import android.hardware.SensorManager
import android.location.LocationManager
import android.os.Build
import android.os.Vibrator
import android.os.VibratorManager
import android.view.autofill.AutofillManager
import androidx.annotation.RequiresApi

val Context.locationManager: LocationManager
    get() = getSystemService(Context.LOCATION_SERVICE) as LocationManager

val Context.autofillManager: AutofillManager
    @RequiresApi(Build.VERSION_CODES.O)
    get() = getSystemService(AutofillManager::class.java)

val Context.sensorManager: SensorManager
    get() = getSystemService(Context.SENSOR_SERVICE) as SensorManager

val Context.vibrator: Vibrator
    get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        (getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager).defaultVibrator
    } else {
        @Suppress("DEPRECATION")
        (getSystemService(Context.VIBRATOR_SERVICE) as Vibrator)
    }
