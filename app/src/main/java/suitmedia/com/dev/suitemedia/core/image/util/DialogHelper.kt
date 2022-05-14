package suitmedia.com.dev.suitemedia.core.image.util


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import suitmedia.com.dev.suitemedia.R
import suitmedia.com.dev.suitemedia.core.image.constant.ImageProvider
import suitmedia.com.dev.suitemedia.core.image.listener.DismissListener
import suitmedia.com.dev.suitemedia.core.image.listener.ResultListener

/**
 * Created by Andri Dwi Utomo on 14/11/2021.
 * Mallsampah Indonesia
 * andri@mallsampah.com
 */
internal object DialogHelper {

    /**
     * Show Image Provide Picker Dialog. This will streamline the code to pick/capture image
     *
     */
    fun showChooseAppDialog(
        context: Context,
        listener: ResultListener<ImageProvider>,
        dismissListener: DismissListener?
    ) {
        val layoutInflater = LayoutInflater.from(context)
        val customView = layoutInflater.inflate(R.layout.dialog_choose_app, null)

        val dialog = AlertDialog.Builder(context)
            .setTitle(R.string.pilih_foto_dari)
            .setView(customView)
            .setOnCancelListener {
                listener.onResult(null)
            }
            .setNegativeButton(R.string.batal) { _, _ ->
                listener.onResult(null)
            }
            .setOnDismissListener {
                dismissListener?.onDismiss()
            }
            .show()

        // Handle Camera option click
        customView.findViewById<View>(R.id.lytCameraPick).setOnClickListener {
            listener.onResult(ImageProvider.CAMERA)
            dialog.dismiss()
        }

        // Handle Gallery option click
        customView.findViewById<View>(R.id.lytGalleryPick).setOnClickListener {
            listener.onResult(ImageProvider.GALLERY)
            dialog.dismiss()
        }
    }
}