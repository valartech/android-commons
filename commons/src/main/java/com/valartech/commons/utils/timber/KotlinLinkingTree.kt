package com.valartech.commons.utils.timber

import timber.log.Timber

/**
 * Makes line number links for logs.
 *
 * From this post: https://medium.com/@tauno/android-studio-pro-tip-go-to-source-from-logcat-output-f13bf46411b5
 * And this discussion: https://www.reddit.com/r/androiddev/comments/788dp7/android_studio_pro_tip_go_to_source_from_logcat/
 */
class KotlinLinkingTree : Timber.DebugTree() {
    override fun createStackElementTag(e: StackTraceElement) =
        "(${e.fileName}:${e.lineNumber})"
}
