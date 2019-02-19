package com.valartech.commons.utils

import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.FragmentActivity

fun Float.spToPx(): Float {
    val scale = Resources.getSystem().displayMetrics.scaledDensity
    return this * scale
}

fun Float.dpToPx(): Float {
    return (this * Resources.getSystem().displayMetrics.density)
}

fun Int.dpToPx(): Int {
    return (this * Resources.getSystem().displayMetrics.density).toInt()
}

fun FragmentActivity.hideKeyboard() {
    if (currentFocus != null) {
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }
}

fun Resources.getDeviceHeightInPx(): Int {
    return displayMetrics.heightPixels
}

/**
 * https://stackoverflow.com/a/28965901/3460025
 */
fun Resources.getStatusBarHeight(): Int {
    val resourceId = getIdentifier("status_bar_height", "dimen", "android")
    return if (resourceId > 0)
        getDimensionPixelSize(resourceId)
    else
        Math.ceil(
            ((if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) 24 else 25)
                    * displayMetrics.density).toDouble()
        ).toInt()
}
