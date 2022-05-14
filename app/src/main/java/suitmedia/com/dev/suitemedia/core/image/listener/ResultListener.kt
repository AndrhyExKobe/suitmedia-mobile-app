package suitmedia.com.dev.suitemedia.core.image.listener


/**
 * Created by Andri Dwi Utomo on 14/11/2021.
 * Mallsampah Indonesia
 * andri@mallsampah.com
 */
internal interface ResultListener<T> {

    fun onResult(t: T?)
}