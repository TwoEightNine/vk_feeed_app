package global.msnthrp.feeed.utils

import android.graphics.Typeface
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import global.msnthrp.feeed.R

object Styler {

    private val SANS_SERIF_LIGHT = Typeface.create("sans-serif-light", Typeface.NORMAL)

    fun dialog(alertDialog: AlertDialog) {
        with(alertDialog) {
            val backgroundColor = ContextCompat.getColor(context, R.color.background)
            val textColor = ContextCompat.getColor(context, R.color.textMain)
            findViewById<TextView>(android.R.id.message)?.apply {
                typeface = SANS_SERIF_LIGHT
                textSize = 18f
                setTextColor(textColor)
            }
            findViewById<Button>(android.R.id.button1)?.apply {
                typeface = SANS_SERIF_LIGHT
                textSize = 18f
            }
            findViewById<Button>(android.R.id.button2)?.apply {
                typeface = SANS_SERIF_LIGHT
                textSize = 18f
            }
            findViewById<View>(R.id.contentPanel)?.setBackgroundColor(backgroundColor)
            findViewById<View>(R.id.buttonPanel)?.setBackgroundColor(backgroundColor)
            findViewById<View>(R.id.topPanel)?.setBackgroundColor(backgroundColor)
        }
    }

}