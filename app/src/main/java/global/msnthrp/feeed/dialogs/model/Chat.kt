package global.msnthrp.feeed.dialogs.model

import android.os.Parcelable
import global.msnthrp.feeed.models.owner.Owner
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Chat(
    val peerId: Int,
    val chatTitle: String,
    val chatPhoto: String?
) : Parcelable, Owner {

    override fun getTitle() = chatTitle

    override fun getPhoto() = chatPhoto

    companion object {
        fun fromConversation(conversation: Conversation) = Chat(
            conversation.peer.id,
            conversation.chatSettings.title,
            conversation.chatSettings.photo?.photo100
        )
    }
}