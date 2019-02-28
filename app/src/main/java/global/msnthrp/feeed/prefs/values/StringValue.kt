package global.msnthrp.feeed.prefs.values

import android.content.SharedPreferences

class StringValue(
    prefs: SharedPreferences,
    key: String
) : PrefValue<String>(prefs, key) {

    override fun getValue(): String = prefs.getString(key, "") ?: ""

    override fun setValue(value: String) = prefs.edit().putString(key, value).apply()
}