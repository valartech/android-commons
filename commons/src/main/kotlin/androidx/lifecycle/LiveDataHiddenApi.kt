package androidx.lifecycle

/**
 * https://github.com/kxfeng/livedata-ext/blob/master/library/src/main/java/androidx/lifecycle/LiveDataHiddenApi.kt
 */
object LiveDataHiddenApi {
    /**
     * this function helps us in emitting a version with each instance of the live data
     * so that according to the version we can compare and know if it has been
     * handled previously or not.
     */
    fun LiveData<*>.version(): Int {
        return this.version
    }
}