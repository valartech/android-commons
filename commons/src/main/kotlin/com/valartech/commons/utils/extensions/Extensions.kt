package com.valartech.commons.utils.extensions

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
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

fun EditText.showKeyboard(context: Context) {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

/**
 * https://stackoverflow.com/a/10947374/3460025
 */
fun TextView.underline() {
    this.paintFlags = paintFlags or Paint.UNDERLINE_TEXT_FLAG
}

/**
 * https://stackoverflow.com/a/6796829/3460025
 */
fun TextView.removeUnderline() {
    this.paintFlags = paintFlags and Paint.UNDERLINE_TEXT_FLAG.inv()
}

/**
 * https://stackoverflow.com/a/18669307/3460025
 */
fun View.getAllChildren(): List<View> {
    val visited = ArrayList<View>()
    val unvisited = ArrayList<View>()
    unvisited.add(this)

    while (unvisited.isNotEmpty()) {
        val child = unvisited.removeAt(0)
        visited.add(child)
        if (child !is ViewGroup) continue
        val childCount = child.childCount
        for (i in 0 until childCount) unvisited.add(child.getChildAt(i))
    }

    return visited
}
