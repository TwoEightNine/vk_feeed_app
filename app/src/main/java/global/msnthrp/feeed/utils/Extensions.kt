package global.msnthrp.feeed.utils

import android.os.SystemClock
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding.widget.RxTextView
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import global.msnthrp.feeed.network.BaseResponse
import global.msnthrp.feeed.network.Error
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import rx.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

fun <T : View> AppCompatActivity.view(@IdRes viewId: Int) = lazy { findViewById<T>(viewId) }

fun <T : View> AlertDialog.view(@IdRes viewId: Int) = lazy { findViewById<T>(viewId)!! }

/** Binds views by its [viewId] */
fun <T : View> RecyclerView.ViewHolder.view(@IdRes viewId: Int) = lazy { itemView.findViewById<T>(viewId) }

/** Returns true if [RecyclerView] is positioned at the bottom (last view is visible) */
fun RecyclerView.isAtEnd(totalCount: Int) =
    (layoutManager as LinearLayoutManager).findLastVisibleItemPosition() == totalCount - 1

fun <T> Single<BaseResponse<T>>.subscribeSmart(
    onSuccess: (T) -> Unit,
    onError: (Int, String) -> Unit,
    onNetworkError: (String) -> Unit = { onError(0, it) }
): Disposable {
    return this.compose(applySchedulersSingle())
        .subscribe({ resp ->
            if (resp.response != null) {
                onSuccess(resp.response)
            } else if (resp.error != null) {
                val errorMsg = resp.error.message
                val errCode = resp.error.code
                when (errCode) {
                    Error.TOO_MANY -> {
                        Thread {
                            SystemClock.sleep(330)
                            this.subscribeSmart(onSuccess, onError)
                        }.start()
                    }
                    else -> onError.invoke(errCode, errorMsg ?: "null")
                }

            }
        }, {
            onNetworkError.invoke(it.message ?: "Unknown error")
        })
}

fun View.setVisible(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}

fun View.getVisible() = visibility == View.VISIBLE

fun View.toggle() {
    setVisible(!getVisible())
}

fun View.show() = setVisible(true)

fun View.hide() = setVisible(false)

fun ImageView.load(url: String?, block: RequestCreator.() -> RequestCreator = { this }) {
    Picasso.get()
        .load(url)
        .block()
        .into(this)
}

fun TextView.subscribeSearch(
    allowEmpty: Boolean,
    onNext: (String) -> Unit
) = RxTextView.textChanges(this)
    .filter { allowEmpty || it.isNotBlank() }
    .debounce(300, TimeUnit.MILLISECONDS)
    .observeOn(AndroidSchedulers.mainThread())
    .map { it.toString() }
    .subscribe(onNext) {
        it.printStackTrace()
    }

fun EditText.asText() = text.toString()

fun EditText.clear() {
    setText("")
}