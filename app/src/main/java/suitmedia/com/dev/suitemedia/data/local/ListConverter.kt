package suitmedia.com.dev.suitemedia.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


/**
 * Created by Andri Dwi Utomo on 13/5/2022.
 * Mallsampah Indonesia
 * andri@mallsampah.com
 */
class ListConverter {
    @TypeConverter
    fun saveIntList(listOfString: List<Int?>?): String? {
        return Gson().toJson(listOfString)
    }

    @TypeConverter
    fun getIntList(listOfString: String?): List<Int?>? {
        return Gson().fromJson(
            listOfString,
            object : TypeToken<List<Int?>?>() {}.type
        )
    }

}