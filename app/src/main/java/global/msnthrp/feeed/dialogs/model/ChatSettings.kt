package global.msnthrp.feeed.dialogs.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ChatSettings(

    @SerializedName("title")
    @Expose
    val title: String,

    @SerializedName("photo")
    @Expose
    val photo: ChatPhoto?
)