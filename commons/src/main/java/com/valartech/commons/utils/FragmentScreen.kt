package com.valartech.commons.utils

import androidx.fragment.app.Fragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

abstract class FragmentScreen(val f: Fragment): SupportAppScreen() {
    override fun getFragment() = f
}
