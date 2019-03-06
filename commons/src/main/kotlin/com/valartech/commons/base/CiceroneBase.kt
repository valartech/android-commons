package com.valartech.commons.base

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

abstract class FragmentScreen(private val f: Fragment): SupportAppScreen() {
    override fun getFragment() = f
}

abstract class ActivityScreen<T: AppCompatActivity>(private val activityClassToken: Class<T>): SupportAppScreen() {

    override fun getActivityIntent(context: Context?): Intent {
        return Intent(context, activityClassToken)
    }
}
