package global.msnthrp.feeed.storage

import android.util.Log

object Lg {

    private const val APP_TAG = "qwer"
    private const val ALLOWED_LEN = 500
    private val logs = arrayListOf<String>()

    fun i(s: String) {
        Log.i(APP_TAG, s)
        logs.add(s)
        truncate()
    }

    fun wtf(s: String) {
        Log.wtf(APP_TAG, s)
        logs.add(s)
        truncate()
    }

    fun getFormatted() = logs.joinToString(separator = "\n")

    private fun truncate() {
        while (logs.size > ALLOWED_LEN) {
            logs.removeAt(0)
        }
    }

}