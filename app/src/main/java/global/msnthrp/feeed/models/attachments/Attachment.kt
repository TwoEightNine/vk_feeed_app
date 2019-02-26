package global.msnthrp.feeed.models.attachments

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Attachment(

    @SerializedName("type")
    @Expose
    val type: String,

    @SerializedName(TYPE_PHOTO)
    @Expose
    val photo: Photo? = null
) : Parcelable {
    companion object {
        const val TYPE_PHOTO = "photo"
    }
}

fun ArrayList<Attachment>.getPhotos() = ArrayList(this.mapNotNull { it.photo })

fun ArrayList<Attachment>.photosCount() = getPhotos().size

fun ArrayList<Attachment>?.hasSmthToShow() = this?.photosCount() ?: 0 > 0