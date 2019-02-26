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
    val photo200: String
) : Parcelable, Owner {

    override fun getTitle() = name

    override fun getPhoto() = photo100
}