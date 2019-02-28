package global.msnthrp.feeed.prefs.values

import android.content.SharedPreferences
import com.j256.ormlite.stmt.query.In

class IntValue(
    prefs: SharedPreferences,
    key: String,
    private val defaultValue: Int = 0) : PrefValue<Int>(prefs, key) {

    override fun getValue() = prefs.getInt(key, defaultValue)

    override fun setValue(value: Int) = prefs.edit().putInt(key, value).apply()
}