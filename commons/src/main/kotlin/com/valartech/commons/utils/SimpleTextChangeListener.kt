package com.valartech.commons.utils

import android.text.Editable
import android.text.TextWatcher

/**
 * Provides default implementations for functions that don't usually need overriding.
 */
interface SimpleTextChangeListener : TextWatcher {

    override fun afterTextChanged(s: Editable?) {}

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
}
