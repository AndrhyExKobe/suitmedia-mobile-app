package suitmedia.com.dev.suitemedia.core.extension

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner

fun Fragment.windowSoftInputMode(mode : Int){
    requireActivity().window.setSoftInputMode(mode)
}

fun Fragment.addBackCallback(owner : LifecycleOwner, callback : OnBackPressedCallback){
    requireActivity().onBackPressedDispatcher.addCallback(owner,callback)
}