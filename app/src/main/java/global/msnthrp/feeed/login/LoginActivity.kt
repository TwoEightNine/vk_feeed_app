package global.msnthrp.feeed.login

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.webkit.CookieManager
import android.webkit.CookieSyncManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import global.msnthrp.feeed.App
import global.msnthrp.feeed.R
import global.msnthrp.feeed.main.MainActivity
import global.msnthrp.feeed.storage.DbHelper
import global.msnthrp.feeed.storage.Session
import global.msnthrp.feeed.utils.*
import kotlinx.android.synthetic.main.activity_login.*
import java.util.regex.Pattern
import javax.inject.Inject


class LoginActivity : AppCompatActivity() {

    companion object {

        const val ARG_NEW_ACC = "newAcc"

        fun launch(context: Context?, newAccount: Boolean = false) {
            context?.startActivity(Intent(context, LoginActivity::class.java).apply {
                putExtra(ARG_NEW_ACC, newAccount)
            })
        }

        private const val LOGIN_URL = "https://oauth.vk.com/authorize?" +
                "client_id=${App.APP_ID}&" +
                "scope=${App.SCOPE_ALL}&" +
                "redirect_uri=${App.REDIRECT_URL}&" +
                "display=touch&" +
                "v=${App.VERSION}&" +
                "response_type=token"
    }

    @Inject
    lateinit var session: Session

    @Inject
    lateinit var apiUtils: ApiUtils

    @Inject
    lateinit var dbHelper: DbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        App.appComponent.inject(this)
        checkToken()
    }

    private fun checkToken() {
        val token = session.token
        val uid = session.userId
        val shouldLogin = token.isEmpty() || intent.extras?.getBoolean(ARG_NEW_ACC) == true
        when {
            shouldLogin && !isOnline(this) -> noInternet()
            shouldLogin -> toLogIn()
            else -> toDialogs()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun toLogIn() {
        l("logging in")
        webView.hide()
        webView.settings?.javaScriptEnabled = true
        CookieSyncManager.createInstance(webView.context).sync()
        val man = CookieManager.getInstance()
        man.removeAllCookie()
        webView.settings?.javaScriptCanOpenWindowsAutomatically = true
        webView.webViewClient = ParsingWebClient()

        webView.loadUrl(LOGIN_URL)
        if (!isOnline(this)) {
            showToast(this, R.string.no_internet)
            finish()
            return
        }
        webView.show()
    }

    private fun noInternet() {
        l("no internet")
        showToast(this, R.string.no_internet_for_logging_in)
        finish()
    }

    private fun toDialogs() {
        if (!isDev(session.userId)) {
            apiUtils.trackVisitor()
        }
        MainActivity.launch(this)
        finish()
    }

    fun doneWithThis(url: String) {
        val token = extract(url, "access_token=(.*?)&")
        val userId: Int
        try {
            userId = extract(url, "user_id=(\\d*)").toInt()
        } catch (e: Exception) {
            onFailed()
            return
        }
        l("token obtained ...${token.substring(token.length - 6)}")

        progressBar.show()
        webView.hide()

        session.token = token
        session.userId = userId

        apiUtils.getMyself { user ->
            if (user != null) {
                session.userName = user.getTitle()
                toDialogs()
            } else {
                onFailed()
            }
        }
    }

    private fun onFailed() {
        showToast(this, R.string.error_loading_user)
        session.token = ""
        finish()
    }

    private fun extract(from: String, regex: String): String {
        val pattern = Pattern.compile(regex)
        val matcher = pattern.matcher(from)
        if (!matcher.find()) {
            return ""
        }
        return matcher.toMatchResult().group(1)
    }

    /**
     * handles redirect and calls token parsing
     */
    private inner class ParsingWebClient : WebViewClient() {

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            progressBar.hide()
            if (url.startsWith(App.REDIRECT_URL)) {
                doneWithThis(url)
            }
        }
    }
}