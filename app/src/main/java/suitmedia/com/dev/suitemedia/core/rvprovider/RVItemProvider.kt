package suitmedia.com.dev.suitemedia.core.rvprovider

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class RVItemProvider<T> {

    internal abstract fun isForViewType(item: T, position: Int): Boolean

    internal abstract fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder

    internal abstract fun onBindViewHolder(
        item: T,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    )

    @SuppressLint("UNUSED_PARAMETER")
    internal fun onViewRecycled(holder: RecyclerView.ViewHolder) {

    }

    @SuppressLint("UNUSED_PARAMETER")
    internal fun onFailedToRecyclerView(holder: RecyclerView.ViewHolder): Boolean {
        return false
    }

    @SuppressLint("UNUSED_PARAMETER")
    internal fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {

    }

    @SuppressLint("UNUSED_PARAMETER")
    internal fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {

    }
}