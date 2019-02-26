package global.msnthrp.feeed.feed.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import global.msnthrp.feeed.R
import global.msnthrp.feeed.base.BaseAdapter
import global.msnthrp.feeed.base.BaseReachAdapter
import global.msnthrp.feeed.feed.model.PostPhoto
import global.msnthrp.feeed.feed.model.WallPost
import global.msnthrp.feeed.models.attachments.Photo
import global.msnthrp.feeed.utils.load
import kotlinx.android.synthetic.main.item_post_photo.view.*

class FeedAdapter(
    context: Context,
    loader: (Int) -> Unit,
    private val onClick: (Int, List<PostPhoto>) -> Unit
) : BaseReachAdapter<PostPhoto, FeedAdapter.PhotoPostViewHolder>(context, loader) {

    override fun createHolder(parent: ViewGroup, viewType: Int)
            = PhotoPostViewHolder(inflater.inflate(R.layout.item_post_photo, null))

    override fun bind(holder: PhotoPostViewHolder, item: PostPhoto) {
        holder.bind(item)
    }

    override fun createStubLoadItem() = PostPhoto(Photo(), "")

    inner class PhotoPostViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(postPhoto: PostPhoto) {
            with(itemView) {
                ivPhoto.load(postPhoto.photo.getMediumPhoto().url) {
                    placeholder(R.drawable.shape_placeholder)
                }
                setOnClickListener { onClick(adapterPosition, itemsCopy()) }
            }
        }
    }
}