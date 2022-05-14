package suitmedia.com.dev.suitemedia.core

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import suitmedia.com.dev.suitemedia.data.model.Users
import suitmedia.com.dev.suitemedia.ui.screenfour.model.OnGuestItemModel
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class CommonsPreferenceManager @Inject constructor(@ApplicationContext context : Context) {

    companion object {
        private const val PREF_NAME = "suitmedia.common.pref"
        private const val LIST_GUEST = "guest.list"
    }

    private var preferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun listGuest(): List<Users>? {
        val gson = Gson()
        val json: String = preferences.getString(LIST_GUEST, "")!!
        val type = object : TypeToken<List<Users?>?>() {}.type

        return Gson().fromJson(json, type)
    }

    fun setListGuest(list: List<Users>) {
        val convertedData = Gson().toJson(list)

        preferences.edit(commit = true) {
            putString(LIST_GUEST, convertedData)
        }
    }
}