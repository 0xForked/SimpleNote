package id.aasumitro.simplenote.ui.main.fragment.list.trash.rvtrash

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import id.aasumitro.simplenote.R
import id.aasumitro.simplenote.data.NotesLocalRepository
import id.aasumitro.simplenote.data.local.model.NotesTrash
import java.util.*


/**
 * Created by Agus Adhi Sumitro on 25/01/2018.
 * https://asmith.my.id
 * aasumitro@gmail.com
 */

class RecyclerTrashAdapter(private val noteList: ArrayList<NotesTrash>,
                           private val listener: RecyclerTrashListener) :
        RecyclerView.Adapter<RecyclerTrashHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerTrashHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_note_list, parent, false)
        return RecyclerTrashHolder(view)
    }

    override fun getItemCount(): Int = noteList.count()

    override fun onBindViewHolder(holder: RecyclerTrashHolder, position: Int) =
            holder.bind(noteList[position], listener)

    fun restoreBack(position: Int) {
        position.let {
            NotesLocalRepository.restoreFromTrash(
                    noteList[it].id!!.toLong(),
                    noteList[it].title.toString(),
                    noteList[it].description.toString(),
                    noteList[it].createdAt!!,
                    Date())
            noteList.removeAt(it)
            notifyItemRemoved(it)
        }
    }

    fun deleteTrashItem(position: Int) {
        position.let {
            NotesLocalRepository.deleteItemFromTrashById(noteList[it].id!!.toLong())
            noteList.removeAt(it)
            notifyItemRemoved(it)
        }
    }

}