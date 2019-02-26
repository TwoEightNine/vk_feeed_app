package global.msnthrp.feeed.main.pager

import android.view.View
import androidx.viewpager.widget.ViewPager

class MainPagerTransformer : ViewPager.PageTransformer {

    override fun transformPage(page: View, position: Float) {
        page.translationX = page.width * -position
        page.alpha = when {
            position <= -1f || position >= 1f -> 0f
            position == 0f -> 1f
            else -> 1f - Math.abs(position)
        }
    }
}