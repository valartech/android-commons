package com.valartech.commons.aac.navigation

import androidx.navigation.NavDirections

/**
 * To make navigating directly from ViewModels possible.
 *
 * https://medium.com/google-developer-experts/using-navigation-architecture-component-in-a-large-banking-app-ac84936a42c2
 */
sealed class NavigationCommand {
    data class To(val directions: NavDirections) : NavigationCommand()
    object Back : NavigationCommand()
    data class BackTo(val destinationId: Int) : NavigationCommand()
//    object ToRoot : NavigationCommand()
}
