package global.msnthrp.feeed.utils

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ClipboardManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.Intent.*
import android.net.ConnectivityManager
import android.net.Uri
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import android.graphics.drawable.Drawable
import com.squareup.picasso.Picasso
import android.graphics.Bitmap
import android.os.Build
import android.os.Handler
import com.squareup.picasso.Target
import global.msnthrp.feeed.App
import global.msnthrp.feeed.R
import global.msnthrp.feeed.storage.Lg
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception
import java.io.IOException
import java.io.RandomAccessFile
import java.text.DecimalFormat
import java.util.regex.Pattern


fun l(s: String) = Lg.i(s)

fun isOnline(context: Context): Boolean {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return cm.activeNetworkInfo?.isConnectedOrConnecting ?: false
}

fun <T> applySchedulersSingle() = { single: Single<T> ->
    single
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}

fun callIntent(context: Context?, number: String) {
    number.apply {
        replace("-", "")
        replace(" ", "")
        context?.startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:$this")))
    }
}

fun copyToClip(context: Context?, text: String) {
    val clipboard = context?.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
    clipboard?.text = text
}

fun getNameFromUrl(url: String) = url.split("/").last().split("?").first()

fun shareImage(context: Context?, url: String) {
    if (context == null) return

    Picasso.get().load(url).into(object : Target {
        override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {
            val i = Intent(Intent.ACTION_SEND).apply {
                type = "image/png"
                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                putExtra(Intent.EXTRA_STREAM, saveBitmap(bitmap))
            }
            context.startActivity(Intent.createChooser(i, context.getString(R.string.share_image)))
        }

        override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {}

        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}

        private fun saveBitmap(bmp: Bitmap): Uri? {
            var bmpUri: Uri? = null
            try {
                val file = File(context.externalCacheDir, "${System.currentTimeMillis()}.png")
                val out = FileOutputStream(file)
                bmp.compress(Bitmap.CompressFormat.PNG, 90, out)
                out.close()
                bmpUri = Uri.fromFile(file)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return bmpUri
        }

    })
}

fun shareText(context: Context?, text: String) {
    context ?: return

    val sharingIntent = Intent(android.content.Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(android.content.Intent.EXTRA_TEXT, text)
    }
    context.startActivity(Intent.createChooser(sharingIntent, context.getString(R.string.share_post)))
}

fun time() = (System.currentTimeMillis() / 1000L).toInt()

fun isDev(userId: Int) = App.ID_SALTS
    .map { md5(userId.toString() + it) }
    .filterIndexed { index, hash -> hash == App.ID_HASHES[index] }
    .isNotEmpty()

fun isChat(peerId: Int) = peerId > 2000000000
fun isGroup(peerId: Int) = peerId < 0
fun isUser(peerId: Int) = peerId in 1..2000000000

fun goHome(context: Context?) {
    context?.startActivity(Intent(Intent.ACTION_MAIN).apply {
        addCategory(Intent.CATEGORY_HOME)
        flags = Intent.FLAG_ACTIVITY_NEW_TASK
    })
}

fun getTotalRAM(): String {

    val reader: RandomAccessFile?
    val load: String?
    val twoDecimalForm = DecimalFormat("#.##")
    val totRam: Double
    var ram = ""
    try {
        reader = RandomAccessFile("/proc/meminfo", "r")
        load = reader.readLine()

        // Get the Number value from the string
        val p = Pattern.compile("(\\d+)")
        val m = p.matcher(load)
        var value = ""
        while (m.find()) {
            value = m.group(1)
        }
        reader.close()

        totRam = java.lang.Double.parseDouble(value) / 1024.0
        ram = twoDecimalForm.format(totRam) + " MB"

    } catch (ex: IOException) {
        ex.printStackTrace()
    } finally {
        // Streams.close(reader);
    }
    return ram
}

fun restartApp(context: Context?, title: String) {
    showToast(context, title)
    Handler().postDelayed({ restartApp(context) }, 800L)
}

fun restartApp(context: Context?) {
    context ?: return

    val mStartActivity = getRestartIntent(context)
    val mPendingIntentId = 123456
    val mPendingIntent =
        PendingIntent.getActivity(context, mPendingIntentId, mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT)
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    alarmManager.set(AlarmManager.RTC, System.currentTimeMillis() + 500, mPendingIntent)

    val notificationManager = context.getSystemService(
        Context.NOTIFICATION_SERVICE
    ) as NotificationManager
    notificationManager.cancelAll()
    System.exit(0)
}

fun getRestartIntent(context: Context): Intent {
    val defaultIntent = Intent(ACTION_MAIN, null)
    defaultIntent.addFlags(FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK)
    defaultIntent.addCategory(CATEGORY_LAUNCHER)

    val packageName = context.packageName
    val packageManager = context.packageManager
    for (resolveInfo in packageManager.queryIntentActivities(defaultIntent, 0)) {
        val activityInfo = resolveInfo.activityInfo
        if (activityInfo.packageName == packageName) {
            defaultIntent.component = ComponentName(packageName, activityInfo.name)
            return defaultIntent
        }
    }

    throw IllegalStateException(
        "Unable to determine default activity for "
                + packageName
                + ". Does an activity specify the DEFAULT category in its intent filter?"
    )
}
