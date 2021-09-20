package com.valartech.commons.utils.timber

import android.util.Log
import timber.log.Timber

class CrashlyticsTree : Timber.DebugTree() {

    override fun log(priority: Int, tag: String?, message: String, throwable: Throwable?) {
        if (priority == Log.VERBOSE) {
            return
        }

        /*Crashlytics.setInt(CRASHLYTICS_KEY_PRIORITY, priority)
        Crashlytics.setString(CRASHLYTICS_KEY_TAG, tag)
        Crashlytics.setString(CRASHLYTICS_KEY_MESSAGE, message)

        if (priority == Log.DEBUG || priority == Log.INFO || priority == Log.WARN) {
            Crashlytics.log(priority, tag, message)
            return
        }*/
        //Timber.e gets till here
//        if (throwable == null) {
//            Crashlytics.logException(Exception(message))
//        } else if (throwable is AppException) {
//            val errorCode = throwable.errorCode
//            //no need to log common exceptions that can't be resolved on our end
//            if (!commonExceptions.contains(errorCode)) {
//                Crashlytics.logException(throwable)
//            }
//        } else {
//            //not an AppException, log it
//            Crashlytics.logException(throwable)
//        }
    }

    companion object {
        private const val CRASHLYTICS_KEY_PRIORITY = "priority"
        private const val CRASHLYTICS_KEY_TAG = "tag"
        private const val CRASHLYTICS_KEY_MESSAGE = "message"
//        private val commonExceptions = listOf(
//            HTTP_403_FORBIDDEN,
//            HTTP_401_UNAUTHORIZED,
//            HTTP_502_BAD_GATEWAY,
//            NO_ACTIVE_CONNECTION
//        )
    }
}
