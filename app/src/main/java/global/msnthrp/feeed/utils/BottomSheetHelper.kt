package global.msnthrp.feeed.utils

import com.google.android.material.bottomsheet.BottomSheetBehavior

class BottomSheetHelper(private val bottomSheetBehavior: BottomSheetBehavior<*>) {

    fun open() {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    fun close() {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    fun isOpen() = bottomSheetBehavior.state != BottomSheetBehavior.STATE_COLLAPSED

}