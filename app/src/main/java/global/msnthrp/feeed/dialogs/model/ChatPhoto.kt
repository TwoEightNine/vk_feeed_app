package global.msnthrp.feeed.dialogs.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ChatPhoto(

    @SerializedName("photo_100")
    @Expose
    val photo100: String
)