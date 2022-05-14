package suitmedia.com.dev.suitemedia.ui.screenthree.model

import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes


/**
 * Created by Andri Dwi Utomo on 14/5/2022.
 * Mallsampah Indonesia
 * andri@mallsampah.com
 */
interface EventUiModel

data class OnEventItemModel(
    @DrawableRes val img: Int,
    val title: String,
    val desc: String,
    val date: String,
    val time: String,
    val lat: Double,
    val long: Double
) : EventUiModel