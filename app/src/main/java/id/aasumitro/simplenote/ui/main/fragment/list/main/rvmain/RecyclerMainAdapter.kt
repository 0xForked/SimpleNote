package id.aasumitro.simplenote.ui.main.fragment.list.main.rvmain

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.aasumitro.simplenote.R
import id.aasumitro.simplenote.data.NotesLocalRepository
import id.aasumitro.simplenote.data.local.model.Notes
import id.aasumitro.simplenote.utils.dsl.action
import id.aasumitro.simplenote.utils.dsl.snack
import java.util.*


/**
 * Created by Agus Adhi Sumitro on 25/01/2018.
 * https://asmith.my.id
 * aasumitro@gmail.com
 */

class RecyclerMainAdapter(private val noteList: ArrayList<Notes>,
                          private val listener: RecyclerMainListener) :
        RecyclerView.Adapter<RecyclerMainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerMainHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_note_list, parent, false)
        return RecyclerMainHolder(view)
    }

    override fun getItemCount(): Int = noteList.count()

    override fun onBindViewHolder(holder: RecyclerMainHolder, position: Int) =
            holder.bind(noteList[position], listener)

    fun removeAt(position: Int, view: View) {
        NotesLocalRepository.sendToRecyclerBin(
                noteList[position].id!!.toLong(),
                noteList[position].title.toString(),
                noteList[position].description.toString(),
                noteList[position].createdAt!!,
                Date())
        view.let {
            it.snack("${noteList[position].title} moved to trash") {
                action("OK") { }
            }
        }
        noteList.removeAt(position)
        notifyItemRemoved(position)
    }
}