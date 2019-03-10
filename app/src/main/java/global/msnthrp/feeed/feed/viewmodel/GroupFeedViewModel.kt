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
    private val subscriptionLiveData = WrappedMutableLiveData<Boolean>()
    private var page = 0

    private lateinit var group: Group

    fun init(gr: Group) {
        if (!::group.isInitialized) {
            group = gr
            subscriptionLiveData.value = Wrapper(group.isMember)
        }
    }

    fun getAvatar() = avatarLiveData as WrappedLiveData<Photo>

    fun getSubscription() = subscriptionLiveData as WrappedLiveData<Boolean>

    fun loadAvatar() {
        repo.loadAvatar(group, ::onAvatarLoaded) {
            avatarLiveData.value = Wrapper(error = it)
        }
    }

    fun subscribeOrNot() {
        if (group.isMember) {
            repo.unsubscribe(group, ::onSubscribedOrNot, ::onSubscribeError)
        } else {
            repo.subscribe(group, ::onSubscribedOrNot, ::onSubscribeError)
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

    private fun onSubscribedOrNot() {
        group.isMember = !group.isMember
        subscriptionLiveData.value = Wrapper(group.isMember)
    }

    private fun onSubscribeError(error: String) {
        subscriptionLiveData.value = Wrapper(error = error)
    }

    companion object {
        const val COUNT = 100
    }
}