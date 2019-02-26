package global.msnthrp.feeed.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import java.text.SimpleDateFormat
import java.util.*

fun showToast(context: Context?, text: String = "", duration: Int = Toast.LENGTH_SHORT) {
    if (context == null) return

    Toast.makeText(context, text, duration).show()
}

fun showToast(context: Context?, @StringRes textId: Int, duration: Int = Toast.LENGTH_SHORT) {
    showToast(context, context?.getString(textId) ?: "", duration)
}

fun hideKeyboard(activity: Activity) {
    val view = activity.currentFocus
    if (view != null) {
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun showAlert(context: Context?, text: String?) {
    if (context == null) return

    val dialog = AlertDialog.Builder(context)
        .setMessage(text)
        .setPositiveButton(android.R.string.ok, null)
        .create()
    dialog.show()
    Styler.dialog(dialog)
}

fun showConfirm(context: Context?, text: String, callback: (Boolean) -> Unit) {
    if (context == null) return

    val dialog = AlertDialog.Builder(context)
        .setMessage(text)
        .setPositiveButton(android.R.string.ok) { _, _ -> callback.invoke(true) }
        .setNegativeButton(android.R.string.cancel) { _, _ -> callback.invoke(false) }
        .create()
    dialog.show()
    Styler.dialog(dialog)
}

@SuppressLint("SimpleDateFormat")
fun getTime(ts: Int, full: Boolean = false, onlyTime: Boolean = false, format: String? = null): String {
    val date = Date(ts * 1000L)
    val today = Date()
    if (format != null) {
        return SimpleDateFormat(format).format(date)
    }
    val fmt = when {
        onlyTime || today.day == date.day &&
                today.month == date.month &&
                today.year == date.year -> "HH:mm"
        full && today.year == date.year -> "HH:mm dd MMM"
        full -> "HH:mm dd MMM yyyy"
        today.year == date.year -> "dd MMM"
        else -> "dd MMM yyyy"
    }
    return SimpleDateFormat(fmt).format(date)
}

@SuppressLint("SimpleDateFormat")
fun getDate(ts: Int): String {
    val date = Date(ts * 1000L)
    val today = Date()
    val fmt = when {
        today.year == date.year -> "dd MMMM"
        else -> "dd MMMM yyyy"
    }
    return SimpleDateFormat(fmt).format(date)
}

fun secToTime(seconds: Int): String {
    val min = seconds / 60
    val sec = seconds % 60
    val minStr = "${if (min <= 9) "0" else ""}$min"
    val secStr = "${if (sec <= 9) "0" else ""}$sec"
    return "$minStr:$secStr"
}

fun shortified(value: Int): String = when {
    value > 1000000 -> {
        val mod = value % 1000000 / 100000
        var num = "${value / 1000000}"
        if (mod > 0) {
            num += ".$mod"
        }
        num += "M"
        num
    }
    value > 10000 -> "${value / 1000}k"
    value > 1000 -> {
        val mod = value % 1000 / 100
        var num = "${value / 1000}"
        if (mod > 0) {
            num += ".$mod"
        }
        num += "k"
        num
    }
    else -> value.toString()
}

fun rate(context: Context?) {
    val uri = Uri.parse("market://details?id=" + context?.packageName)
    val goToMarket = Intent(Intent.ACTION_VIEW, uri)
    goToMarket.addFlags(
        Intent.FLAG_ACTIVITY_NO_HISTORY or
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK
    )
    try {
        context?.startActivity(goToMarket)
    } catch (e: ActivityNotFoundException) {
        context?.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=" + context.packageName)
            )
        )
    }
}

fun simpleUrlIntent(context: Context?, url: String?) {
    var url = url ?: return
    if (!url.startsWith("http://") && !url.startsWith("https://")) {
        url = "http://$url"
    }
    context?.startActivity(Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse(url)
    })
}

/**
 * fucking vk format allows "d.M.yyyy" WTF???
 */
fun formatBdate(bdate: String?) = bdate
    ?.split(".")
    ?.map {
        if (it.length == 1) "0$it" else it
    }
    ?.joinToString(".") ?: ""
