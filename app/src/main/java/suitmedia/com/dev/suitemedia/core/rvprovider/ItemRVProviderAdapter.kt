package suitmedia.com.dev.suitemedia.core.rvprovider

import android.view.View

class ItemRVProviderAdapter<T> : BaseRVProviderAdapter<T> {

    constructor(fixedType: Int = 10) : super(fixedType)

    constructor(providerManager: RVItemProviderManager<List<T>>) : super(providerManager)

    constructor(
        vararg itemProvider: RVItemProvider<List<T>>,
        fixedType: Int = 10
    ) : super(*itemProvider, fixedCount = fixedType)

    fun update(list: List<T>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    fun add(newItem: T) {
        items.add(newItem)
        notifyItemInserted(items.size)
    }

    fun add(position: Int, newItem: T) {
        items.add(position, newItem)
        notifyItemInserted(position)
        notifyItemRangeChanged(position, itemCount - position)
    }

    fun addAll(newItems: List<T>) {
        val startPosition = itemCount
        items.addAll(newItems)
        notifyItemRangeInserted(startPosition, newItems.size)
    }

    fun removeItem(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount - position)
    }

    fun updateItem(newItem: T, position: Int) {
        items[position] = newItem
        notifyItemChanged(position)
    }

    fun getItem(position: Int): T? {
        return if (position < 0 || position >= itemCount) return null
        else items[position]
    }

    fun removeItem(item: T) {
        when (val position = items.indexOf(item)) {
            -1 -> return
            else -> removeItem(position)
        }
    }

}