package suitmedia.com.dev.suitemedia.ui.screenone.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import suitmedia.com.dev.suitemedia.core.viewmodel.BaseViewModel
import suitmedia.com.dev.suitemedia.data.local.entities.ProfileEntities
import suitmedia.com.dev.suitemedia.data.repository.AppRepository
import javax.inject.Inject


/**
 * Created by Andri Dwi Utomo on 13/5/2022.
 * Mallsampah Indonesia
 * andri@mallsampah.com
 */
@HiltViewModel
class ScreenOneViewModel @Inject constructor(
    private val appRepository: AppRepository
) : BaseViewModel<ScreenOneViewModel.Event, ScreenOneViewModel.State>() {

    sealed class Event {
        object OnViewCreated : Event()
        data class OnSaveProfile(val name: String, val image: Uri) : Event()
    }

    sealed class State {
        data class ViewCreated(val profile: ProfileEntities): State()
        object Loading: State()
        data class RequestFailed(val message: String) : State()
        object Success: State()
    }

    init {

    }

    private fun resolveSaveProfile(name: String, image: Uri) {
        pushState(State.Loading)
        viewModelScope.launch {
            var profil = ProfileEntities(1, name, image)
            if (appRepository.getProfile() != null) {
                appRepository.updateProfile(name, image)
            } else {
                appRepository.insertToProfile(profil)
            }
            pushState(State.Success)
        }

    }

    private fun resolveGetProfile() {
        pushState(State.Loading)
        viewModelScope.launch {
            appRepository.deleteEvent()
            appRepository.deleteGuest()
            var getProfile = appRepository.getProfile()
            if (getProfile != null) {
                pushState(State.ViewCreated(ProfileEntities(getProfile.id, getProfile.name, getProfile.image)))
            }
        }

    }

    override fun onEventReceived(event: Event) {
        when(event) {
            is Event.OnViewCreated -> {
                resolveGetProfile()
            }
            is Event.OnSaveProfile -> {
                resolveSaveProfile(event.name, event.image)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}