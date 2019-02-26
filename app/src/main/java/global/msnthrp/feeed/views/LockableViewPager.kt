package global.msnthrp.feeed.views

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException

class LockableViewPager : ViewPager {

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet)

    var isLocked = false

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return try {
            !isLocked && super.onTouchEvent(event)
        } catch (e: IllegalStateException) {
            e.printStackTrace()
            false
        }
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return try {
            !isLocked && super.onInterceptTouchEvent(event)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
            false
        }
    }

    override fun canScrollHorizontally(direction: Int): Boolean {
        return !isLocked && super.canScrollHorizontally(direction)
    }


}