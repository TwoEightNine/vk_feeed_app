package global.msnthrp.feeed.feed.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import global.msnthrp.feeed.feed.model.PostPhoto
import global.msnthrp.feeed.feed.model.WallPost
import global.msnthrp.feeed.feed.repo.FeedRepository
import global.msnthrp.feeed.models.WrappedLiveData
import global.msnthrp.feeed.models.WrappedMutableLiveData
import global.msnthrp.feeed.models.Wrapper
import java.lang.IllegalArgumentException
import javax.inject.Inject

abstract class FeedViewModel(protected val repo: FeedRepository) : ViewModel() {

    val wallPosts = arrayListOf<WallPost>()

    protected val feedLiveData = WrappedMutableLiveData<ArrayList<PostPhoto>>()

    fun getFeed() = feedLiveData as WrappedLiveData<ArrayList<PostPhoto>>

    abstract fun loadFeed()

    abstract fun resetFeed()

    protected fun onErrorOccurred(error: String) {
        feedLiveData.value = Wrapper(error = error)
    }

    class Factory @Inject constructor(private val feedRepository: FeedRepository) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>) = when(modelClass.simpleName) {
            "NewsFeedViewModel" -> NewsFeedViewModel(feedRepository) as T
            "SuggestionsFeedViewModel" -> SuggestionsFeedViewModel(feedRepository) as T
            "GroupFeedViewModel" -> GroupFeedViewModel(feedRepository) as T
            "FavoritesFeedViewModel" -> FavoritesFeedViewModel(feedRepository) as T
            else -> throw IllegalArgumentException("Unable to create an instance of ${modelClass.simpleName}")
        }
    }
}