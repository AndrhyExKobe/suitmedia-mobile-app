package suitmedia.com.dev.suitemedia.data.local.entities

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * Created by Andri Dwi Utomo on 14/5/2022.
 * Mallsampah Indonesia
 * andri@mallsampah.com
 */
@Entity(tableName = "event")
data class EventEntities (
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "title")
    val title: String,

)