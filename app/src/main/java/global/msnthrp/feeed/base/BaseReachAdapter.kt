package global.msnthrp.feeed.base

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import global.msnthrp.feeed.R
import global.msnthrp.feeed.base.BaseAdapter


/**
 * provides pagination functionality
 * [loader] is being invoked when there is a need to load more items. Int param is an offset
 *
 * usage:
 *  - [BaseReachAdapter.startLoading]
 *  - // load smth
 *  - [BaseReachAdapter.update] or [BaseReachAdapter.stopLoading]
 *  - on reload call [BaseReachAdapter.resetDone]
 */
abstract class BaseReachAdapter<T : Any, VH : RecyclerView.ViewHolder> constructor(
    context: Context,
    private var loader: (Int) -> Unit
) : BaseAdapter<T, RecyclerView.ViewHolder>(context) {

    /**
     * indicates the adapter is showing loader
     */
    private var isLoaderAdded: Boolean = false

    /**
     * indicated the adapter is in loading state
     */
    private var isLoading: Boolean = false

    /**
     * indicates the adapter obtained all data (no more loadings)
     */
    private var isDone: Boolean = false

    private lateinit var stubLoadItem: T

    abstract fun createHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder

    abstract fun bind(holder: VH, item: T)

    abstract fun createStubLoadItem(): T

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        stubLoadItem = createStubLoadItem()
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy <= 0)
                    return

                val total = itemCount
                val last = lastVisiblePosition(recyclerView.layoutManager)
                if (!isDone && !isLoading && last >= total - THRESHOLD) {
                    loader.invoke(total)
                    startLoading()
                }
            }
        })
    }

    override fun clear() {
        super.clear()
        isDone = false
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) =
        when (viewType) {
            STUB_LOAD -> LoaderViewHolder(inflater.inflate(R.layout.item_loading, parent, false))
            else -> createHolder(parent, viewType)
        }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        if (!isStubLoad(item)) {
            bind(holder as VH, item)
        }
    }

    override fun getItemViewType(position: Int) = when {
        isStubLoad(items[position]) -> STUB_LOAD
        else -> NO_STUB
    }

    private fun isStubLoad(obj: T) = stubLoadItem === obj

    /**
     * adds loader to RecyclerView
     */
    fun startLoading() {
        isLoading = true
        addStubLoad()
    }

    /**
     * restarts loading
     */
    fun resetDone() {
        isDone = false
    }

    private fun addStubLoad() {
        if (!isLoaderAdded) {
            add(stubLoadItem)
            isLoaderAdded = true
        }
    }

    /**
     * manipulates the states
     * pass empty list to stop loading
     */
    override fun update(items: List<T>) {
        removeStub()
        val noChanges = itemCount == items.size
        super.update(items)
        isLoading = false
        if (noChanges) {
            isDone = true
        }
    }

    private fun removeStub() {
        remove(stubLoadItem)
        isLoaderAdded = false
    }

    private fun stopLoading() {
        isLoading = false
        removeStub()
    }


    inner class LoaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    companion object {

        const val THRESHOLD = 5

        const val STUB_LOAD = 134
        const val NO_STUB = 135
    }
}