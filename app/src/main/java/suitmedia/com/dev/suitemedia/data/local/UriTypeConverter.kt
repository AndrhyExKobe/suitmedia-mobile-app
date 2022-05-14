package suitmedia.com.dev.suitemedia.data.local

import android.net.Uri
import androidx.room.TypeConverter


/**
 * Created by Andri Dwi Utomo on 13/5/2022.
 * Mallsampah Indonesia
 * andri@mallsampah.com
 */
class UriTypeConverter {
    @TypeConverter
    fun fromUriString(value: String?): Uri? = value?.let { Uri.parse(it) }

    @TypeConverter
    fun uriToString(uri: Uri?): String? = uri?.toString()
}