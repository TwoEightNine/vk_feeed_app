package global.msnthrp.feeed.feed.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import global.msnthrp.feeed.App
import global.msnthrp.feeed.R
import global.msnthrp.feeed.base.BaseFragment
import global.msnthrp.feeed.feed.adapters.FeedAdapter
import global.msnthrp.feeed.feed.model.PostPhoto
import global.msnthrp.feeed.feed.viewmodel.FeedViewModel
import global.msnthrp.feeed.imageviewer.activities.ImageViewerActivity
import global.msnthrp.feeed.models.Wrapper
import global.msnthrp.feeed.prefs.LivePrefs
import global.msnthrp.feeed.utils.showAlert
import kotlinx.android.synthetic.main.fragment_feed.*
import javax.inject.Inject

abstract class FeedFragment : BaseFragment() {

    @Inject
    lateinit var livePrefs: LivePrefs

    @Inject
    lateinit var viewModelFactory: FeedViewModel.Factory
    protected lateinit var viewModel: FeedViewModel

    private val adapter by lazy {
        FeedAdapter(safeContext, ::loadMore, ::onClick)
    }

    abstract fun getViewModelClass(): Class<out FeedViewModel>

    override fun getLayoutId() = R.layout.fragment_feed

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)[getViewModelClass()]
        prepareViewModel()
        viewModel.getFeed().observe(this, Observer { updateUi(it) })
        loadMore()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        App.appComponent.inject(this)
        rvPhotos.layoutManager = GridLayoutManager(context, SPAN_COUNT)
        rvPhotos.adapter = adapter
        swipeRefresh.isRefreshing = true
        swipeRefresh.setOnRefreshListener {
            viewModel.resetFeed()
            loadMore()
        }

        livePrefs.gridColumns.observe(this) { columnsCount ->
            (rvPhotos.layoutManager as? GridLayoutManager)?.spanCount = columnsCount
        }
    }

    protected open fun prepareViewModel() {}

    private fun updateUi(data: Wrapper<ArrayList<PostPhoto>>) {
        swipeRefresh.isRefreshing = false
        if (data.data != null) {
            adapter.update(data.data)
        } else {
            showAlert(context, data.error ?: "unknown error")
        }
    }

    private fun loadMore(pos: Int = 0) {
        viewModel.loadFeed()
    }

    private fun onClick(position: Int, postPhotos: List<PostPhoto>) {
        val posFrom = if (position >= POSITION_OFFSET) position - POSITION_OFFSET else 0
        val posTo = if (position < postPhotos.size - POSITION_OFFSET) position + POSITION_OFFSET else postPhotos.size
        val groupClick = this !is GroupFeedFragment
        ImageViewerActivity.launch(
            context, postPhotos.subList(posFrom, posTo),
            viewModel.wallPosts, groupClick, position - posFrom
        )
    }

    companion object {

        const val POSITION_OFFSET = 30
        const val SPAN_COUNT = 3
    }
}