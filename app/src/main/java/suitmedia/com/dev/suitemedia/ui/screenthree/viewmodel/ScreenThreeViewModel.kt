package suitmedia.com.dev.suitemedia.ui.screenthree.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import suitmedia.com.dev.suitemedia.core.viewmodel.BaseViewModel
import suitmedia.com.dev.suitemedia.data.local.entities.EventEntities
import suitmedia.com.dev.suitemedia.data.local.entities.ProfileEntities
import suitmedia.com.dev.suitemedia.data.repository.AppRepository
import suitmedia.com.dev.suitemedia.ui.screentwo.viewmodel.ScreenTwoViewModel
import javax.inject.Inject


/**
 * Created by Andri Dwi Utomo on 14/5/2022.
 * Mallsampah Indonesia
 * andri@mallsampah.com
 */
@HiltViewModel
class ScreenThreeViewModel @Inject constructor(
    private val appRepository: AppRepository
) : BaseViewModel<ScreenThreeViewModel.Event, ScreenThreeViewModel.State>() {

    sealed class Event {
        data class OnSelectEvent(val name: String) : Event()
    }

    sealed class State {
        object Loading: State()
        object Success: State()
    }

    init {

    }
    private fun resolveSaveEvent(name: String) {
        pushState(State.Loading)
        viewModelScope.launch {
            var getEvent = appRepository.getEvent()
            if (getEvent != null) {
                appRepository.updateEvent(name)
            } else {
                appRepository.insertEvent(EventEntities(1, name))
            }

            pushState(State.Success)
        }

    }

    override fun onEventReceived(event: Event) {
        when(event) {
            is Event.OnSelectEvent -> {
                resolveSaveEvent(event.name)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}