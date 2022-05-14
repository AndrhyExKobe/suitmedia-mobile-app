package suitmedia.com.dev.suitemedia.data.local.dao

import android.net.Uri
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import suitmedia.com.dev.suitemedia.data.local.entities.EventEntities
import suitmedia.com.dev.suitemedia.data.local.entities.GuestEntities
import suitmedia.com.dev.suitemedia.data.local.entities.ProfileEntities


/**
 * Created by Andri Dwi Utomo on 13/5/2022.
 * Mallsampah Indonesia
 * andri@mallsampah.com
 */

@Dao
interface DraftDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addRegProfile(profile: ProfileEntities)

    @Query("select * from profile where id=1")
    fun getRegProfile(): ProfileEntities?

    @Query("update profile set name=:name, image=:image where id=1")
    fun updateProfile(name: String, image: Uri): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addEvent(event: EventEntities)

    @Query("select * from event where id=1")
    fun getEvent(): EventEntities?

    @Query("update event set title=:title where id=1")
    fun updateEvent(title: String): Int

    @Query("delete from event where id=1")
    fun deleteEvent(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addGuest(guestEntities: GuestEntities)

    @Query("select * from guest where id=1")
    fun getGuest(): GuestEntities?

    @Query("update guest set name=:name where id=1")
    fun updateGuest(name: String): Int

    @Query("delete from guest where id=1")
    fun deleteGuest(): Int
}