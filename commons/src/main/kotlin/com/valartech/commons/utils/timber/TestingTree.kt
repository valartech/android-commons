package com.valartech.commons.utils.timber

import timber.log.Timber


/**
 * Prints out logs to the standard Java Console so that they're viewable during tests.
 *
 * Plant in the @Before methods in your tests.
 */
class TestingTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        when {
            t != null -> t.printStackTrace()
            tag != null -> println("$tag: $message")
            else -> println(message)
        }
    }
}
