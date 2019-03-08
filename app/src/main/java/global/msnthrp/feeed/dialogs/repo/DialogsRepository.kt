package global.msnthrp.feeed.dialogs.repo

import android.content.Context
import global.msnthrp.feeed.dialogs.model.Chat
import global.msnthrp.feeed.models.owner.Group
import global.msnthrp.feeed.models.owner.Owner
import global.msnthrp.feeed.models.owner.User
import global.msnthrp.feeed.network.ApiService
import global.msnthrp.feeed.storage.Session
import global.msnthrp.feeed.utils.subscribeSmart
import javax.inject.Inject
import kotlin.random.Random

class DialogsRepository @Inject constructor(
    private val api: ApiService,
    private val session: Session,
    private val context: Context
) {

    fun loadDialogs(
        onSuccess: (ArrayList<Owner>) -> Unit,
        onError: (String) -> Unit
    ) {
        api.getDialogs()
            .subscribeSmart({
                val owners = it.getDialogOwners()
                owners.add(0, User.createOwn(context, session))
                onSuccess(owners)
            }, { _, error ->
                onError(error)
            })
    }

    fun send(
        owner: Owner,
        message: String,
        attachment: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val peerId = when (owner) {
            is User -> owner.id
            is Group -> -owner.id
            is Chat -> owner.peerId
            else -> return
        }
        api.send(peerId, message, attachment, getRandomId())
            .subscribeSmart({
                onSuccess()
            }, { _, error ->
                onError(error)
            })

    }

    private fun getRandomId() = Random.nextInt()
}