package global.msnthrp.feeed.main.pager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import global.msnthrp.feeed.dialogs.fragments.DialogsFragment
import global.msnthrp.feeed.feed.fragments.FavoritesFeedFragment
import global.msnthrp.feeed.feed.fragments.NewsFeedFragment
import global.msnthrp.feeed.feed.fragments.SuggestionsFeedFragment
import global.msnthrp.feeed.search.fragments.SearchFragment
import global.msnthrp.feeed.settings.SettingsFragment

class MainPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    private val fragments = arrayListOf<Fragment>()

    init {
        fragments.apply {
            add(SearchFragment.newInstance())
            add(SuggestionsFeedFragment.newInstance())
            add(NewsFeedFragment.newInstance())
            add(FavoritesFeedFragment.newInstance())
            add(SettingsFragment.newInstance())
        }
    }

    override fun getCount() = fragments.size

    override fun getItem(position: Int) = fragments[position]
}