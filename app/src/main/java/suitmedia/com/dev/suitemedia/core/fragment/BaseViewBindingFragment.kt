package suitmedia.com.dev.suitemedia.core.fragment

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.viewbinding.ViewBinding

abstract class BaseViewBindingFragment<V : ViewBinding> : BaseFragment() {

    private var _binding: V? = null

    protected val binding: V
        get() = _binding!!

    override fun layoutResourceId(): Int = -1

    protected abstract fun doViewBinding(inflater: LayoutInflater, container: ViewGroup?): V

    protected open val isStatusBarInclude = false

    protected open fun doOnCreateView() {
        //Do on create view
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = doViewBinding(inflater, container)
        /*setHasOptionsMenu(menuResourceId() != null || showBackButton() == true)*/
        doOnCreateView()
        /*setTransparentStatusBar(requireActivity())*/
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setTransparentStatusBar(activity : Activity) {
        //make fully Android Transparent Status bar
        /*if (Build.VERSION.SDK_INT in 19..20) {
            setWindowFlag(
                activity,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                isStatusBarInclude
            )
        }
        if (Build.VERSION.SDK_INT >= 19) {
            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }*/
        /*if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(
                activity,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                false
            )

            if(isStatusBarInclude)
                activity.window.statusBarColor = Color.TRANSPARENT
            else {
                activity.window.statusBarColor = Color.parseColor(R.color.)
            }
        }

        if(Build.VERSION.SDK_INT >= 23) {
            if(isStatusBarInclude)
                activity.window.statusBarColor = Color.TRANSPARENT
            else {
                activity.window.statusBarColor = Color.GREEN
                activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }*/
    }

    companion object{
        @JvmStatic
        fun setWindowFlag(activity: Activity, bits: Int, on: Boolean) {
            val win = activity.window
            val winParams: WindowManager.LayoutParams = win.attributes
            if (on) {
                winParams.flags.and(bits)
                win.attributes = winParams
            } else {
                winParams.flags = winParams.flags and bits.inv()
            }
        }
    }
}