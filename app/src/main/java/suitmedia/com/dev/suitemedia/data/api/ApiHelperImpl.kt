package suitmedia.com.dev.suitemedia.data.api

import retrofit2.Call
import suitmedia.com.dev.suitemedia.data.model.Responses
import javax.inject.Inject


/**
 * Created by Andri Dwi Utomo on 13/5/2022.
 * Mallsampah Indonesia
 * andri@mallsampah.com
 */
class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper{
    override suspend fun getUsers(page: Int, perPage: Int) = apiService.getUsers(page, perPage)
}