package global.msnthrp.feeed.feed.model

import android.os.Parcelable
import global.msnthrp.feeed.models.attachments.Photo
import global.msnthrp.feeed.models.attachments.getPhotos
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PostPhoto(
    val photo: Photo,
    val wallPostId: String
) : Parcelable {

    companion object {

        fun fromWallPosts(posts: List<WallPost>): ArrayList<PostPhoto> {
            val result = arrayListOf<PostPhoto>()
            posts.forEach { wallPost ->
                wallPost.attachments
                    ?.getPhotos()
                    ?.forEach { photo ->
                        result.add(PostPhoto(photo, wallPost.toString()))
                    }
            }
            return result
        }
    }
}