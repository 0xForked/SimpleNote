package id.aasumitro.simplenote.data.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import id.aasumitro.simplenote.data.local.model.Notes
import id.aasumitro.simplenote.data.local.model.NotesTrash
import io.reactivex.Flowable




/**
 * Created by Agus Adhi Sumitro on 25/01/2018.
 * https://asmith.my.id
 * aasumitro@gmail.com
 */

@Dao
interface NoteDAO {

    @Query("SELECT * FROM notes")
    fun getAllNotes(): Flowable<List<Notes>>

    @Query("SELECT * FROM notes WHERE id=:id")
    fun getNoteById(id: Long): Notes

    @Query("SELECT * FROM notes WHERE title=:title")
    fun getNoteByTitle(title: String): Flowable<List<Notes>>

    @Insert
    fun postNotes(notes: Notes)

    @Update
    fun putNotes(notes: Notes)

    @Query("DELETE FROM notes WHERE id=:id")
    fun deleteNoteById(id: Long): Int

    //For Trash (Recycler Bin)
    @Insert
    fun postToTrash(notes: NotesTrash)

    @Query("SELECT * FROM NotesTrash")
    fun getTrashList(): Flowable<List<NotesTrash>>

    @Query("DELETE FROM NotesTrash")
    fun clearTrash()

    @Query("DELETE FROM NotesTrash WHERE id=:id")
    fun deleteTrashById(id: Long): Int

}