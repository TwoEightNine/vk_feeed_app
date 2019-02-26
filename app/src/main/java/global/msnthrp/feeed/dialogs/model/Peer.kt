package global.msnthrp.feeed.dialogs.model

data class Peer(
    val id: Int,
    val type: String
) {
    companion object {
        const val USER = "user"
        const val CHAT = "chat"
        const val GROUP = "group"
    }
}