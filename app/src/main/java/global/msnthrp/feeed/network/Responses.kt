package global.msnthrp.feeed.network

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

typealias BaseList<T> = BaseResponse<ListResponse<T>>

data class BaseResponse<T>(

    @SerializedName("response")
    @Expose
    val response: T? = null,

    @SerializedName("error")
    @Expose
    val error: Error? = null
)

data class Error(

    @SerializedName("error_code")
    @Expose
    val code: Int = 0,

    @SerializedName("error_msg")
    @Expose
    val message: String? = null

) {
    companion object {
        const val TOO_MANY = 6
        const val CAPTCHA = 14
    }
}

data class ListResponse<T>(

    @SerializedName("items")
    @Expose
    val items: ArrayList<T> = arrayListOf(),

    @SerializedName("count")
    @Expose
    val count: Long = 0L

)