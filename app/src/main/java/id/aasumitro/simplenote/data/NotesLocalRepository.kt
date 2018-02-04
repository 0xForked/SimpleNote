package id.aasumitro.simplenote.data

import id.aasumitro.simplenote.data.local.model.Notes
import id.aasumitro.simplenote.data.local.model.NotesTrash
import id.aasumitro.simplenote.data.local.room.CreatorDatabase
import io.reactivex.Flowable
import java.util.*


/**
 * Created by Agus Adhi Sumitro on 25/01/2018.
 * https://asmith.my.id
 * aasumitro@gmail.com
 */

object NotesLocalRepository {

    private val notesDao = CreatorDatabase.database.notesDao()

    /**
     * REPO for Main
     * @GET list from main table
     * @POST add new note
     * @DELETE delete item/note by id
     * @UPDATE update item/note by id
     */
    fun getAllNotes(): Flowable<List<Notes>> = notesDao.getAllNotes()

    fun postNote(title: String, description: String,
                   isLock: Boolean, createdAt: Date, updatedAt: Date) {

        val notes = Notes(null, title, description, isLock, createdAt, updatedAt)
        notesDao.postNotes(notes)
    }

    fun updateNote() {
        print("update me please")
    }

    fun sendToRecyclerBin(id: Long, title: String, description: String,
                          createdAt: Date, deletedAt: Date) {
        postToTrash(title, description, createdAt, deletedAt)
        notesDao.deleteNoteById(id)
    }

    /**
     * REPO for Trash
     * @Get list from trash table
     * @POST add note from notes table after deleted
     * @CLEAR this table
     */
    fun getAllTrashList(): Flowable<List<NotesTrash>> = notesDao.getTrashList()

    private fun postToTrash(title: String, description: String, createdAt: Date, deletedAt: Date) {
        val notes = NotesTrash(null, title, description, createdAt, deletedAt)
        notesDao.postToTrash(notes)
    }

    fun clearThisTrash() = notesDao.clearTrash()

    fun restoreFromTrash(id: Long, title: String, description: String,
                         createdAt: Date, updatedAt: Date) {
        postNote(title, description, false, createdAt, updatedAt)
        notesDao.deleteTrashById(id)
    }

}