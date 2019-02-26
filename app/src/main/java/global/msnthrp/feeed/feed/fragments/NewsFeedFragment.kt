package global.msnthrp.feeed.feed.fragments

import global.msnthrp.feeed.feed.viewmodel.NewsFeedViewModel

class NewsFeedFragment : FeedFragment() {

    override fun getViewModelClass() = NewsFeedViewModel::class.java

    companion object {
        fun newInstance() = NewsFeedFragment()
    }
}