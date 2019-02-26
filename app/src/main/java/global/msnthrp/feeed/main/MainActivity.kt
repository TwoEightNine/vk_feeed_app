package global.msnthrp.feeed.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import global.msnthrp.feeed.R
import global.msnthrp.feeed.base.BaseActivity
import global.msnthrp.feeed.main.pager.MainPagerAdapter
import global.msnthrp.feeed.main.pager.MainPagerTransformer
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavView.setOnNavigationItemSelectedListener(BottomViewListener())
        initViewPager()
        bottomNavView.selectedItemId = R.id.menu_news
    }

    private fun initViewPager() {
        with(viewPager) {
            adapter = MainPagerAdapter(supportFragmentManager)
            isLocked = true
//            setPageTransformer(true, MainPagerTransformer())
            offscreenPageLimit = 4
        }
    }

    private inner class BottomViewListener : BottomNavigationView.OnNavigationItemSelectedListener {
        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            viewPager.setCurrentItem(
                when (item.itemId) {
                    R.id.menu_search -> 0
                    R.id.menu_suggestions -> 1
                    R.id.menu_favorites -> 3
                    R.id.menu_settings -> 4
                    else -> 2 // default
                },
                false
            )
            return true
        }
    }

    companion object {
        fun launch(context: Context?) {
            context?.startActivity(Intent(context, MainActivity::class.java))
        }
    }
}