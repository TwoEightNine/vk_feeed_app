package global.msnthrp.feeed.exception

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import global.msnthrp.feeed.R
import global.msnthrp.feeed.utils.restartApp
import kotlinx.android.synthetic.main.activity_exception.*

class ExceptionActivity : AppCompatActivity() {

    companion object {
        const val ERROR = "error"

        fun launch(context: Context?, error: String) {
            context?.startActivity(Intent(context, ExceptionActivity::class.java).apply {
                putExtra(ERROR, error)
            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exception)
        tvDetails.text = intent.extras?.getString(ERROR)
        btnSendReport.setOnClickListener { sendReport() }
    }

    private fun sendReport() {
        // do stuff
        onBackPressed()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        restartApp(this)
    }
}