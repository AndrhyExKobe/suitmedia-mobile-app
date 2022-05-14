package suitmedia.com.dev.suitemedia.di.main

import android.content.Context
import android.content.res.Resources
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import suitmedia.com.dev.suitemedia.BuildConfig
import suitmedia.com.dev.suitemedia.data.api.ApiHelper
import suitmedia.com.dev.suitemedia.data.api.ApiHelperImpl
import suitmedia.com.dev.suitemedia.data.api.ApiService
import suitmedia.com.dev.suitemedia.data.local.MasterDatabase
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton


/**
 * Created by Andri Dwi Utomo on 13/5/2022.
 * Mallsampah Indonesia
 * andri@mallsampah.com
 */
@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    fun provideBaseUrl() = BuildConfig.API_URL

    @Provides
    @Singleton
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()
    } else OkHttpClient
        .Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .build()


    @Provides
    @Singleton
    @Named("retrofit_base")
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        BASE_URL: String
    ): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideAppResource(context: Context) : Resources = context.applicationContext.resources

    @Provides
    @Singleton
    fun provideApiService(@Named("retrofit_base") retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideApiHelper(apiHelper: ApiHelperImpl): ApiHelper = apiHelper

    @Singleton // Tell Dagger-Hilt to create a singleton accessible everywhere in ApplicationCompenent (i.e. everywhere in the application)
    @Provides
    fun provideMasterDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        MasterDatabase::class.java,
        "suitemedia.db"
    ).fallbackToDestructiveMigration().fallbackToDestructiveMigrationFrom().build()

    @Singleton
    @Provides
    fun provideDraftDao(db: MasterDatabase) = db.draftDao

}