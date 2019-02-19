package com.valartech.commons.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.valartech.commons.utils.ViewModelFactory
import javax.inject.Inject

abstract class BaseVMFragment<VM : ViewModel> : BaseFragment() {

    protected abstract val vmClassToken: Class<VM>

    protected val viewModel: VM by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(vmClassToken)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<VM>
}
