package suitmedia.com.dev.suitemedia.ui.screenfour.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import suitmedia.com.dev.suitemedia.core.CommonsPreferenceManager
import suitmedia.com.dev.suitemedia.core.viewmodel.BaseViewModel
import suitmedia.com.dev.suitemedia.data.local.entities.GuestEntities
import suitmedia.com.dev.suitemedia.data.model.Responses
import suitmedia.com.dev.suitemedia.data.repository.AppRepository
import suitmedia.com.dev.suitemedia.ui.screenfour.model.OnGuestItemModel
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject


/**
 * Created by Andri Dwi Utomo on 14/5/2022.
 * Mallsampah Indonesia
 * andri@mallsampah.com
 */
@HiltViewModel
class ScreenFourViewModel @Inject constructor(
    private val appRepository: AppRepository,
    private val commonsPreferenceManager: CommonsPreferenceManager
) : BaseViewModel<ScreenFourViewModel.Event, ScreenFourViewModel.State>() {

    sealed class Event {
        data class OnSelectGuest(val name: String) : Event()
        data class OnViewCreated(val page: Int, val perpage: Int) : Event()
    }

    sealed class State {
        data class OnViewCreated(val guestItemModel: List<OnGuestItemModel>, val total: Int) : State()
        object Loading: State()
        object Success: State()
        data class RequestFailed(val message: String) : State()
    }

    init {

    }
    private fun resolveSaveGuest(name: String) {
        pushState(State.Loading)
        viewModelScope.launch {
            var getGuest = appRepository.getGuest()
            if (getGuest != null) {
                appRepository.updateGuest(name)
            } else {
                appRepository.insertGuest(GuestEntities(1, name))
            }

            pushState(State.Success)
        }

    }

    private fun resolveGetGuest(page: Int, perpage: Int) {
        pushState(State.Loading)
        viewModelScope.launch {
            appRepository.getGuestApi(page, perpage)
                ?.enqueue(object : Callback<Responses> {
                    override fun onResponse(call: Call<Responses>,
                                            response: Response<Responses>
                    ) {
                            if (response.isSuccessful) {
                                try {

                                    var data = response.body()?.data

                                    commonsPreferenceManager.setListGuest(data!!)

                                    var listGuest : List<OnGuestItemModel> = listOf()
                                    data?.map {
                                        listGuest = listGuest + OnGuestItemModel(
                                            Uri.parse(it.avatar),
                                            it.firstName+" "+it.lastName
                                        )
                                    }

                                    var totalData = response.body()?.total

                                    pushState(State.OnViewCreated(listGuest, totalData!!))
                                } catch (e: IOException) {
                                    pushState(State.RequestFailed("Terjadi kesalahan"))
                                }

                            }


                    }

                    override fun onFailure(call: Call<Responses>, t: Throwable) {
                        Log.v("isinya error", t.message.toString())
                        var data = commonsPreferenceManager.listGuest()
                        if (data != null) {
                            var listGuest : List<OnGuestItemModel> = listOf()
                            data?.map {
                                listGuest = listGuest + OnGuestItemModel(
                                    Uri.parse(it.avatar),
                                    it.firstName+" "+it.lastName
                                )
                            }

                            var totalData = data?.size

                            pushState(State.OnViewCreated(listGuest, totalData!!))

                        } else {
                            pushState(State.RequestFailed(t.message.toString()))
                        }

                    }

                })
        }

    }

    override fun onEventReceived(event: Event) {
        when(event) {
            is Event.OnSelectGuest -> {
                resolveSaveGuest(event.name)
            }
            is Event.OnViewCreated -> {
                resolveGetGuest(event.page, event.perpage)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}