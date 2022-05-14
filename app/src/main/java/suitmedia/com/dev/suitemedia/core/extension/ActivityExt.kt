package suitmedia.com.dev.suitemedia.core.extension

import android.app.Activity
import androidx.core.app.ActivityCompat

fun Activity.requestAnyPermission(permission: String, requestCode: Int, showRationale: () -> Unit) {
    requestPermissions(this, permission, requestCode, showRationale)
}

private fun requestPermissions(
    activity: Activity,
    permission: String,
    requestCode: Int,
    showRationale: () -> Unit
) {
    if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
        showRationale.invoke()
    }
    ActivityCompat.requestPermissions(activity, arrayOf(permission), requestCode)
}