package global.msnthrp.feeed.dagger.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import global.msnthrp.feeed.network.ApiService
import global.msnthrp.feeed.prefs.LivePrefs
import global.msnthrp.feeed.storage.DbHelper
import global.msnthrp.feeed.storage.Session
import global.msnthrp.feeed.utils.ApiUtils
import javax.inject.Singleton


@Module
class ContextModule(private val app: Application) {

    @Provides
    @Singleton
    fun provideContext(): Context = app

    @Provides
    @Singleton
    fun provideSession(context: Context): Session = Session(context)

    @Provides
    @Singleton
    fun provideDbHelper(context: Context): DbHelper = DbHelper(context)

    @Provides
    @Singleton
    fun provideLivePrefs(context: Context): LivePrefs = LivePrefs(context)

    @Provides
    @Singleton
    fun provideApiUtils(api: ApiService, session: Session, dbHelper: DbHelper): ApiUtils = ApiUtils(api, session, dbHelper)


}