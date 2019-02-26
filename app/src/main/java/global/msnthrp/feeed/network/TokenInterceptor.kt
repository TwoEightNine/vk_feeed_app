package global.msnthrp.feeed.network

import global.msnthrp.feeed.App
import global.msnthrp.feeed.storage.Session
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class TokenInterceptor(private val session: Session) : Interceptor {

    companion object {
        private const val ACCESS_TOKEN = "access_token"
        private const val VERSION = "v"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val builder = request.url().newBuilder()
        if (needParameters(request)) {
            builder.addQueryParameter(ACCESS_TOKEN, session.token)
                .addQueryParameter(VERSION, App.VERSION)
        }
        val url = builder.build()
        request = request.newBuilder()
            .url(url)
            .removeHeader(ApiService.NO_TOKEN_HEADER_KEY)
            .build()
        return chain.proceed(request)
    }

    private fun needParameters(request: Request) = request.header(ApiService.NO_TOKEN_HEADER_KEY).isNullOrEmpty()
}