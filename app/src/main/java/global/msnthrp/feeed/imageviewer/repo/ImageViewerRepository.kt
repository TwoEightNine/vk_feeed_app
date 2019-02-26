package global.msnthrp.feeed.imageviewer.repo

import global.msnthrp.feeed.feed.model.WallPost
import global.msnthrp.feeed.models.attachments.Photo
import global.msnthrp.feeed.network.ApiService
import global.msnthrp.feeed.utils.subscribeSmart
import javax.inject.Inject

class ImageViewerRepository @Inject constructor(private val api: ApiService) {

    /**
     * @param callback true if succeed
     */
    fun like(wallPost: WallPost, callback: (Boolean) -> Unit) {
        api.like(wallPost.ownerId(), wallPost.id())
            .subscribeSmart({
                callback(true)
            }, { _, _ ->
                callback(false)
            })
    }

    /**
     * @param callback true if succeed
     */
    fun unlike(wallPost: WallPost, callback: (Boolean) -> Unit) {
        api.unlike(wallPost.ownerId(), wallPost.id())
            .subscribeSmart({
                callback(true)
            }, { _, _ ->
                callback(false)
            })
    }

    fun copyImage(photo: Photo, callback: (Boolean) -> Unit) {
        api.copyPhoto(photo.ownerId, photo.id)
            .subscribeSmart({
                callback(true)
            }, { _, _ ->
                callback(false)
            })
    }
}