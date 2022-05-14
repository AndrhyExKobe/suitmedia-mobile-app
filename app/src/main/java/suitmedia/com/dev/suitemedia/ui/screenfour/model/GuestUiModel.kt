package suitmedia.com.dev.suitemedia.ui.screenfour.model

import android.net.Uri


/**
 * Created by Andri Dwi Utomo on 14/5/2022.
 * Mallsampah Indonesia
 * andri@mallsampah.com
 */
interface GuestUiModel

data class OnGuestItemModel(
    val image: Uri,
    val title: String
) : GuestUiModel