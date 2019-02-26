package global.msnthrp.feeed.dagger

import dagger.Component
import global.msnthrp.feeed.dagger.module.ContextModule
import global.msnthrp.feeed.dagger.module.NetworkModule
import global.msnthrp.feeed.dialogs.fragments.DialogsFragment
import global.msnthrp.feeed.feed.fragments.FeedFragment
import global.msnthrp.feeed.imageviewer.activities.ImageViewerActivity
import global.msnthrp.feeed.login.LoginActivity
import global.msnthrp.feeed.search.fragments.SearchFragment
import global.msnthrp.feeed.services.DownloadFileService
import global.msnthrp.feeed.settings.SettingsFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [ContextModule::class, NetworkModule::class])
interface AppComponent {

    fun inject(imageViewerActivity: ImageViewerActivity)
    fun inject(loginActivity: LoginActivity)

    fun inject(downloadFileService: DownloadFileService)

    fun inject(feedFragment: FeedFragment)
    fun inject(searchFragment: SearchFragment)
    fun inject(settingsFragment: SettingsFragment)
    fun inject(dialogsFragment: DialogsFragment)
}