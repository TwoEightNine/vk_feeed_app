package global.msnthrp.feeed.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import global.msnthrp.feeed.utils.setVisible

/**
 * Created by msnthrp on 22/01/18.
 */

abstract class BaseAdapter<T, VH : RecyclerView.ViewHolder>(
    protected val context: Context
) : RecyclerView.Adapter<VH>() {

    protected val items: ArrayList<T> = arrayListOf()

    protected val inflater = LayoutInflater.from(context)

    var emptyView: View? = null

    /**
     * invokes:
     *  - true when becomes non-empty
     *  - false when becomes empty
     */
    var multiListener: ((Boolean) -> Unit)? = null

    /**
     * selected items
     */
    private val multiSelectRaw: ArrayList<T> = arrayListOf()

    val isEmpty: Boolean
        get() = items.isEmpty()

    fun add(item: T, pos: Int) {
        items.add(pos, item)
        notifyItemInserted(pos)
        invalidateEmptyView()
    }

    open fun add(item: T) {
        add(item, items.size)
    }

    @JvmOverloads
    fun addAll(items: List<T>, pos: Int = this.items.size) {
        val size = items.size
        this.items.addAll(pos, items)
        notifyItemRangeInserted(pos, size)
        invalidateEmptyView()
    }

    fun removeAt(pos: Int): T {
        val removed = items.removeAt(pos)
        notifyItemRemoved(pos)
        invalidateEmptyView()
        return removed
    }

    fun remove(obj: T): Boolean {
        val pos = items.indexOf(obj)
        if (pos !in IntRange(0, items.size - 1)) {
            return false
        }
        removeAt(pos)
        return true
    }

    fun update(pos: Int, item: T): T {
        val oldItem = items[pos]
        items[pos] = item
        notifyItemChanged(pos)
        return oldItem
    }

    open fun clear() {
        items.clear()
        notifyDataSetChanged()
        invalidateEmptyView()
    }

    open fun update(items: List<T>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
        invalidateEmptyView()
    }

    fun itemsCopy() = ArrayList(items)

    override fun getItemCount() = items.size

    fun lastVisiblePosition(layoutManager: RecyclerView.LayoutManager?) = when (layoutManager) {
        is LinearLayoutManager -> layoutManager.findLastVisibleItemPosition()
        is GridLayoutManager -> layoutManager.findLastVisibleItemPosition()
        else -> -1
    }


    fun firstVisiblePosition(layoutManager: RecyclerView.LayoutManager?) = when (layoutManager) {
        is LinearLayoutManager -> layoutManager.findFirstVisibleItemPosition()
        is GridLayoutManager -> layoutManager.findFirstVisibleItemPosition()
        else -> -1
    }

    fun isAtTop(layoutManager: LinearLayoutManager?) = firstVisiblePosition(layoutManager) == 0

    fun isAtBottom(layoutManager: LinearLayoutManager?) = lastVisiblePosition(layoutManager) == items.size - 1

    private fun invalidateEmptyView() {
        emptyView?.setVisible(items.isEmpty())
    }

    /**
     * toggle selection: add or remove
     */
    fun toggleSelection(item: T) {
        if (multiSelectRaw.contains(item)) {
            multiSelectRaw.remove(item)
        } else {
            multiSelectRaw.add(item)
        }
        notifyMultiSelect()
        val position = items.indexOf(item)
        if (position != -1) {
            notifyItemChanged(position)
        }
    }

    open fun notifyMultiSelect() {
        if (multiSelectRaw.size == 0) {
            multiListener?.invoke(false)
        } else if (multiSelectRaw.size == 1) {
            multiListener?.invoke(true)
        }
    }

    fun clearMultiSelect() {
        multiSelectRaw.clear()
        multiListener?.invoke(false)
        notifyDataSetChanged()
    }

}