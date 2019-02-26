package global.msnthrp.feeed.feed.fragments

import global.msnthrp.feeed.feed.viewmodel.FavoritesFeedViewModel
import global.msnthrp.feeed.feed.viewmodel.FeedViewModel

class FavoritesFeedFragment : FeedFragment() {

    override fun getViewModelClass() = FavoritesFeedViewModel::class.java

    companion object {
        fun newInstance() = FavoritesFeedFragment()
    }
}