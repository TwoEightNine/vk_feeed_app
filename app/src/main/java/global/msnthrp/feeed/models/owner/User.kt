package global.msnthrp.feeed.models.owner

import android.content.Context
import android.net.Uri
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import global.msnthrp.feeed.R
import global.msnthrp.feeed.storage.Session
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
    val photo100: String? = null,

    val myself: Boolean = false
) : Parcelable, Owner {
    companion object {
        const val FIELDS = "photo_100"

        fun createOwn(context: Context, session: Session) = User(
            id = session.userId,
            firstName = context.getString(R.string.saved_messages),
            lastName = "",
            myself = true
        )
    }

    override fun getTitle() = "$firstName $lastName"

    override fun getPhoto() = photo100
}


