package global.msnthrp.feeed.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import global.msnthrp.feeed.R

@SuppressLint("Registered")
open class ContentActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_container)
    }

    protected fun loadPage(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.content_frame, fragment)
            .commit()
    }

    override fun onOptionsItemSelected(item: MenuItem?) = when(item?.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}