package com.valartech.common

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.valartech.commons.views.LoadingLayout.Companion.COMPLETE
import com.valartech.commons.views.LoadingLayout.Companion.EMPTY
import com.valartech.commons.views.LoadingLayout.Companion.ERROR
import com.valartech.commons.views.LoadingLayout.Companion.LOADING
import com.valartech.commons.views.LoadingLayout.Companion.LOADING_OVERLAY
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loading.setOnClickListener { mainLoadingLayout.setState(LOADING) }
        loadingOverlay.setOnClickListener { mainLoadingLayout.setState(LOADING_OVERLAY) }
        complete.setOnClickListener { mainLoadingLayout.setState(COMPLETE) }
        empty.setOnClickListener { mainLoadingLayout.setState(EMPTY) }
        error.setOnClickListener { mainLoadingLayout.setState(ERROR) }
    }
}
