package global.msnthrp.feeed.dialogs.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import global.msnthrp.feeed.base.ContentActivity
import global.msnthrp.feeed.dialogs.fragments.DialogsFragment
import global.msnthrp.feeed.feed.model.WallPost
import global.msnthrp.feeed.models.attachments.Photo

class DialogsActivity : ContentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadPage(DialogsFragment.newInstance(intent.extras ?: return))
    }

    companion object {
        fun launch(context: Context?, wallPost: WallPost) {
            launch(context, wallPost.toString())
        }

        fun launch(context: Context?, photo: Photo) {
            launch(context, photo.toString())
        }

        private fun launch(context: Context?, attachment: String) {
            context?.startActivity(Intent(context, DialogsActivity::class.java).apply {
                putExtra(DialogsFragment.ARG_ATTACHMENT, attachment)
            })
        }
    }
}