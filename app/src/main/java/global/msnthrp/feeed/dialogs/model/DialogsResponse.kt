package global.msnthrp.feeed.dialogs.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import global.msnthrp.feeed.models.owner.Group
import global.msnthrp.feeed.models.owner.Owner
import global.msnthrp.feeed.models.owner.User

data class DialogsResponse(

    @SerializedName("items")
    @Expose
    val items: ArrayList<Dialog>,

    @SerializedName("profiles")
    @Expose
    val profiles: ArrayList<User>,

    @SerializedName("groups")
    @Expose
    val groups: ArrayList<Group>
) {
    fun getDialogOwners(): ArrayList<Owner> {
        val result = arrayListOf<Owner>()
        items.filter { it.conversation.canWrite.allowed }
            .forEach { dialog ->
                val id = dialog.conversation.peer.id
                val owner = when (dialog.conversation.peer.type) {
                    Peer.USER -> profiles.firstOrNull { it.id == id }
                    Peer.GROUP -> groups.firstOrNull { it.id == -id }
                    Peer.CHAT -> Chat.fromConversation(dialog.conversation)
                    else -> null
                }
                if (owner != null) {
                    result.add(owner)
                }
            }
        return result
    }
}