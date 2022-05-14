package suitmedia.com.dev.suitemedia.core.extension

import android.os.SystemClock
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter

inline fun TextView.delayedOnTextChange(
    delay: Long = 250L, crossinline action: (
        text: CharSequence?,
        start: Int,
        before: Int,
        count: Int
    ) -> Unit
) = addDelayedTextChangeListener(delay, onTextChange = action)

inline fun TextView.delayedAfterTextChange(
    delay: Long = 250L,
    crossinline action: (editable: Editable?) -> Unit
) = addDelayedTextChangeListener(delay, afterTextChange = action)

inline fun TextView.addDelayedTextChangeListener(
    delay: Long,
    crossinline beforeTextChange: (
        text: CharSequence?,
        start: Int,
        count: Int,
        after: Int
    ) -> Unit = { _, _, _, _ -> },
    crossinline onTextChange: (
        text: CharSequence?,
        start: Int,
        before: Int,
        count: Int
    ) -> Unit = { _, _, _, _ -> },
    crossinline afterTextChange: (
        editable: Editable?
    ) -> Unit = { _ -> }
): TextWatcher {
    val watcher = object : TextWatcher {
        private var lastExecutionTimeBefore = 0L
        private var lastExecutionTimeOn = 0L
        private var lastExecutionTimeAfter = 0L
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            if ((SystemClock.elapsedRealtime() - lastExecutionTimeBefore) >= delay) {
                lastExecutionTimeBefore = SystemClock.elapsedRealtime()
                beforeTextChange.invoke(s, start, count, after)
            }
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if ((SystemClock.elapsedRealtime() - lastExecutionTimeOn) >= delay) {
                Log.d("OnTextChange", "Invocation")
                lastExecutionTimeOn = SystemClock.elapsedRealtime()
                onTextChange.invoke(s, start, before, count)
            }
        }

        override fun afterTextChanged(s: Editable?) {
            if ((SystemClock.elapsedRealtime() - lastExecutionTimeAfter) >= delay) {
                lastExecutionTimeAfter = SystemClock.elapsedRealtime()
                afterTextChange.invoke(s)
            }
        }
    }
    addTextChangedListener(watcher)
    return watcher
}

@BindingAdapter("textOrGone")
fun TextView.textOrGone(value: String?) {
    value?.let {
        text = it
        visibility = View.VISIBLE
    } ?: this.run { visibility = View.GONE }
}

@BindingAdapter("textOrGone")
fun TextView.textOrGone(resId: Int?) {
    resId?.let {
        setText(resId)
        visibility = View.VISIBLE
    } ?: this.run { visibility = View.GONE }
}


@BindingAdapter("android:text")
fun TextView.textByResId(@StringRes resId: Int) {
    setText(resId)
}

@BindingAdapter("fromHtml")
fun TextView.textFromHtml(text: String) {
    setText(HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY))
}

@BindingAdapter("fromHtmlResId")
fun TextView.textFromHtmlResId(@StringRes resId: Int) = textFromHtml(resources.getString(resId))