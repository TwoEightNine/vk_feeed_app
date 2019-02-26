package global.msnthrp.feeed.dialogs.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import global.msnthrp.feeed.dialogs.repo.DialogsRepository
import global.msnthrp.feeed.models.WrappedLiveData
import global.msnthrp.feeed.models.WrappedMutableLiveData
import global.msnthrp.feeed.models.Wrapper
import global.msnthrp.feeed.models.attachments.Attachment
import global.msnthrp.feeed.models.owner.Owner
import javax.inject.Inject

class DialogsViewModel(private val repo: DialogsRepository) : ViewModel() {

    private val dialogsLiveData = WrappedMutableLiveData<ArrayList<Owner>>()
    private val sentLiveData = WrappedMutableLiveData<Boolean>()

    fun getDialogs() = dialogsLiveData as WrappedLiveData<ArrayList<Owner>>

    fun getSent() = sentLiveData as WrappedLiveData<Boolean>

    fun loadDialogs() {
        repo.loadDialogs(::onDialogsLoaded, ::onErrorOccurred)
    }

    fun share(owner: Owner, attachment: String, comment: String) {
        repo.send(owner, comment, attachment, ::onShared, ::onErrorOccurred)
    }

    private fun onShared() {
        sentLiveData.value = Wrapper(true)
    }

    private fun onDialogsLoaded(owners: ArrayList<Owner>) {
        dialogsLiveData.value = Wrapper(owners)
    }

    private fun onErrorOccurred(error: String) {
        dialogsLiveData.value = Wrapper(error = error)
    }

    class Factory @Inject constructor(private val repo: DialogsRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>) = DialogsViewModel(repo) as T
    }
}