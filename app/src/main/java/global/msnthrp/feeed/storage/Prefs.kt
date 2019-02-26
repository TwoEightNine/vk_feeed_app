package global.msnthrp.feeed.storage

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class Prefs @Inject constructor(private val context: Context) {

    private val prefs: SharedPreferences by lazy {
        context.getSharedPreferences(NAME, Context.MODE_PRIVATE)
    }

    var hideAds: Boolean
        get() = prefs.getBoolean(HIDE_ADS, true)
        set(value) = prefs.edit().putBoolean(HIDE_ADS, value).apply()

    var columnsCount: Int
        get() = prefs.getInt(COLUMNS_COUNT, 3)
        set(value) = prefs.edit().putInt(COLUMNS_COUNT, value).apply()

    companion object {
        const val NAME = "prefs"

        const val HIDE_ADS = "hideAds"
        const val COLUMNS_COUNT = "gridSpanCount"
    }
}