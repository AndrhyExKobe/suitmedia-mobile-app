package suitmedia.com.dev.suitemedia.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import suitmedia.com.dev.suitemedia.data.local.dao.DraftDao
import suitmedia.com.dev.suitemedia.data.local.entities.EventEntities
import suitmedia.com.dev.suitemedia.data.local.entities.GuestEntities
import suitmedia.com.dev.suitemedia.data.local.entities.ProfileEntities


/**
 * Created by Andri Dwi Utomo on 13/5/2022.
 * Mallsampah Indonesia
 * andri@mallsampah.com
 */
@Database(entities = [ProfileEntities::class, EventEntities::class, GuestEntities::class], version = 20, exportSchema = false)
@TypeConverters(UriTypeConverter::class, ListConverter::class)
abstract class MasterDatabase : RoomDatabase() {
    abstract val draftDao: DraftDao
}