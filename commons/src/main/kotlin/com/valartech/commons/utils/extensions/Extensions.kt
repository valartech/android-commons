package com.valartech.commons.utils.extensions

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.valartech.commons.R
import java.text.DecimalFormat
import kotlin.reflect.KClass

fun <T : AppCompatActivity> AppCompatActivity.startActivity(clazz: KClass<T>) {
    val intent = Intent(this, clazz.java)
    startActivity(intent)
}

fun ViewGroup.inflate(layoutRes: Int): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, false)
}

fun Float.formatINR(): String {
    return DecimalFormat("##,##,##,##,##0").format(this)
}

fun Float.formatINRwithPrefix(context: Context?): String {
    val formattedNum = this.formatINR()
    return context?.let {
        String.format(context.getString(R.string.rs_string), formattedNum)
    } ?: formattedNum
}

fun Float.removeTrailingZeroes(): String {
    return DecimalFormat("#").format(this)
}

fun String?.isValidEmail() =
    !this.isNullOrBlank() && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun View.setVisibility(isVisible: Boolean, setInvisible: Boolean = false) {
    visibility = if (isVisible) {
        View.VISIBLE
    } else {
        if (setInvisible) {
            View.INVISIBLE
        } else {
            View.GONE
        }
    }
}
