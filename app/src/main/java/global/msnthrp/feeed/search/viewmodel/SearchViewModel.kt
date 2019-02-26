package global.msnthrp.feeed.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import global.msnthrp.feeed.models.WrappedLiveData
import global.msnthrp.feeed.models.WrappedMutableLiveData
import global.msnthrp.feeed.models.Wrapper
import global.msnthrp.feeed.models.owner.Group
import global.msnthrp.feeed.search.repo.SearchRepository
import global.msnthrp.feeed.storage.Session
import javax.inject.Inject

class SearchViewModel(private val repo: SearchRepository) : ViewModel() {

    private val groupsLiveData = WrappedMutableLiveData<ArrayList<Group>>()

    fun getGroups() = groupsLiveData as WrappedLiveData<ArrayList<Group>>

    fun searchGroups(query: String) {
        if (query.isEmpty()) {
            repo.getGroups(::onGroupsLoaded, ::onErrorOccurred)
        } else {
            repo.searchGroups(query, ::onGroupsLoaded, ::onErrorOccurred)
        }
    }

    private fun onGroupsLoaded(groups: ArrayList<Group>) {
        groupsLiveData.value = Wrapper(groups)
    }

    private fun onErrorOccurred(error: String) {
        groupsLiveData.value = Wrapper(error = error)
    }

    class Factory @Inject constructor(private val repo: SearchRepository) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>) = SearchViewModel(repo) as T
    }
}