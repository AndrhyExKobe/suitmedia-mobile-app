package suitmedia.com.dev.suitemedia.ui.screenthree

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import suitmedia.com.dev.suitemedia.R
import suitmedia.com.dev.suitemedia.core.extension.observe
import suitmedia.com.dev.suitemedia.core.fragment.BaseViewBindingFragment
import suitmedia.com.dev.suitemedia.core.rvprovider.ItemRVProviderAdapter
import suitmedia.com.dev.suitemedia.core.rvprovider.itemProviderCreate
import suitmedia.com.dev.suitemedia.databinding.FragmentScreenThreeBinding
import suitmedia.com.dev.suitemedia.databinding.ListItemEventBinding
import suitmedia.com.dev.suitemedia.ui.screenone.viewmodel.ScreenOneViewModel
import suitmedia.com.dev.suitemedia.ui.screenthree.dummy.Event
import suitmedia.com.dev.suitemedia.ui.screenthree.model.EventUiModel
import suitmedia.com.dev.suitemedia.ui.screenthree.model.OnEventItemModel
import suitmedia.com.dev.suitemedia.ui.screenthree.viewmodel.ScreenThreeViewModel
import suitmedia.com.dev.suitemedia.ui.screentwo.ScreenTwoFragmentDirections
import suitmedia.com.dev.suitemedia.ui.screentwo.viewmodel.ScreenTwoViewModel
import suitmedia.com.dev.suitemedia.utils.CustomSetting


/**
 * Created by Andri Dwi Utomo on 14/5/2022.
 * Mallsampah Indonesia
 * andri@mallsampah.com
 */
class ScreenThreeFragment : BaseViewBindingFragment<FragmentScreenThreeBinding>() {
    override fun doViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentScreenThreeBinding.inflate(inflater, container, false)

    private val adapterEvent by lazy {
        ItemRVProviderAdapter(
            onEventProvider()
        )
    }
    private val viewModel: ScreenThreeViewModel by viewModels()

    private var state: String = "maps"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CustomSetting.changeColorActionBar(requireActivity(), resources.getColor(R.color.secondaryOrange))


        val layoutManager = LinearLayoutManager(requireContext())
        /*val layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)*/

        binding?.rvEvent.let {
            it.layoutManager = layoutManager
            it.smoothScrollToPosition(1)
            it.adapter = adapterEvent
        }

        var listEvent = Event.listEvent

        adapterEvent.update(listEvent)

        bindViewModel()


    }

    internal fun onEventProvider() =
        itemProviderCreate<OnEventItemModel, EventUiModel, ListItemEventBinding>(R.layout.list_item_event) {

            onBind { model, _ ->
                binding?.model = model
                binding?.layGetEvent?.setOnClickListener {
                    viewModel.onEventReceived(ScreenThreeViewModel.Event.OnSelectEvent(model.title))
                }
            }
        }

    private fun bindViewModel(){
        observe(viewModel.state) {
            when (it) {
                is ScreenThreeViewModel.State.Success -> {
                    try {
                        val destination = ScreenBaseEventFragmentDirections.actionScreenBaseEventFragmentToScreenTwoFragment()

                        navController.navigate(destination)
                    } catch (e : Exception) {
                        Log.v("isinya ini", navController.currentDestination?.label.toString())
                    }

                }
            }
        }
    }
}