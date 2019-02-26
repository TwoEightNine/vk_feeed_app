package global.msnthrp.feeed.search.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import global.msnthrp.feeed.R
import global.msnthrp.feeed.base.BaseAdapter
import global.msnthrp.feeed.models.owner.Group
import global.msnthrp.feeed.utils.load
import kotlinx.android.synthetic.main.item_search.view.*

class SearchAdapter(
    context: Context,
    private val onClick: (Group) -> Unit
) : BaseAdapter<Group, SearchAdapter.SearchViewHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        SearchViewHolder(inflater.inflate(R.layout.item_search, null))

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class SearchViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(group: Group) {
            with(itemView) {
                ivPhoto.load(group.photo100)
                tvTitle.text = group.name
                setOnClickListener { onClick(items[adapterPosition]) }
            }
        }
    }
}