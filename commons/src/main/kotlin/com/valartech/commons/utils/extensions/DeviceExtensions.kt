package com.valartech.commons.utils.extensions

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.view.KeyCharacterMap
import android.view.KeyEvent
import android.view.ViewConfiguration
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

/**
 * Returns the notch height if one is present, or the status height, in pixels.
 */
fun Resources.getStatusBarOrNotchHeight(activity: Activity?): Int {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        val safeInsetTop =
            activity?.window?.decorView?.rootWindowInsets?.displayCutout?.safeInsetTop
        safeInsetTop?.let {
            return it
        } ?: getStatusBarHeight()
    } else getStatusBarHeight()
}

/**
 * Note that this doesn't work for emulators.
 *
 * https://stackoverflow.com/a/29938139/3460025
 */
fun Context.getNavBarHeight(): Int {
    val result = 0
    val hasMenuKey = ViewConfiguration.get(this).hasPermanentMenuKey()
    val hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK)

    if (!hasMenuKey && !hasBackKey) {
        //The device has a navigation bar
        val resources = this.resources

        val orientation = resources.configuration.orientation
        val resourceId: Int
        resourceId = if (resources.isTablet()) {
            resources.getIdentifier(
                if (orientation == Configuration.ORIENTATION_PORTRAIT) "navigation_bar_height"
                else "navigation_bar_height_landscape",
                "dimen",
                "android"
            )
        } else {
            resources.getIdentifier(
                if (orientation == Configuration.ORIENTATION_PORTRAIT) "navigation_bar_height"
                else "navigation_bar_width",
                "dimen",
                "android"
            )
        }

        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId)
        }
    }
    return result
}

/**
 * Returns whether this device is a tablet or not.
 */
fun Resources.isTablet(): Boolean {
    return configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK >= Configuration.SCREENLAYOUT_SIZE_LARGE
}
