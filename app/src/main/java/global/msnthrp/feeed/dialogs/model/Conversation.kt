package global.msnthrp.feeed.dialogs.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Conversation(

    @SerializedName("peer")
    @Expose
    val peer: Peer,

    @SerializedName("chat_settings")
    @Expose
    val chatSettings: ChatSettings,

    @SerializedName("can_write")
    @Expose
    val canWrite: CanWrite
)