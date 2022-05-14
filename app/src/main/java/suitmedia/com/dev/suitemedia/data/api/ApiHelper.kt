package suitmedia.com.dev.suitemedia.data.api

import retrofit2.Call
import suitmedia.com.dev.suitemedia.data.model.Responses


/**
 * Created by Andri Dwi Utomo on 13/5/2022.
 * Mallsampah Indonesia
 * andri@mallsampah.com
 */
interface ApiHelper {
    suspend fun getUsers(page: Int, perPage: Int): Call<Responses>
}