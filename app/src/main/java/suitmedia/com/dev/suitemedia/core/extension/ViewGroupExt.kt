package suitmedia.com.dev.suitemedia.core.extension

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

fun ViewGroup.inflate(@LayoutRes layoutResId: Int): View = LayoutInflater.from(context)
    .inflate(layoutResId, this, false)

