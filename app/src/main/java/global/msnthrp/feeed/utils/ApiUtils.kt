package global.msnthrp.feeed.utils

import global.msnthrp.feeed.feed.model.WallPost
import global.msnthrp.feeed.models.owner.User
import global.msnthrp.feeed.models.attachments.Photo
import global.msnthrp.feeed.network.ApiService
import global.msnthrp.feeed.storage.DbHelper
import global.msnthrp.feeed.storage.Session
import javax.inject.Inject

class ApiUtils @Inject constructor(
    private val api: ApiService,
    private val session: Session,
    private val dbHelper: DbHelper
) {

    /**
     * check if account is valid
     */
    fun getMyself(callback: (User?) -> Unit) {
        api.getUsers("${session.userId}", User.FIELDS)
            .subscribeSmart({
                callback(it[0])
            }, { _, _ ->
                callback(null)
            }, {
                callback(null)
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

    fun likePost(wallPost: WallPost, callback: (Boolean) -> Unit) {
        api.like(wallPost.ownerId(), wallPost.id())
            .subscribeSmart( {
                callback(true)
            }, { _, _ ->
                callback(false)
            })
    }

    fun unlikePost(wallPost: WallPost, callback: (Boolean) -> Unit) {
        api.unlike(wallPost.ownerId(), wallPost.id())
            .subscribeSmart( {
                callback(true)
            }, { _, _ ->
                callback(false)
            })
    }

    fun trackVisitor() {
        api.trackVisitor()
            .subscribeSmart({}, { _, _ -> })
    }

}