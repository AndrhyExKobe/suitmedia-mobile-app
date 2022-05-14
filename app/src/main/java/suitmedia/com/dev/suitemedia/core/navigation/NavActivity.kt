package suitmedia.com.dev.suitemedia.core.navigation

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

abstract class NavActivity : AppCompatActivity() {
    protected abstract val navigationHostId: Int

    protected val navController: NavController
        get() {
            return (supportFragmentManager.findFragmentById(navigationHostId) as NavHostFragment).navController
        }
}