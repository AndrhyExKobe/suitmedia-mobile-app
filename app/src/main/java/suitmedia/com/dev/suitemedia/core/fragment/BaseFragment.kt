package suitmedia.com.dev.suitemedia.core.fragment

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

@AndroidEntryPoint
abstract class BaseFragment : Fragment(), CoroutineScope {
    protected val job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    protected abstract fun layoutResourceId(): Int

    protected open fun menuResourceId(): Int? = null

    protected open val navController: NavController by lazy { findNavController() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutResourceId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setupToolbar() {
        (requireActivity() as AppCompatActivity).apply {
            /*setSupportActionBar(getToolbar())
            title = this@BaseFragment.getTitle()
            showBackButton()?.let {
                supportActionBar?.setDisplayHomeAsUpEnabled(it)
                ResourcesCompat.getDrawable(resources, backButtonIcon(), requireContext().theme)
                    ?.also { icon ->
                        icon.colorFilter = PorterDuffColorFilter(
                            ResourcesCompat.getColor(resources, backButtonColor(), null),
                            PorterDuff.Mode.SRC_IN
                        )
                        supportActionBar?.setHomeAsUpIndicator(icon)
                    }
            }
            setupToolbarMenu()*/
        }
    }
}