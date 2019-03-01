package global.msnthrp.feeed.base

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import global.msnthrp.feeed.exception.ExceptionHandler

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // i cant use it while sending reports is not working
//        Thread.setDefaultUncaughtExceptionHandler(ExceptionHandler(this))
    }
}