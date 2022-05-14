package suitmedia.com.dev.suitemedia.ui.screentwo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import suitmedia.com.dev.suitemedia.R
import suitmedia.com.dev.suitemedia.core.extension.observe
import suitmedia.com.dev.suitemedia.core.fragment.BaseViewBindingFragment
import suitmedia.com.dev.suitemedia.databinding.FragmentScreenTwoBinding
import suitmedia.com.dev.suitemedia.ui.screenone.ScreenOneFragmentDirections
import suitmedia.com.dev.suitemedia.ui.screenone.viewmodel.ScreenOneViewModel
import suitmedia.com.dev.suitemedia.ui.screentwo.viewmodel.ScreenTwoViewModel
import suitmedia.com.dev.suitemedia.utils.CustomSetting


/**
 * Created by Andri Dwi Utomo on 13/5/2022.
 * Mallsampah Indonesia
 * andri@mallsampah.com
 */
class ScreenTwoFragment : BaseViewBindingFragment<FragmentScreenTwoBinding>() {

    override fun doViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentScreenTwoBinding.inflate(inflater, container, false)

    private val viewModel: ScreenTwoViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CustomSetting.changeColorActionBar(requireActivity(), resources.getColor(R.color.secondaryBlack))

        viewModel.onEventReceived(ScreenTwoViewModel.Event.OnViewCreated)

        bindViewModel()

        binding.btnEvent.setOnClickListener {
            val destination = ScreenTwoFragmentDirections.actionScreenTwoFragmentToScreenBaseEventFragment()
            navController.navigate(destination)
        }

        binding.btnGuest.setOnClickListener {
            val destination = ScreenTwoFragmentDirections.actionScreenTwoFragmentToScreenFourFragment()
            navController.navigate(destination)
        }
    }

    private fun bindViewModel(){
        observe(viewModel.state) {
            when (it) {
                is ScreenTwoViewModel.State.ViewCreated -> {
                    binding.tvName.setText(it.name)
                    binding.btnEvent.setText(it.event)
                    binding.btnGuest.setText(it.guest)
                }
            }
        }
    }
}