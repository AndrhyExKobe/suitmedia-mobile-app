package suitmedia.com.dev.suitemedia.core.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


/**
 * Created by Andri Dwi Utomo on 13/5/2022.
 * Mallsampah Indonesia
 * andri@mallsampah.com
 */
abstract class BaseViewModel<Event, State> : ViewModel() {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state

    protected fun pushState(state: State) {
        _state.value = state
    }

    abstract fun onEventReceived(event: Event)
}