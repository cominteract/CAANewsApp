package com.ainsigne.common.utils.extension

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.BulletSpan
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.util.Locale

/**
 * Format peso bill extension
 */
fun Float.formatBill(): String = String.format(Locale.getDefault(), "₱%.2f", this)

/**
 * Start and end index list for substring in a [String]
 */
fun String?.indexesOf(substr: String, ignoreCase: Boolean = true): List<Pair<Int, Int>> {
    val list = mutableListOf<Pair<Int, Int>>()
    if (this == null || substr.isBlank()) return list

    var i = -1
    var subEnd: Int
    while (true) {
        i = indexOf(substr, i + 1, ignoreCase)
        subEnd = i + substr.length
        when (i) {
            -1 -> return list
            else -> list.add(Pair(i, subEnd))
        }
    }
}

/**
 * Gets the current version to be compared in force update
 */
fun Context.getVersion(): Pair<String, Long> {
    val info = this.packageManager.getPackageInfo(this.packageName, 0)
    val version = info.versionName
    val versionCode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        info.longVersionCode
    } else {
        info.versionCode.toLong()
    }
    return Pair(version, versionCode)
}

/**
 * Displayed bill with decimal
 */
fun Long.getDisplayedBill(): String {
    return String.format(Locale.getDefault(), "₱%,.2f", this / 100f)
}

/**
 * [String] masking with custom replaced character
 * @param start [Int] start index of characters masked
 * @param end [Int] end index of characters masked
 * @param replaceWith [String] character to mask
 */
fun String.maskRange(start: Int, end: Int, replaceWith: String): String {
    val replaceString: String = replaceWith.repeat(end - start)
    if (length > 0) {
        return replaceRange(start, end, replaceString)
    }
    return this
}

/**
 * Trim commas in a [String]
 */
fun String.trimCommaOfString(): String {
    return if (this.contains(",")) {
        this.replace(",", "")
    } else {
        this
    }
}

/**
 * Trim decimals in [String]
 */
fun String.trimDecimalOfString(): String? {
    return if (this.contains(".")) {
        this.replace(".", "")
    } else {
        this
    }
}

fun Bitmap.toBitmapString(): String {
    val byteArrayOutputStream = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
    return Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT)
}

/**
 * @return bitmap (from given string)
 */
fun String.toBitmap(): Bitmap? {
    return try {
        val encodeByte =
            Base64.decode(this, Base64.DEFAULT)
        BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
    } catch (e: Exception) {
        e.message
        null
    }
}

/**
 * Display bulleted format for [List] [String]
 */
fun List<String>.bulleted(): CharSequence {
    var currentBulleted: CharSequence = ""
    for (publisher in this) {
        val spannable = SpannableString(publisher)
        spannable.setSpan(BulletSpan(15), 0, publisher.length, 0)
        currentBulleted = TextUtils.concat(spannable, currentBulleted)
    }
    return currentBulleted
}

// Empty utility for default empty value in string
const val EMPTY = ""
// New line utility for default newline value in string
const val NEWLINE = "\n"
