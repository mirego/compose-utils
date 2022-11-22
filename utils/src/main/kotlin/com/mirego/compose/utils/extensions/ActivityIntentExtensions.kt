package com.mirego.compose.utils.extensions

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi

fun Activity.startEmailActivity(
    emailRecipient: String,
    emailSubject: String,
    intentChooserTitle: String = emailSubject,
    onException: ((ActivityNotFoundException) -> Unit)? = null
) = safeStartActivity(
    intent = Intent.createChooser(
        Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:")).apply {
            putExtra(Intent.EXTRA_SUBJECT, emailSubject)
            putExtra(Intent.EXTRA_EMAIL, arrayOf(emailRecipient))
        },
        intentChooserTitle
    ),
    onException = onException
)

fun Activity.startPhoneActivity(
    phoneNumber: String,
    onException: ((ActivityNotFoundException) -> Unit)? = null
) = safeStartActivity(
    intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber")),
    onException = onException
)

fun Activity.startMapActivity(
    latitude: Double,
    longitude: Double,
    onException: ((ActivityNotFoundException) -> Unit)? = null
) = safeStartActivity(
    intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:${latitude},${longitude}")),
    onException = onException
)

/**
 * Open the Settings app at a specific location
 *
 * @param settings: Use a value from `android.provider.Settings`
 */
@RequiresApi(Build.VERSION_CODES.O)
fun Activity.navigateToSettings(
    settings: String,
    onException: ((ActivityNotFoundException) -> Unit)? = null
) = safeStartActivity(
    intent = Intent(settings).apply {
        putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
    },
    onException = onException
)

private fun Activity.safeStartActivity(
    intent: Intent,
    onException: ((ActivityNotFoundException) -> Unit)? = null
) {
    try {
        startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        onException?.invoke(e)
    }
}
