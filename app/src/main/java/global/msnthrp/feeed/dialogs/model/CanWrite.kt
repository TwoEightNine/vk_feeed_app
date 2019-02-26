package global.msnthrp.feeed.dialogs.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CanWrite(

    @SerializedName("allowed")
    @Expose
    val allowed: Boolean
)