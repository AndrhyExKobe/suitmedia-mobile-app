package suitmedia.com.dev.suitemedia.ui.screenfour

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import suitmedia.com.dev.suitemedia.R
import suitmedia.com.dev.suitemedia.core.CommonsPreferenceManager
import suitmedia.com.dev.suitemedia.core.extension.observe
import suitmedia.com.dev.suitemedia.core.fragment.BaseViewBindingFragment
import suitmedia.com.dev.suitemedia.core.rvprovider.ItemRVProviderAdapter
import suitmedia.com.dev.suitemedia.core.rvprovider.itemProviderCreate
import suitmedia.com.dev.suitemedia.databinding.FragmentScreenFourBinding
import suitmedia.com.dev.suitemedia.databinding.ListItemGuestBinding
import suitmedia.com.dev.suitemedia.ui.screenfour.model.GuestUiModel
import suitmedia.com.dev.suitemedia.ui.screenfour.model.OnGuestItemModel
import suitmedia.com.dev.suitemedia.ui.screenfour.viewmodel.ScreenFourViewModel
import suitmedia.com.dev.suitemedia.utils.CustomSetting
import java.io.*


/**
 * Created by Andri Dwi Utomo on 14/5/2022.
 * Mallsampah Indonesia
 * andri@mallsampah.com
 */
class ScreenFourFragment : BaseViewBindingFragment<FragmentScreenFourBinding>() {
    override fun doViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentScreenFourBinding.inflate(inflater, container, false)

    private val adapterGuest by lazy {
        ItemRVProviderAdapter(
            onGuestProvider()
        )
    }
    private val viewModel: ScreenFourViewModel by viewModels()

    var page = 1
    var showpage = 1
    var perpage = 10
    var total = 0

    private val prefManager by lazy { CommonsPreferenceManager(requireContext()) }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CustomSetting.changeColorActionBar(requireActivity(), resources.getColor(R.color.secondaryOrange))
        binding.toolbar.tvToolbarTitle.text = "Guests"
        binding.toolbar.ivToolbarIconLeft.setOnClickListener {
            navController.popBackStack()
        }

        /*val layoutManager = LinearLayoutManager(requireContext())*/
        val layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)

        binding?.rvGuest.let {
            it.layoutManager = layoutManager
            it.smoothScrollToPosition(1)
            it.adapter = adapterGuest
        }

        viewModel.onEventReceived(ScreenFourViewModel.Event.OnViewCreated(page, perpage))

        bindViewModel()

        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = false
            perpage = 10
            viewModel.onEventReceived(ScreenFourViewModel.Event.OnViewCreated(page, perpage))
        }

        binding.rvGuest.addOnScrollListener(scrollToPosition())

        var data = prefManager.listGuest()

        if (data != null) {
            var listGuest : List<OnGuestItemModel> = listOf()
            data?.map {
                listGuest = listGuest + OnGuestItemModel(
                    Uri.parse(it.avatar),
                    it.firstName+" "+it.lastName
                )
            }

            total = data?.size!!

            adapterGuest.update(listGuest)

        }

    }

    private fun scrollToPosition() = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                //reached end
                if (perpage < total) {
                    showpage++
                    perpage = showpage * perpage
                    Toast.makeText(requireContext(), "Loading Data...", Toast.LENGTH_SHORT).show()
                    viewModel.onEventReceived(ScreenFourViewModel.Event.OnViewCreated(page, perpage))
                }


            }

            if (!recyclerView.canScrollVertically(-1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                //reached top
            }
            if(newState == RecyclerView.SCROLL_STATE_DRAGGING){
                //scrolling

            }
        }
    }

    internal fun onGuestProvider() =
        itemProviderCreate<OnGuestItemModel, GuestUiModel, ListItemGuestBinding>(R.layout.list_item_guest) {

            onBind { model, _ ->
                binding?.model = model
                binding?.layGetGuest?.setOnClickListener {
                    viewModel.onEventReceived(ScreenFourViewModel.Event.OnSelectGuest(model.title))
                }
            }
        }

    private fun bindViewModel(){
        observe(viewModel.state) {
            when (it) {
                is ScreenFourViewModel.State.OnViewCreated -> {
                    adapterGuest.update(it.guestItemModel)
                    total = it.total
                }
                is ScreenFourViewModel.State.Success -> {
                    val destination = ScreenFourFragmentDirections.actionScreenFourFragmentToScreenTwoFragment()
                    navController.navigate(destination)
                }
                is ScreenFourViewModel.State.RequestFailed -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

                }
            }
        }
    }


}