package suitmedia.com.dev.suitemedia.app

import androidx.multidex.MultiDexApplication
import com.google.android.libraries.places.api.Places
import dagger.hilt.android.HiltAndroidApp
import suitmedia.com.dev.suitemedia.R


/**
 * Created by Andri Dwi Utomo on 13/5/2022.
 * Mallsampah Indonesia
 * andri@mallsampah.com
 */

@HiltAndroidApp
class Apps : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        Places.initialize(applicationContext, getString(R.string.google_api_key))
        Places.createClient(this)
    }
}