package id.aasumitro.simplenote.ui.main.fragment.list.trash.recycler

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import id.aasumitro.simplenote.R
import id.aasumitro.simplenote.data.NotesLocalRepository
import id.aasumitro.simplenote.data.local.model.NotesTrash
import id.aasumitro.simplenote.ui.main.fragment.list.main.recycler.RecyclerTrashHolder
import id.aasumitro.simplenote.ui.main.fragment.list.main.recycler.RecyclerTrashListener
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

    fun removeAt(position: Int) {
        NotesLocalRepository.restoreFromTrash(
                noteList[position].id!!.toLong(),
                noteList[position].title.toString(),
                noteList[position].description.toString(),
                noteList[position].createdAt!!,
                Date())
        noteList.removeAt(position)
        notifyItemRemoved(position)
    }
}