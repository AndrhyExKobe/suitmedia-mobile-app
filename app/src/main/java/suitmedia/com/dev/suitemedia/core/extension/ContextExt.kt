package suitmedia.com.dev.suitemedia.core.extension

import android.Manifest
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import androidx.core.content.res.ResourcesCompat

fun Context.isLocationPermissionGranted(): Boolean {
    val lowerThanM = Build.VERSION.SDK_INT < Build.VERSION_CODES.M
    val fine = checkPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)
    val coarse = checkPermissionGranted(Manifest.permission.ACCESS_COARSE_LOCATION)

    return lowerThanM || (fine && coarse)
}

fun Context.dismissKeyboard(view: View) {
    val imm: InputMethodManager? = getSystemService()
    imm?.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.showKeyboardFrom(view: View){
    val imm: InputMethodManager? = getSystemService()
    imm?.showSoftInput(view, 0)
}

fun Context.copyToClipBoard(text: String) {
    val clipboard: ClipboardManager? = getSystemService()
    val clip = ClipData.newPlainText(text, text)
    clipboard?.setPrimaryClip(clip)
}

fun Context.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Context.appAlreadyInstalled(applicationId: String) = try {
    packageManager.getPackageInfo(applicationId, PackageManager.GET_ACTIVITIES)
    true
} catch (e: Exception) {
    false
}

fun Context.checkPermissionGranted(permission: String): Boolean {
    return ContextCompat.checkSelfPermission(
        this,
        permission
    ) == PackageManager.PERMISSION_GRANTED
}

fun Context.callPhoneNumber(number: String) {
    val intent = Intent(Intent.ACTION_DIAL)
    intent.data = Uri.parse("tel:$number")
    if (intent.resolveActivity(packageManager) != null)
        startActivity(intent)
    else
        toast("Phone application needed")
}

fun Context.sendSms(number: String) {
    startActivity(Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", number, null)))
}

fun Context.colorCompat(@ColorRes resId: Int): Int {
    return ResourcesCompat.getColor(resources, resId, theme)
}