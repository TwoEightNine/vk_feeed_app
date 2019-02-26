package global.msnthrp.feeed.feed.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import global.msnthrp.feeed.base.ContentActivity
import global.msnthrp.feeed.feed.fragments.GroupFeedFragment
import global.msnthrp.feeed.models.owner.Group

class GroupActivity : ContentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadPage(GroupFeedFragment.newInstance(intent.extras ?: return))
    }

    companion object {
        fun launch(context: Context?, group: Group) {
            context?.startActivity(Intent(context, GroupActivity::class.java).apply {
                putExtra(GroupFeedFragment.ARG_GROUP, group)
            })
        }
    }
}