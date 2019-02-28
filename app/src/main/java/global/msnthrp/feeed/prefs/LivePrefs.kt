package global.msnthrp.feeed.prefs

import android.content.Context
import global.msnthrp.feeed.prefs.values.BooleanValue
import global.msnthrp.feeed.prefs.values.IntValue
import javax.inject.Inject

class LivePrefs @Inject constructor(private val context: Context) {

    private val prefs by lazy {
        context.getSharedPreferences("live_prefs", Context.MODE_PRIVATE)
    }

    val hideAds = BooleanValue(prefs, "hideAds", true)

    val gridColumns = IntValue(prefs, "gridColumns", 3)
}