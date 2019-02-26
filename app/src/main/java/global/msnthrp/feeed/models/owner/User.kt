package global.msnthrp.feeed.models.owner

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(

    @SerializedName("id")
    @Expose
    val id: Int = 0,

    @SerializedName("first_name")
    @Expose
    val firstName: String? = null,

    @SerializedName("last_name")
    @Expose
    val lastName: String? = null,

    @SerializedName("deactivated")
    val deactivated: String? = null,

    @SerializedName("hidden")
    val hidden: Int? = null,

    @SerializedName("is_closed")
    @Expose
    val isClosed: Boolean = false,

    @SerializedName("photo_100")
    @Expose
    val photo100: String? = null
) : Parcelable, Owner {
    companion object {
        const val FIELDS = "photo_100"
    }

    override fun getTitle() = "$firstName $lastName"

    override fun getPhoto() = photo100
}


