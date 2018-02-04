package id.aasumitro.simplenote.data.local.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import id.aasumitro.simplenote.data.local.NoteDAO
import id.aasumitro.simplenote.data.local.model.Notes
import id.aasumitro.simplenote.data.local.model.NotesTrash
import id.aasumitro.simplenote.utils.DateConverter


/**
 * Created by Agus Adhi Sumitro on 25/01/2018.
 * https://asmith.my.id
 * aasumitro@gmail.com
 */

@Database(entities = [(Notes::class), (NotesTrash::class)], version = 1)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun notesDao(): NoteDAO
}