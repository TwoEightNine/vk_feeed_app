package global.msnthrp.feeed.search.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import global.msnthrp.feeed.App
import global.msnthrp.feeed.R
import global.msnthrp.feeed.base.BaseFragment
import global.msnthrp.feeed.feed.activities.GroupActivity
import global.msnthrp.feeed.models.Wrapper
import global.msnthrp.feeed.models.owner.Group
import global.msnthrp.feeed.search.adapters.SearchAdapter
import global.msnthrp.feeed.search.viewmodel.SearchViewModel
import global.msnthrp.feeed.utils.hideKeyboard
import global.msnthrp.feeed.utils.showAlert
import global.msnthrp.feeed.utils.subscribeSearch
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.view_search.*
import javax.inject.Inject

class SearchFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: SearchViewModel.Factory
    private lateinit var viewModel: SearchViewModel

    private val adapter by lazy {
        SearchAdapter(safeContext, ::onClick)
    }

    override fun getLayoutId() = R.layout.fragment_search

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvSearch.layoutManager = LinearLayoutManager(context)
        rvSearch.adapter = adapter
        rvSearch.setOnTouchListener { _, _ ->
            activity?.let { hideKeyboard(it) }
            false
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        App.appComponent.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory)[SearchViewModel::class.java]
        viewModel.getGroups().observe(this, Observer { updateUi(it) })
        etSearch.subscribeSearch(true, viewModel::searchGroups)
        ivDelete.setOnClickListener { etSearch.setText("") }
    }

    private fun updateUi(data: Wrapper<ArrayList<Group>>) {
        if (data.data != null) {
            adapter.update(data.data)
        } else {
            showAlert(context, data.error ?: "unknown error")
        }
    }

    private fun onClick(group: Group) {
        GroupActivity.launch(context, group)
    }

    companion object {
        fun newInstance() = SearchFragment()
    }
}