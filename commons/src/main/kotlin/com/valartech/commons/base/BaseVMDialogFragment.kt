package com.valartech.commons.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.valartech.commons.aac.ViewModelFactory
import javax.inject.Inject

/**
 * Caution: do NOT use this in case you need to share your [ViewModel] across fragments. You'll need
 * to call [ViewModelProviders.of] with the Activity reference.
 */
abstract class BaseVMDialogFragment<VM : ViewModel> : BaseDialogFragment() {

    protected abstract val vmClassToken: Class<VM>

    protected val viewModel: VM by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(vmClassToken)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<VM>
}