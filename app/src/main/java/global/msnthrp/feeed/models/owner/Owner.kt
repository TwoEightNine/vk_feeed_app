package global.msnthrp.feeed.models.owner

import android.os.Parcelable

interface Owner : Parcelable {
    fun getTitle(): String
    fun getPhoto(): String?
}