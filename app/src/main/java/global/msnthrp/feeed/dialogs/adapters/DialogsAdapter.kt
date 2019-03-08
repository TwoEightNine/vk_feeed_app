package global.msnthrp.feeed.dialogs.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import global.msnthrp.feeed.R
import global.msnthrp.feeed.base.BaseAdapter
import global.msnthrp.feeed.models.owner.Owner
import global.msnthrp.feeed.models.owner.User
import global.msnthrp.feeed.utils.load
import kotlinx.android.synthetic.main.item_owner.view.*

class DialogsAdapter(
    context: Context,
    private val onClick: (Owner) -> Unit
) : BaseAdapter<Owner, DialogsAdapter.OwnerViewHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        OwnerViewHolder(inflater.inflate(R.layout.item_owner, null))

    override fun onBindViewHolder(holder: OwnerViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class OwnerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(owner: Owner) {
            with(itemView) {
                if (owner is User && owner.myself) {
                    ivPhoto.setImageResource(R.drawable.ic_like_red)
                } else {
                    ivPhoto.load(owner.getPhoto()) {
                        placeholder(R.drawable.shape_placeholder)
                    }
                }
                tvTitle.text = owner.getTitle().toLowerCase()
                setOnClickListener { onClick(items[adapterPosition]) }
            }
        }
    }
}