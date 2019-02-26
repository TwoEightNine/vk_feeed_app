package global.msnthrp.feeed.utils

import android.graphics.Typeface
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

object Styler {

    private val SANS_SERIF_LIGHT = Typeface.create("sans-serif-light", Typeface.NORMAL)

    fun dialog(alertDialog: AlertDialog) {
        with(alertDialog) {
            findViewById<TextView>(android.R.id.message)?.apply {
                typeface = SANS_SERIF_LIGHT
                textSize = 18f
            }
            findViewById<Button>(android.R.id.button1)?.apply {
                typeface = SANS_SERIF_LIGHT
                textSize = 18f
            }
            findViewById<Button>(android.R.id.button2)?.apply {
                typeface = SANS_SERIF_LIGHT
                textSize = 18f
            }
        }
    }

}