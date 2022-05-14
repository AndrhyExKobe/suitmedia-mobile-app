package suitmedia.com.dev.suitemedia.core.extension

import android.os.Build
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter

@BindingAdapter("updateProgress")
fun ProgressBar.updateProgress(progress: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        this.setProgress(progress, true)
    } else {
        this.progress = progress
    }
}