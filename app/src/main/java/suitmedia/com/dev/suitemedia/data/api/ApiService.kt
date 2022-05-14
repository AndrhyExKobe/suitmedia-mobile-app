package suitmedia.com.dev.suitemedia.data.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query
import suitmedia.com.dev.suitemedia.data.model.Responses


/**
 * Created by Andri Dwi Utomo on 13/5/2022.
 * Mallsampah Indonesia
 * andri@mallsampah.com
 */
interface ApiService {
    @GET("users")
    fun getUsers(@Query("page") page: Int, @Query("per_page") perpage: Int): Call<Responses>

}