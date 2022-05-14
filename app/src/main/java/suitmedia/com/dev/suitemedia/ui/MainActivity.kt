package suitmedia.com.dev.suitemedia.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import com.google.android.play.core.appupdate.AppUpdateManager
import dagger.hilt.android.AndroidEntryPoint
import suitmedia.com.dev.suitemedia.R
import suitmedia.com.dev.suitemedia.core.navigation.NavActivity

@AndroidEntryPoint
class MainActivity : NavActivity(), NavController.OnDestinationChangedListener{

    override val navigationHostId: Int
        get() = R.id.mainNavHost

    /*private val prefManager by lazy { CommonsPreferenceManager(this) }*/


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        navController.addOnDestinationChangedListener(this)

    }

    override fun onDestroy() {
        navController.removeOnDestinationChangedListener(this)
        super.onDestroy()
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {

    }
}