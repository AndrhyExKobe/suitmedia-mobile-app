package suitmedia.com.dev.suitemedia.core.rvprovider

import android.content.Context
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRVViewHolder<out Binding : ViewDataBinding, UiModel>(
    view: View
) : RecyclerView.ViewHolder(view) {

    val binding: Binding? by lazy { DataBindingUtil.bind<Binding>(view) }

    val itemContext: Context
        get() = itemView.context

    protected abstract fun bind(uiModel: UiModel, payloads: MutableList<Any>)

    fun setUiModel(uiModel: UiModel, payloads: MutableList<Any>) {
        bind(uiModel, payloads)
        binding?.executePendingBindings()
    }

}