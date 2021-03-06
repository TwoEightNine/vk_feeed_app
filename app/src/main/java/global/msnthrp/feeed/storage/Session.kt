package global.msnthrp.feeed.storage

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class Session @Inject constructor(private val context: Context) {

    private val prefs: SharedPreferences by lazy {
        context.getSharedPreferences(NAME, Context.MODE_PRIVATE)
    }

    var token: String
        get() = prefs.getString(TOKEN, "") ?: ""
        set(value) = prefs.edit().putString(TOKEN, value).apply()

    var userId: Int
        get() = prefs.getInt(UID, 0)
        set(value) = prefs.edit().putInt(UID, value).apply()

    var userName: String
        get() = prefs.getString(USER_NAME, "") ?: ""
        set(value) = prefs.edit().putString(USER_NAME, value).apply()

    fun logOut() {
        token = ""
        userId = 0
        userName = ""
    }

    companion object {
        const val NAME = "session"

        const val TOKEN = "token"
        const val UID = "userId"
        const val USER_NAME = "userName"
    }
}