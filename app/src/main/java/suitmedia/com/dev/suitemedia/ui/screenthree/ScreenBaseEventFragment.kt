package suitmedia.com.dev.suitemedia.ui.screenthree

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import suitmedia.com.dev.suitemedia.R
import suitmedia.com.dev.suitemedia.core.fragment.BaseViewBindingFragment
import suitmedia.com.dev.suitemedia.databinding.FragmentScreenBaseEventBinding
import suitmedia.com.dev.suitemedia.ui.screenfive.ScreenFiveFragment


/**
 * Created by Andri Dwi Utomo on 14/5/2022.
 * Mallsampah Indonesia
 * andri@mallsampah.com
 */
class ScreenBaseEventFragment : BaseViewBindingFragment<FragmentScreenBaseEventBinding>() {

    override fun doViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentScreenBaseEventBinding.inflate(inflater, container, false)

    private var state: String = "maps"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ft: FragmentTransaction = fragmentManager?.beginTransaction()!!
        ft.replace(R.id.frameLayout, ScreenThreeFragment(), "ScreenThree")
        ft.commit()
        ft.addToBackStack(null)

        binding.toolbar.tvToolbarTitle.text = "Events"
        binding.toolbar.ivToolbarIconLeft.setOnClickListener {
            navController.popBackStack()
        }

        binding.toolbar.ivToolbarIconMap.setOnClickListener {
            if (state.equals("list")) {
                binding.toolbar.ivToolbarIconMap.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_map_24))
                state = "maps"
                val ft: FragmentTransaction = fragmentManager?.beginTransaction()!!
                ft.replace(R.id.frameLayout, ScreenThreeFragment(), "ScreenThree")
                ft.commit()
                ft.addToBackStack(null)
            } else {
                binding.toolbar.ivToolbarIconMap.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_format_list_bulleted_24))
                state = "list"

                val ft: FragmentTransaction = fragmentManager?.beginTransaction()!!
                ft.replace(R.id.frameLayout, ScreenFiveFragment(), "ScreenFive")
                ft.commit()
                ft.addToBackStack(null)
            }
        }
    }
}