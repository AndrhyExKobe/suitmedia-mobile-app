package suitmedia.com.dev.suitemedia.core.extension

import java.text.SimpleDateFormat
import java.util.*

fun Date.toDisplayDate(pattern: String): String {
    val sdf = SimpleDateFormat(pattern, Locale.getDefault())
    return sdf.format(this)
}

fun Date.toDisplayDate(pattern: String, locale: Locale): String {
    val sdf = SimpleDateFormat(pattern, locale)
    return sdf.format(this)
}