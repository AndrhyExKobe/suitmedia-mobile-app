package suitmedia.com.dev.suitemedia.core.rvprovider

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import javax.security.auth.callback.Callback

abstract class BaseRVProviderAdapter<T> : RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected val providerManager: RVItemProviderManager<List<T>>

    protected val items: MutableList<T> = mutableListOf()

    constructor(fixedCount: Int = 10) : this(RVItemProviderManager(fixedCount))

    constructor(providerManager: RVItemProviderManager<List<T>>) {
        this.providerManager = providerManager
    }

    constructor(vararg itemProvider: RVItemProvider<List<T>>, fixedCount: Int = 10) : this(
        RVItemProviderManager<List<T>>(*itemProvider, fixedCount = fixedCount)
    )

    override fun getItemViewType(position: Int): Int =
        providerManager.getItemViewType(items, position)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        providerManager.onCreateViewHolder(parent, viewType)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        providerManager.onBindViewHolder(items, position, holder)

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) = providerManager.onBindViewHolder(items, position, holder, payloads)

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) =
        providerManager.onViewRecycled(holder)

    override fun onFailedToRecycleView(holder: RecyclerView.ViewHolder): Boolean =
        providerManager.onFailedToRecyclerView(holder)

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) =
        providerManager.onViewAttachToWindow(holder)

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) =
        providerManager.onViewDetachFromWindow(holder)

    override fun getItemCount(): Int = items.size

}
