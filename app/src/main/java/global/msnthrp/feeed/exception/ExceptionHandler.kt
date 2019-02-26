package global.msnthrp.feeed.exception

import android.content.Context
import android.os.Build
import global.msnthrp.feeed.BuildConfig
import global.msnthrp.feeed.storage.Lg
import global.msnthrp.feeed.utils.getTotalRAM
import java.io.PrintWriter
import java.io.StringWriter

class ExceptionHandler(private val context: Context) : Thread.UncaughtExceptionHandler {

    companion object {
        const val LINE = "\n"
    }

    override fun uncaughtException(t: Thread?, e: Throwable?) {
        val stackTrace = StringWriter()
        e?.printStackTrace(PrintWriter(stackTrace))
        val errorReport = StringBuilder()
            .append("CAUSE OF ERROR:\n")
            .append(stackTrace.toString())
            .append("\nLOGS:\n")
            .append(Lg.getFormatted())
            .append("\n\nDEVICE INFORMATION:\n")
            .append("RAM: ")
            .append(getTotalRAM())
            .append(LINE)
            .append("Version: ")
            .append(BuildConfig.VERSION_NAME)
            .append(LINE)
            .append("Brand: ")
            .append(Build.MANUFACTURER)
            .append(LINE)
            .append("Model: ")
            .append(Build.MODEL)
            .append(LINE)
            .append("SDK: ")
            .append(Build.VERSION.SDK)
            .append(LINE)

        Lg.wtf(errorReport.toString())

        try {
            ExceptionActivity.launch(context, errorReport.toString())
        } catch (e: Exception) {
            Lg.i("error handling exception: ${e.message}")
        }

        android.os.Process.killProcess(android.os.Process.myPid())
        System.exit(10)
    }
}