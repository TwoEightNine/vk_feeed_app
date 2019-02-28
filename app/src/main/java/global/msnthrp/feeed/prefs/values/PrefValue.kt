package global.msnthrp.feeed.prefs.values

import android.content.SharedPreferences
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

abstract class PrefValue<T>(
    protected val prefs: SharedPreferences,
    protected val key: String
) {

    private val liveData by lazy {
        val ld = MutableLiveData<T>()
        ld.value = getValue()
        ld
    }

    abstract fun getValue(): T

    protected abstract fun setValue(value: T)

    fun get() = liveData as LiveData<T>

    fun observe(owner: LifecycleOwner, observerFunc: (T) -> Unit) {
        get().observe(owner, Observer(observerFunc))
    }

    fun set(value: T) {
        setValue(value)
        liveData.value = value
    }
}