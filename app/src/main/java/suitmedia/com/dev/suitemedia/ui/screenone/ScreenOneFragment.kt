package suitmedia.com.dev.suitemedia.ui.screenone

import android.app.Activity
import android.app.AlertDialog
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import suitmedia.com.dev.suitemedia.R
import suitmedia.com.dev.suitemedia.core.extension.observe
import suitmedia.com.dev.suitemedia.core.fragment.BaseViewBindingFragment
import suitmedia.com.dev.suitemedia.databinding.FragmentScreenOneBinding
import suitmedia.com.dev.suitemedia.utils.CustomSetting
import suitmedia.com.dev.suitemedia.core.image.ImagePicker
import suitmedia.com.dev.suitemedia.ui.screenone.viewmodel.ScreenOneViewModel


/**
 * Created by Andri Dwi Utomo on 13/5/2022.
 * Mallsampah Indonesia
 * andri@mallsampah.com
 */
class ScreenOneFragment: BaseViewBindingFragment<FragmentScreenOneBinding>() {

    override fun doViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentScreenOneBinding.inflate(inflater, container, false)

    private var image: Uri? = null

    private val viewModel: ScreenOneViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CustomSetting.changeColorActionBar(requireActivity(), resources.getColor(R.color.secondaryOrange))

        viewModel.onEventReceived(ScreenOneViewModel.Event.OnViewCreated)

        bindViewModel()

        binding.layProfile.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(context)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                dialogBuilder.setView(R.layout.layout_dialog_image_picker)
            } else {
                val dialogView = LayoutInflater.from(context)
                    .inflate(R.layout.layout_dialog_image_picker, null, false)
                dialogBuilder.setView(dialogView)
            }
            dialogBuilder.create()
                .also {
                    it.show()
                    it.findViewById<TextView>(R.id.tvDialogTitle).text = getString(R.string.pilih_foto_dari)
                    it.findViewById<View>(R.id.tvActionGallery).setOnClickListener { _ ->
                        it.dismiss()
                        openGallery()
                    }
                    it.findViewById<View>(R.id.tvActionCamera).setOnClickListener { _ ->
                        it.dismiss()
                        openCamera()
                    }
                }
        }

        binding.btnCheck.setOnClickListener {
            if (CustomSetting.isPalindromeString(binding.etPalindrome.text.toString())) {
                Toast.makeText(requireContext(), "Text is palindrome", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Text is not palindrome", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnNext.setOnClickListener{
            if (image == null) {
                Toast.makeText(requireContext(), "Please Insert Photo", Toast.LENGTH_SHORT).show()
            } else if(binding.etName.text.toString().equals("")) {
                Toast.makeText(requireContext(), "Please Insert Name", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.onEventReceived(ScreenOneViewModel.Event.OnSaveProfile(binding.etName.text.toString(),
                    image!!
                ))
            }
        }
    }

    private fun openCamera() {
        ImagePicker.with(requireActivity())
            .crop()
            .cameraOnly()
            .compress(512)
            .maxResultSize(625, 625)
            .createIntent { i -> startForCameraImageResult.launch(i) }
    }

    private fun openGallery() {
        ImagePicker.with(requireActivity())
            .cropSquare()
            .galleryOnly()
            .galleryMimeTypes(
                mimeTypes = arrayOf(
                    "image/png",
                    "image/jpg",
                    "image/jpeg"
                )
            )
            .compress(512)
            .maxResultSize(625, 625)
            .createIntent { i -> startForGalleryImageResult.launch(i) }
    }

    private val startForCameraImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == Activity.RESULT_OK) {
                val fileUri = data?.data!!

                binding.image = fileUri
                image = fileUri

            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Task Cancelled", Toast.LENGTH_SHORT).show()

            }
        }

    private val startForGalleryImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == Activity.RESULT_OK) {
                val fileUri = data?.data!!

                binding.image = fileUri
                image = fileUri

            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Task Cancelled", Toast.LENGTH_SHORT).show()

            }
        }

    private fun bindViewModel(){
        observe(viewModel.state) {
            when (it) {
                is ScreenOneViewModel.State.ViewCreated -> {
                    binding.image = it.profile.image
                    image = it.profile.image
                    binding.etName.setText(it.profile.name)
                }
                is ScreenOneViewModel.State.Success -> {
                    Toast.makeText(requireContext(), "Success to Save", Toast.LENGTH_SHORT).show()
                    val destination = ScreenOneFragmentDirections.actionScreenOneFragmentToScreenTwoFragment()
                    navController.navigate(destination)
                }
            }
        }
    }
}