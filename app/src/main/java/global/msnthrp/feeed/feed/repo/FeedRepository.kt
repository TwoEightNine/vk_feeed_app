package global.msnthrp.feeed.feed.repo

import global.msnthrp.feeed.feed.model.FeedResponse
import global.msnthrp.feeed.feed.model.PostPhoto
import global.msnthrp.feeed.feed.model.WallPost
import global.msnthrp.feeed.models.attachments.Photo
import global.msnthrp.feeed.models.owner.Group
import global.msnthrp.feeed.network.ApiService
import global.msnthrp.feeed.network.BaseResponse
import global.msnthrp.feeed.prefs.LivePrefs
import global.msnthrp.feeed.utils.subscribeSmart
import io.reactivex.Single
import javax.inject.Inject

class FeedRepository @Inject constructor(
    private val api: ApiService,
    private val livePrefs: LivePrefs
) {

    fun loadNewsFeed(
        nextFrom: String? = null,
        onSuccess: (ArrayList<PostPhoto>, ArrayList<WallPost>, String?) -> Unit,
        onError: (String) -> Unit
    ) {
        api.getNewsfeed(nextFrom)
            .processWithNextFrom(onSuccess, onError)
    }

    fun loadSuggestions(
        nextFrom: String? = null,
        onSuccess: (ArrayList<PostPhoto>, ArrayList<WallPost>, String?) -> Unit,
        onError: (String) -> Unit
    ) {
        api.getSuggestions(nextFrom)
            .processWithNextFrom(onSuccess, onError)
    }

    fun loadGroupWall(
        offset: Int,
        count: Int,
        group: Group,
        onSuccess: (ArrayList<PostPhoto>, ArrayList<WallPost>) -> Unit,
        onError: (String) -> Unit
    ) {
        api.getWall(-group.id, count, offset)
            .processWithOffset(onSuccess, onError)
    }

    fun loadFavorites(
        offset: Int,
        count: Int,
        onSuccess: (ArrayList<PostPhoto>, ArrayList<WallPost>) -> Unit,
        onError: (String) -> Unit
    ) {
        api.getFavorites(count, offset)
            .processWithOffset(onSuccess, onError)
    }

    fun loadAvatar(
        group: Group,
        onSuccess: (Photo?) -> Unit,
        onError: (String) -> Unit
    ) {
        api.getAvatar(-group.id)
            .subscribeSmart({ listResponse ->
                if (listResponse.items.isNotEmpty()) {
                    onSuccess(listResponse.items[0])
                } else {
                    onSuccess(null)
                }
            }, { _, message ->
                onError(message)
            })
    }

    private fun Single<BaseResponse<FeedResponse>>.processWithOffset(
        onSuccess: (ArrayList<PostPhoto>, ArrayList<WallPost>) -> Unit,
        onError: (String) -> Unit
    ) {
        this.subscribeSmart({ feedResponse ->
            val wallPosts = feedResponse.getFilledItems(hideAds())
            val postPhotos = PostPhoto.fromWallPosts(wallPosts)
            wallPosts.forEach { it.attachments = null }
            onSuccess(postPhotos, wallPosts)
        }, { _, message ->
            onError(message)
        })
    }

    private fun Single<BaseResponse<FeedResponse>>.processWithNextFrom(
        onSuccess: (ArrayList<PostPhoto>, ArrayList<WallPost>, String?) -> Unit,
        onError: (String) -> Unit
    ) {
        this.subscribeSmart({ feedResponse ->
            val wallPosts = feedResponse.getFilledItems(hideAds())
            val postPhotos = PostPhoto.fromWallPosts(wallPosts)
            wallPosts.forEach { it.attachments = null }
            onSuccess(postPhotos, wallPosts, feedResponse.nextFrom)
        }, { _, message ->
            onError(message)
        })
    }

    private fun hideAds() = livePrefs.hideAds.getValue()

}