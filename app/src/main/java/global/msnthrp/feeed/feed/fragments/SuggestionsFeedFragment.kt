package global.msnthrp.feeed.feed.fragments

import global.msnthrp.feeed.feed.viewmodel.SuggestionsFeedViewModel

class SuggestionsFeedFragment : FeedFragment() {

    override fun getViewModelClass() = SuggestionsFeedViewModel::class.java

    companion object {
        fun newInstance() = SuggestionsFeedFragment()
    }
}