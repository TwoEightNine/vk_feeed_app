package global.msnthrp.feeed.dialogs.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Dialog(

    @SerializedName("conversation")
    @Expose
    val conversation: Conversation
)