package global.msnthrp.feeed.models.owner

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Group(

    @SerializedName("id")
    @Expose
    val id: Int,

    @SerializedName("name")
    @Expose
    val name: String,

    @SerializedName("screen_name")
    @Expose
    val screenName: String,

    @SerializedName("photo_100")
    @Expose
    val photo100: String,

    @SerializedName("photo_200")
    @Expose
    val photo200: String,

    @SerializedName("is_member")
    @Expose
    var membership: Int
) : Parcelable, Owner {

    var isMember: Boolean
        get() = membership == 1
        set(value) {
            membership = if (value) 1 else 0
        }

    override fun getTitle() = name

    override fun getPhoto() = photo100
}