package global.msnthrp.feeed.feed.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import global.msnthrp.feeed.models.attachments.hasSmthToShow
import global.msnthrp.feeed.models.owner.Group
import global.msnthrp.feeed.models.owner.User

data class FeedResponse(

    @SerializedName("items")
    @Expose
    val items: List<WallPost>,

    @SerializedName("profiles")
    @Expose
    val profiles: List<User>,

    @SerializedName("groups")
    @Expose
    val groups: List<Group>,

    @SerializedName("next_from")
    @Expose
    val nextFrom: String?
) {

    /**
     * filters posts with photos
     * fills items with owners
     */
    fun getFilledItems(hideAds: Boolean = false): ArrayList<WallPost> {
        val items = ArrayList(this.items.filter { it.attachments.hasSmthToShow() })
        items.forEach {
            it.owner = if (it.ownerId() > 0) {
                getUserById(it.ownerId())
            } else {
                getGroupById(-it.ownerId())
            }
        }
        if (hideAds) {
            items.removeAll { it.isAds }
        }
        return ArrayList(items.distinctBy { it.toString() })
    }

    private fun getGroupById(groupId: Int) = groups.firstOrNull { it.id == groupId }

    private fun getUserById(userId: Int) = profiles.firstOrNull { it.id == userId }
}