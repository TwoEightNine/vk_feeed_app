package global.msnthrp.feeed.utils

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.util.SparseArray
import androidx.annotation.StringRes
import androidx.core.app.ActivityCompat
import androidx.core.app.DialogCompat
import androidx.core.content.PermissionChecker
import androidx.fragment.app.Fragment
import global.msnthrp.feeed.R


/**
 *  1. override [Activity.onRequestPermissionsResult]:
 *  override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
 *      super.onRequestPermissionsResult(requestCode, permissions, grantResults)
 *      permissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults)
 *  }
 *
 *  2. make calls via [doOrRequest]
 *
 */
class PermissionHelper(private val activity: Activity?) {

    private val callbacks = SparseArray<() -> Unit>()
    private var fragment: Fragment? = null

    /**
     * should be >=0 and use least 16 bits
     * @return random request code
     */
    private val requestCode: Int
        get() = (Math.random() * Integer.MAX_VALUE).toInt() % 65536


    constructor(fragment: Fragment) : this(fragment.activity) {
        this.fragment = fragment
    }

    fun doOrRequest(
        permission: String,
        detailMessage: String,
        onGranted: (() -> Unit)?
    ) {
        if (hasPermission(permission)) {
            onGranted?.invoke()
        } else {
            val requestCode = requestCode
            callbacks.append(requestCode, onGranted)
            showRequestDialog(detailMessage, permission, requestCode)
        }
    }

    private fun showRequestDialog(
        detailMessage: String,
        permission: String,
        requestCode: Int
    ) {
        showConfirm(activity, detailMessage) { confirmed ->
            if (confirmed) requestPermissions(permission, requestCode)
        }
    }

    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        val callback = callbacks.get(requestCode)
        callbacks.remove(requestCode)
        if (callback == null) return

        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            callback()
        }
    }

    fun hasStoragePermission(): Boolean {
        return hasPermission(STORAGE)
    }

    fun hasCameraPermission(): Boolean {
        return hasPermission(CAMERA)
    }

    fun hasLocationPermission(): Boolean {
        return hasPermission(LOCATION)
    }

    private fun requestPermissions(permission: String, requestCode: Int) {
        fragment?.requestPermissions(arrayOf(permission), requestCode)
            ?: ActivityCompat.requestPermissions(activity ?: return, arrayOf(permission), requestCode)
    }

    private fun hasPermission(permission: String): Boolean {
        val check = PermissionChecker.checkSelfPermission(activity ?: return false, permission)
        return check == PackageManager.PERMISSION_GRANTED
    }

    companion object {

        const val STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE
        const val CAMERA = Manifest.permission.CAMERA
        const val LOCATION = Manifest.permission.ACCESS_FINE_LOCATION
    }

}
