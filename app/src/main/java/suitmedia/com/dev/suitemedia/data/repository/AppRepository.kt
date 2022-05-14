package suitmedia.com.dev.suitemedia.data.repository

import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import suitmedia.com.dev.suitemedia.data.api.ApiHelper
import suitmedia.com.dev.suitemedia.data.api.ApiService
import suitmedia.com.dev.suitemedia.data.local.dao.DraftDao
import suitmedia.com.dev.suitemedia.data.local.entities.EventEntities
import suitmedia.com.dev.suitemedia.data.local.entities.GuestEntities
import suitmedia.com.dev.suitemedia.data.local.entities.ProfileEntities
import javax.inject.Inject


/**
 * Created by Andri Dwi Utomo on 13/5/2022.
 * Mallsampah Indonesia
 * andri@mallsampah.com
 */
class AppRepository  @Inject constructor(private val draftDao: DraftDao, private val apiHelper: ApiHelper){
    suspend fun insertToProfile(profile: ProfileEntities) = withContext(Dispatchers.IO) { draftDao.addRegProfile(profile) }
    suspend fun getProfile() = withContext(Dispatchers.IO) { draftDao.getRegProfile() }
    suspend fun updateProfile(name: String, image: Uri) = withContext(Dispatchers.IO) { draftDao.updateProfile(name, image) }

    suspend fun insertEvent(eventEntities: EventEntities) = withContext(Dispatchers.IO) { draftDao.addEvent(eventEntities) }
    suspend fun getEvent() = withContext(Dispatchers.IO) { draftDao.getEvent() }
    suspend fun updateEvent(name: String) = withContext(Dispatchers.IO) { draftDao.updateEvent(name) }
    suspend fun deleteEvent() = withContext(Dispatchers.IO) { draftDao.deleteEvent() }

    suspend fun insertGuest(guestEntities: GuestEntities) = withContext(Dispatchers.IO) { draftDao.addGuest(guestEntities) }
    suspend fun getGuest() = withContext(Dispatchers.IO) { draftDao.getGuest() }
    suspend fun updateGuest(name: String) = withContext(Dispatchers.IO) { draftDao.updateGuest(name) }
    suspend fun deleteGuest() = withContext(Dispatchers.IO) { draftDao.deleteGuest() }

    suspend fun getGuestApi(page: Int, perpage: Int) = withContext(Dispatchers.IO) { apiHelper.getUsers(page, perpage) }
}