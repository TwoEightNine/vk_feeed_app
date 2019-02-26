package global.msnthrp.feeed.feed.viewmodel

import global.msnthrp.feeed.feed.model.PostPhoto
import global.msnthrp.feeed.feed.model.WallPost
import global.msnthrp.feeed.feed.repo.FeedRepository
import global.msnthrp.feeed.models.Wrapper

class FavoritesFeedViewModel(repo: FeedRepository) : FeedViewModel(repo) {

    private var page = 0

    override fun loadFeed() {
        repo.loadFavorites(page * COUNT, COUNT, ::onFeedLoaded, ::onErrorOccurred)
    }

    override fun resetFeed() {
        page = 0
        wallPosts.clear()
    }

    private fun onFeedLoaded(
        postPhotos: ArrayList<PostPhoto>,
        wallPosts: ArrayList<WallPost>
    ) {
        this.wallPosts.addAll(wallPosts)
        val existing = if (page == 0) {
            arrayListOf()
        } else {
            feedLiveData.value?.data ?: return
        }
        page++
        feedLiveData.value = Wrapper(existing.apply { addAll(postPhotos) })
    }

    companion object {
        const val COUNT = 100
    }
}