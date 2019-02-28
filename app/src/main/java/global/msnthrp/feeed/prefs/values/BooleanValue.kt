package global.msnthrp.feeed.prefs.values

import android.content.SharedPreferences
import com.j256.ormlite.stmt.query.In

class BooleanValue(
    prefs: SharedPreferences,
    key: String,
    private val defaultValue: Boolean = false) : PrefValue<Boolean>(prefs, key) {

    override fun getValue() = prefs.getBoolean(key, defaultValue)

    override fun setValue(value: Boolean) = prefs.edit().putBoolean(key, value).apply()
}