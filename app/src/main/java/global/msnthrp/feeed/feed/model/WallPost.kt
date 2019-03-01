package global.msnthrp.feeed.feed.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import global.msnthrp.feeed.models.attachments.Attachment
import global.msnthrp.feeed.models.owner.Owner
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WallPost(

    @SerializedName("id")
    @Expose
    val id: Int = 0,

    @SerializedName("post_id")
    @Expose
    val postId: Int = 0,

    @SerializedName("owner_id")
    @Expose
    val ownerId: Int = 0,

    @SerializedName("source_id")
    @Expose
    val sourceId: Int = 0,

    @SerializedName("date")
    @Expose
    val date: Int = 0,

    @SerializedName("text")
    @Expose
    val text: String = "",

    @SerializedName("marked_as_ads")
    @Expose
    val markedAsAds: Int = 0,

    @SerializedName("likes")
    @Expose
    val likes: Likes = Likes(),

    @SerializedName("attachments")
    @Expose
    var attachments: ArrayList<Attachment>? = null,

    var owner: Owner? = null
) : Parcelable {

    override fun toString() = "wall${ownerId()}_${id()}"

    var isLiked: Boolean
        get() = likes.userLikes == 1
        set(value) {
            likes.userLikes = if (value) 1 else 0
        }

    val isAds: Boolean
        get() = markedAsAds == 1

    fun id() = if (id != 0) id else postId

    fun ownerId() = if (ownerId != 0) ownerId else sourceId
}

@Parcelize
data class Likes(

    @SerializedName("user_likes")
    @Expose
    var userLikes: Int = 0
) : Parcelable
