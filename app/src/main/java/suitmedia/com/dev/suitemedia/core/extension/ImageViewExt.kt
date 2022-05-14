package suitmedia.com.dev.suitemedia.core.extension

import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import suitmedia.com.dev.suitemedia.di.glide.GlideApp
import java.io.File

@BindingAdapter(value = ["source", "placeholder"], requireAll = false)
fun ImageView.fromUrl(url: String?, placeholder: Drawable? = null) {
    if (url.isValidUrl()) {
        glideLoadImage(this, url!!, placeholder = placeholder)
    } else {
        setImageDrawable(placeholder)
    }
}

@BindingAdapter(value = ["source", "placeholder"], requireAll = false)
fun ImageView.fromUri(uri: Uri?, placeholder: Drawable? = null) {
    uri?.let {
        glideLoadImage(this, it,placeholder = placeholder)
    } ?: setImageDrawable(placeholder)
}

@BindingAdapter("source")
fun ImageView.fromResId(@DrawableRes resId: Int) {
    setImageResource(resId)
}

@BindingAdapter(value = ["source", "placeholder"], requireAll = false)
fun ImageView.fromFile(file: File?, placeholder: Drawable? = null) {
    file?.let {
        glideLoadImage(this, it)
    } ?: setImageDrawable(placeholder)
}

@BindingAdapter("source")
fun ImageView.fromBytes(bytes: ByteArray?) {
    Glide.with(this)
        .load(bytes)
        .into(this)
}

@BindingAdapter(value = ["sourceCircle", "placeholder"], requireAll = false)
fun ImageView.fromBytesCircle(bytes: ByteArray?, placeholder: Drawable? = null) {
    if (bytes != null) {
        glideLoadImage(this, bytes, RequestOptions().circleCrop(), placeholder)
    } else {
        setImageDrawable(placeholder)
    }
}

@BindingAdapter(value = ["sourceCircle", "placeholder"], requireAll = false)
fun ImageView.fromFileCircle(file: File?, placeholder: Drawable? = null) {
    if (file != null) {
        glideLoadImage(this, file, RequestOptions().circleCrop(), placeholder)
    } else {
        setImageDrawable(placeholder)
    }
}

@BindingAdapter(value = ["sourceCircle", "placeholder"], requireAll = false)
fun ImageView.fromUrlCircle(url: String?, placeholder: Drawable? = null) {
    if (url.isValidUrl()) {
        glideLoadImage(this, url!!, RequestOptions().circleCrop(), placeholder)
    } else {
        setImageDrawable(placeholder)
    }
}

@BindingAdapter(value = ["sourceCircle", "placeholder"], requireAll = false)
fun ImageView.fromUriCircle(uri: Uri?, placeholder: Drawable? = null) {
    if (uri != null) {
        glideLoadImage(this, uri, RequestOptions().circleCrop(), placeholder)
    } else {
        setImageDrawable(placeholder)
    }
}

@BindingAdapter("sourceCircle")
fun ImageView.fromResIdCircle(resId: Int) {
    glideLoadImage(this, resId, RequestOptions().circleCrop())
}

@BindingAdapter("colorTint")
fun ImageView.tintColor(@ColorRes color: Int) {
    this.setColorFilter(ContextCompat.getColor(context, color))
}

@BindingAdapter(value = ["sourceOrGone", "placeholder"], requireAll = false)
fun ImageView.sourceOrGone(url: String?, placeholder: Drawable? = null) {
    if (url == null && placeholder == null) {
        setImageDrawable(null)
        visibility = View.GONE
    } else if (url.isValidUrl()) {
        visibility = View.VISIBLE
        glideLoadImage(this, url!!, placeholder = placeholder)
    } else {
        visibility = View.VISIBLE
        setImageDrawable(placeholder)
    }
}

@BindingAdapter(value = ["sourceOrGone", "placeholder"], requireAll = false)
fun ImageView.sourceOrGone(uri: Uri?, placeholder: Drawable? = null) {
    if (uri == null && placeholder == null) {
        setImageDrawable(null)
        visibility = View.GONE
    } else if (uri != null) {
        visibility = View.VISIBLE
        glideLoadImage(this, uri, placeholder = placeholder)
    } else {
        visibility = View.VISIBLE
        setImageDrawable(placeholder)
    }
}

@BindingAdapter(value = ["sourceOrGone", "placeholder"], requireAll = false)
fun ImageView.sourceOrGone(file: File?, placeholder: Drawable? = null) {
    if (file == null && placeholder == null) {
        setImageDrawable(null)
        visibility = View.GONE
    } else if (file != null) {
        visibility = View.VISIBLE
        glideLoadImage(this, file, placeholder = placeholder)
    } else {
        visibility = View.VISIBLE
        setImageDrawable(placeholder)
    }
}

@BindingAdapter("sourceOrGone")
fun ImageView.sourceOrGone(@DrawableRes resId: Int) {
    visibility = if (resId == 0) {
        setImageDrawable(null)
        View.GONE
    } else {
        setImageResource(resId)
        View.VISIBLE
    }
}

fun glideLoadImage(
    view: ImageView,
    from: Any,
    requestOption: RequestOptions = RequestOptions(),
    placeholder: Drawable? = null,
    error: Drawable? = null
) {
    GlideApp.with(view)
        .load(from)
        .placeholder(placeholder)
        .error(error)
        .apply(requestOption)
        .into(view)
}