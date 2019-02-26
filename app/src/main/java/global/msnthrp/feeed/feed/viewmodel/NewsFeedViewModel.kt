package global.msnthrp.feeed.feed.viewmodel

import global.msnthrp.feeed.feed.model.PostPhoto
import global.msnthrp.feeed.feed.model.WallPost
import global.msnthrp.feeed.feed.repo.FeedRepository
import global.msnthrp.feeed.models.Wrapper

class NewsFeedViewModel(repo: FeedRepository) : FeedViewModel(repo) {

    private var nextFrom: String? = null

    override fun loadFeed() {
        repo.loadNewsFeed(nextFrom, ::onFeedLoaded, ::onErrorOccurred)
    }

    override fun resetFeed() {
        nextFrom = null
        wallPosts.clear()
    }

    private fun onFeedLoaded(
        postPhotos: ArrayList<PostPhoto>,
        wallPosts: ArrayList<WallPost>,
        nextFrom: String?
    ) {
        this.wallPosts.addAll(wallPosts)
        val existing = if (this.nextFrom.isNullOrEmpty()) {
            arrayListOf()
        } else {
            feedLiveData.value?.data ?: return
        }
        this.nextFrom = nextFrom
        feedLiveData.value = Wrapper(existing.apply { addAll(postPhotos) })
    }
}