package global.msnthrp.feeed.dagger.module

import dagger.Module
import dagger.Provides
import global.msnthrp.feeed.App
import global.msnthrp.feeed.BuildConfig
import global.msnthrp.feeed.network.ApiService
import global.msnthrp.feeed.network.TokenInterceptor
import global.msnthrp.feeed.storage.Session
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by msnthrp on 22/01/18.
 */

@Module
class NetworkModule {

    companion object {
        const val TIMEOUT = 45L
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val log = HttpLoggingInterceptor()
        log.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return log
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor(session: Session): TokenInterceptor = TokenInterceptor(session)

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor,
                            tokenInterceptor: TokenInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(tokenInterceptor)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .build()
    }

    @Provides
    @Singleton
    fun provideNetwork(client: OkHttpClient): Retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(App.API_BASE_URL)
            .client(client)
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)



}