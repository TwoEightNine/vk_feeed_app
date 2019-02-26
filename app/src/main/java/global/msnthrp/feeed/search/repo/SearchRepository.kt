package global.msnthrp.feeed.search.repo

import global.msnthrp.feeed.models.owner.Group
import global.msnthrp.feeed.network.ApiService
import global.msnthrp.feeed.storage.Session
import global.msnthrp.feeed.utils.subscribeSmart
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val api: ApiService,
    private val session: Session
) {

    fun getGroups(
        onSuccess: (ArrayList<Group>) -> Unit,
        onError: (String) -> Unit
    ) {
        api.getGroups(session.userId)
            .subscribeSmart({ listResponse ->
                onSuccess(listResponse.items)
            }, { _, error ->
                onError(error)
            })
    }

    fun searchGroups(
        query: String,
        onSuccess: (ArrayList<Group>) -> Unit,
        onError: (String) -> Unit
    ) {
        api.searchGroups(query)
            .subscribeSmart({ listResponse ->
                onSuccess(listResponse.items)
            }, { _, error ->
                onError(error)
            })

    }
}