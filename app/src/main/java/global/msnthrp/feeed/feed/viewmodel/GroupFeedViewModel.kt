package global.msnthrp.feeed.feed.viewmodel

import global.msnthrp.feeed.feed.model.PostPhoto
import global.msnthrp.feeed.feed.model.WallPost
import global.msnthrp.feeed.feed.repo.FeedRepository
import global.msnthrp.feeed.models.WrappedLiveData
import global.msnthrp.feeed.models.WrappedMutableLiveData
import global.msnthrp.feeed.models.Wrapper
import global.msnthrp.feeed.models.attachments.Photo
import global.msnthrp.feeed.models.owner.Group

class GroupFeedViewModel(repo: FeedRepository) : FeedViewModel(repo) {

    private val avatarLiveData = WrappedMutableLiveData<Photo>()
    private var page = 0

    private lateinit var group: Group

    fun init(gr: Group) {
        if (!::group.isInitialized) {
            group = gr
        }
    }

    fun getAvatar() = avatarLiveData as WrappedLiveData<Photo>

    fun loadAvatar() {
        repo.loadAvatar(group, ::onAvatarLoaded) {
            avatarLiveData.value = Wrapper(error = it)
        }
    }

    override fun loadFeed() {
        repo.loadGroupWall(page * COUNT, COUNT, group, ::onFeedLoaded, ::onErrorOccurred)
    }

    override fun resetFeed() {
        page = 0
        wallPosts.clear()
    }

    private fun onAvatarLoaded(photo: Photo?) {
        avatarLiveData.value = Wrapper(photo)
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