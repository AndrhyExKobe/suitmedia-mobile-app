package suitmedia.com.dev.suitemedia.ui.screentwo.viewmodel

import android.net.Uri
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import suitmedia.com.dev.suitemedia.core.viewmodel.BaseViewModel
import suitmedia.com.dev.suitemedia.data.local.entities.EventEntities
import suitmedia.com.dev.suitemedia.data.local.entities.ProfileEntities
import suitmedia.com.dev.suitemedia.data.repository.AppRepository
import javax.inject.Inject


/**
 * Created by Andri Dwi Utomo on 13/5/2022.
 * Mallsampah Indonesia
 * andri@mallsampah.com
 */
@HiltViewModel
class ScreenTwoViewModel @Inject constructor(
    private val appRepository: AppRepository
) : BaseViewModel<ScreenTwoViewModel.Event, ScreenTwoViewModel.State>() {

    sealed class Event {
        object OnViewCreated : Event()
    }

    sealed class State {
        data class ViewCreated(val name: String, val event: String, val guest: String): State()
        object Loading: State()
        object Success: State()
    }

    init {

    }
    private fun resolveGetProfile() {
        pushState(State.Loading)
        viewModelScope.launch {
            var getProfile = appRepository.getProfile()
            var getEvent = appRepository.getEvent()
            var getGuest = appRepository.getGuest()
            var name = ""
            var event = "Choose Event"
            var guest = "Choose Guest"
            if (getProfile != null) {
                name = getProfile.name
            }
            if (getEvent != null) {
                event = getEvent.title
            }

            if (getGuest != null) {
                guest = getGuest.name
            }

            pushState(State.ViewCreated(name, event, guest))
        }

    }

    override fun onEventReceived(event: Event) {
        when(event) {
            is Event.OnViewCreated -> {
                resolveGetProfile()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}