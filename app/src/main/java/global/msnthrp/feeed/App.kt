package global.msnthrp.feeed

import android.app.Application
import global.msnthrp.feeed.dagger.AppComponent
import global.msnthrp.feeed.dagger.DaggerAppComponent
import global.msnthrp.feeed.dagger.module.ContextModule

class App : Application() {

    companion object {
        lateinit var appComponent: AppComponent

        const val VERSION = "5.92"
        const val APP_ID = 6079611 // 6882114 // after approving
        const val SCOPE_ALL = 339974 //with messages
        const val REDIRECT_URL = "https://oauth.vk.com/blank.html"
        const val API_BASE_URL = "https://api.vk.com/method/"

        val ID_HASHES = arrayListOf("260ca2827e258c06153e86d121de1094", "44b8e44538545051a8bd710e5e10e5ce", "7c3785059f7ffd4a21d38bd203d13721")
        val ID_SALTS = arrayListOf("iw363c8b6385cy4", "iw57xs57fdvb4en", "i26734c8vb34tr")
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .contextModule(ContextModule(this))
            .build()
    }
}