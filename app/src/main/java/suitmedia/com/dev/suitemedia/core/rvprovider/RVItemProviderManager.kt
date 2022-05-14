package suitmedia.com.dev.suitemedia.core.rvprovider

import android.view.View
import android.view.ViewGroup
import androidx.collection.SparseArrayCompat
import androidx.collection.forEach
import androidx.recyclerview.widget.RecyclerView

class RVItemProviderManager<T> constructor(fixedCount: Int = 10) {

    companion object {
        private val EMPTY_PAYLOAD = mutableListOf<Any>()
    }

    private val providers = SparseArrayCompat<RVItemProvider<T>>(
        if (fixedCount < 1) 1 else fixedCount
    )

    constructor(vararg provider: RVItemProvider<T>, fixedCount: Int = 10) : this(fixedCount) {
        provider.forEach { addProvider(it) }
    }

    fun addProvider(provider: RVItemProvider<T>): RVItemProviderManager<T> {
        var viewType = providers.size()
        while (providers[viewType] != null) {
            viewType++
            if (viewType == Int.MAX_VALUE - 1) {
                throw IllegalArgumentException("Oops that's to much type, we are closely with Integer.MAX_VALUE")
            }
        }
        return addProvider(viewType, provider)
    }

    fun addProvider(viewType: Int, provider: RVItemProvider<T>): RVItemProviderManager<T> {
        return addProvider(viewType, false, provider)
    }

    fun addProvider(
        viewType: Int,
        allowReplace: Boolean,
        provider: RVItemProvider<T>
    ): RVItemProviderManager<T> {
        if (viewType == Int.MAX_VALUE - 1) {
            throw IllegalArgumentException("Oops that's to much type, we are closely with Integer.MAX_VALUE")
        }

        if (!allowReplace && providers[viewType] != null) {
            throw IllegalArgumentException("ItemProvider already registered for the viewType ${viewType}. Registered is ${providers[viewType]}")
        }

        providers.put(viewType, provider)

        return this
    }

    fun removeProvider(provider: RVItemProvider<T>): RVItemProviderManager<T> {
        val index = providers.indexOfValue(provider)
        if (index >= 0) providers.removeAt(index)
        return this
    }

    fun removeProvider(viewType: Int): RVItemProviderManager<T> {
        providers.remove(viewType)
        return this
    }

    fun getItemViewType(item: T, position: Int): Int {
        providers.forEach { key, value ->
            if (value.isForViewType(item, position)) {
                return key
            }
        }

        when (item) {
            is List<*> -> throw NullPointerException("No Provider added matches item=${item[position]} at position $position")
            else -> throw NullPointerException("No Provider added for item at position $position and item=$item")
        }
    }

    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return providers[viewType]?.onCreateViewHolder(parent)
            ?: throw NullPointerException("No Provider for viewType $viewType")
    }

    fun onBindViewHolder(
        item: T,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payload: MutableList<Any>
    ) {
        providers[holder.itemViewType]?.onBindViewHolder(item, position, holder, payload)
            ?: throw NullPointerException("No Provider found for item at position $position and viewType ${holder.itemViewType}")
    }

    fun onBindViewHolder(
        item: T,
        position: Int,
        holder: RecyclerView.ViewHolder
    ) {
        onBindViewHolder(item, position, holder, EMPTY_PAYLOAD)
    }

    fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        providers[holder.itemViewType]?.onViewRecycled(holder)
            ?: throw NullPointerException("No Provider found for item at position ${holder.adapterPosition} and viewType ${holder.itemViewType}")
    }

    fun onFailedToRecyclerView(holder: RecyclerView.ViewHolder): Boolean {
        return providers[holder.itemViewType]?.onFailedToRecyclerView(holder)
            ?: throw NullPointerException("No Provider found for item at position ${holder.adapterPosition} and viewType ${holder.itemViewType}")
    }

    fun onViewAttachToWindow(holder: RecyclerView.ViewHolder) {
        providers[holder.itemViewType]?.onViewAttachedToWindow(holder)
            ?: throw NullPointerException("No Provider found for item at position ${holder.adapterPosition} and viewType ${holder.itemViewType}")
    }

    fun onViewDetachFromWindow(holder: RecyclerView.ViewHolder) {
        providers[holder.itemViewType]?.onViewDetachedFromWindow(holder)
            ?: throw NullPointerException("No Provider found for item at position ${holder.adapterPosition} and viewType ${holder.itemViewType}")
    }

    fun getViewType(provider: RVItemProvider<T>): Int {
        return when (val index = providers.indexOfValue(provider)) {
            -1 -> -1
            else -> providers.keyAt(index)
        }
    }

    fun getProviderForViewType(viewType: Int): RVItemProvider<T>? {
        return providers.get(viewType, null)
    }

}