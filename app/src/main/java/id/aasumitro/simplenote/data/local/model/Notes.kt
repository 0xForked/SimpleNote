package id.aasumitro.simplenote.data.local.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import id.aasumitro.simplenote.utils.DateConverter
import java.util.*

/**
 * Created by Agus Adhi Sumitro on 25/01/2018.
 * https://asmith.my.id
 * aasumitro@gmail.com
 */

@Entity(tableName = "notes")
data class Notes(
        @PrimaryKey(autoGenerate = true)
        var id: Long?,
        @ColumnInfo(name = "title")
        val title: String?,
        @ColumnInfo(name = "description")
        var description: String?,
        @ColumnInfo(name = "is_locked")
        var isLocked: Boolean?,
        @ColumnInfo(name = "created_at")
        @TypeConverters(DateConverter::class)
        var createdAt: Date?,
        @ColumnInfo(name = "updated_at")
        @TypeConverters(DateConverter::class)
        var updatedAt: Date?
)


