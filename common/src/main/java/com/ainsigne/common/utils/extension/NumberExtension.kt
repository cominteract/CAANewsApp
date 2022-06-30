package com.ainsigne.common.utils.extension

import android.content.res.Resources
import android.util.DisplayMetrics
import android.util.TypedValue
import com.ainsigne.common.utils.TARGET_MINUTES
import timber.log.Timber
import java.util.Date

/**
 * Convert any number to its px value
 */
val Number.toPx get() = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    this.toFloat(),
    Resources.getSystem().displayMetrics
)

/**
 * Convert any number to its dp value
 */
val Number.toDp get() = (this.toFloat() / (Resources.getSystem().displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT))

fun getNow(): Long {
    return Date().time
}

fun Long.compareExceedingMinutes(targetExpiry: Long): Boolean {
    val minutes = (targetExpiry - this) / 1000 / 60
    Timber.d(" Exceeding Minutes $minutes")
    return minutes >= TARGET_MINUTES
}
