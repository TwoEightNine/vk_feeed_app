package global.msnthrp.xvii2.models.attachments

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.ArrayList

@Parcelize
data class Doc(

    @SerializedName("id")
    @Expose
    val id: Int = 0,

    @SerializedName("owner_id")
    val ownerId: Int = 0,

    @SerializedName("title")
    @Expose
    val title: String = "",

    @SerializedName("access_key")
    val accessKey: String = "",

    @SerializedName("ext")
    @Expose
    val ext: String = "",

    @SerializedName("url")
    @Expose
    val url: String? = null,

    @SerializedName("size")
    @Expose
    val size: Int = 0,

    @SerializedName("type")
    @Expose
    val type: Int = 0
) : Parcelable

