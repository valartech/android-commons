package androidx.lifecycle

/**
 * https://github.com/kxfeng/livedata-ext/blob/master/library/src/main/java/androidx/lifecycle/LiveDataHiddenApi.kt
 */
object LiveDataHiddenApi {
    fun LiveData<*>.version(): Int {
        return this.version
    }
}