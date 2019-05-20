package com.valartech.commons.base

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.valartech.commons.aac.ViewModelFactory
import com.valartech.commons.aac.navigation.NavigationCommand
import com.valartech.commons.toolbar_fragment.FragmentToolbar
import com.valartech.commons.toolbar_fragment.ToolbarManager
import javax.inject.Inject

/**
 * Caution: do NOT use this in case you need to share your [ViewModel] across fragments. You'll need
 * to call [ViewModelProviders.of] with the Activity reference.
 */
abstract class BaseVMFragment<VM : ViewModel> : BaseFragment() {

    protected abstract val vmClassToken: Class<VM>

    protected val viewModel: VM by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(vmClassToken)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<VM>

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (viewModel is BaseViewModel) {
            val viewModel = viewModel as BaseViewModel
            viewModel.navigationCommands.observe(this, Observer { command ->
                when (command) {
                    is NavigationCommand.To -> findNavController().navigate(command.directions)
                    is NavigationCommand.Back -> findNavController().popBackStack()
                    is NavigationCommand.BackTo -> findNavController().popBackStack(
                        command.destinationId,
                        false
                    ) //todo true?
//                is NavigationCommand.ToRoot -> findNavController().naviup
                }
            })
        }

    }

    /**
     * how to use
    https://medium.com/appunite-edu-collection/fragment-toolbar-manage-it-better-a6a6ef31d43
     */
    //toolbar fragment
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ToolbarManager(builder(), view).prepareToolbar()
    }

    protected abstract fun builder(): FragmentToolbar


}
