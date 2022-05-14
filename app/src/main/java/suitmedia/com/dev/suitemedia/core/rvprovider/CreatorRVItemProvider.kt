package suitmedia.com.dev.suitemedia.core.rvprovider

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import suitmedia.com.dev.suitemedia.core.extension.inflate

inline fun <reified I : T, T, B : ViewDataBinding> itemProviderCreate(
    @LayoutRes layoutId: Int,
    noinline isItem: (item: T, list: List<T>, position: Int) -> Boolean = { item, _, _ -> item is I },
    noinline initializerBlock: CreatorItemVH<B, I>.() -> Unit
): RVItemProvider<List<T>> {
    return CreatorRVProvider(layoutId, isItem, initializerBlock)
}


@PublishedApi
internal class CreatorRVProvider<I : T, T, B : ViewDataBinding>(
    @LayoutRes private val itemView: Int,
    private val isItem: (item: T, list: List<T>, position: Int) -> Boolean,
    private val initializerBlock: CreatorItemVH<B, I>.() -> Unit
) : AbsRVItemProvider<I, T, CreatorItemVH<B, I>>() {

    override fun isForViewType(item: T, list: List<T>, position: Int): Boolean {
        return isItem.invoke(item, list, position)
    }

    override fun onBindViewHolder(
        model: I,
        holder: CreatorItemVH<B, I>,
        payloads: MutableList<Any>
    ) {
        holder.setUiModel(model, payloads)
    }

    override fun onCreateViewHolder(parent: ViewGroup): CreatorItemVH<B, I> {
        return CreatorItemVH<B, I>(parent.inflate(itemView)).apply(initializerBlock)
    }
}

internal abstract class AbsRVItemProvider<I : T, T, VH : RecyclerView.ViewHolder> :
    RVItemProvider<List<T>>() {

    protected abstract fun isForViewType(item: T, list: List<T>, position: Int): Boolean

    protected abstract fun onBindViewHolder(model: I, holder: VH, payloads: MutableList<Any>)

    override abstract fun onCreateViewHolder(parent: ViewGroup): VH

    override fun isForViewType(item: List<T>, position: Int): Boolean {
        return isForViewType(item[position], item, position)
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(
        item: List<T>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ) {
        onBindViewHolder(item[position] as I, holder as VH, payloads)
    }
}

class CreatorItemVH<B : ViewDataBinding, I>(
    view: View
) : BaseRVViewHolder<B, I>(view) {

    private var _onBind: ((model: I, payload: MutableList<Any>) -> Unit)? = null

    override fun bind(uiModel: I, payloads: MutableList<Any>) {
        _onBind?.invoke(uiModel, payloads)
    }

    fun onBind(block: (model: I, payload: MutableList<Any>) -> Unit) {
        if (_onBind != null) throw IllegalArgumentException("onBind { ... } is already defined. Only one onBind { ... } is allowed.")
        _onBind = block
    }
}