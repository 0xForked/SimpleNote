package id.aasumitro.simplenote.utils

import android.arch.persistence.room.TypeConverter
import java.util.*


/**
 * Created by Agus Adhi Sumitro on 26/01/2018.
 * https://asmith.my.id
 * aasumitro@gmail.com
 */
class DateConverter {
    @TypeConverter
    fun toDate(timestamp: Long?): Date? {
        return when (timestamp) {
            null -> null
            else -> Date(timestamp)
        }
    }

    @TypeConverter
    fun toTimestamp(date: Date?): Long? {
        return date?.time
    }
}