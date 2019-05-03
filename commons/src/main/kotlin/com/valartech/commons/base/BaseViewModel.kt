package com.valartech.commons.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.valartech.commons.aac.navigation.NavigationCommand

abstract class BaseViewModel : ViewModel() {

    private val _navigationCommands = MutableLiveData<NavigationCommand>()
    val navigationCommands: LiveData<NavigationCommand>
        get() = _navigationCommands

    fun navigate(directions: NavDirections) {
        _navigationCommands.postValue(NavigationCommand.To(directions))
    }
}
