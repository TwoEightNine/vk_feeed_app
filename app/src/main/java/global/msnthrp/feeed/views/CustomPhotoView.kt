package global.msnthrp.feeed.views

import android.content.Context
import android.util.AttributeSet
import com.github.chrisbanes.photoview.PhotoView

class CustomPhotoView : PhotoView {

    constructor(context: Context) : super(context)
    constructor(
        context: Context,
        attributeSet: AttributeSet?
    ) : super(context, attributeSet)

    constructor(
        context: Context,
        attributeSet: AttributeSet?,
        defStyle: Int
    ) : super(context, attributeSet, defStyle)

    init {

    }
}