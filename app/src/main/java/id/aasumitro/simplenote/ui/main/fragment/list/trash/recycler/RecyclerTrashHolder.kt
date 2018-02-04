package id.aasumitro.simplenote.ui.main.fragment.list.main.recycler

import android.support.v7.widget.RecyclerView
import android.view.View
import id.aasumitro.simplenote.R
import id.aasumitro.simplenote.data.local.model.NotesTrash
import kotlinx.android.synthetic.main.item_note_list.view.*

/**
 * Created by Agus Adhi Sumitro on 25/01/2018.
 * https://asmith.my.id
 * aasumitro@gmail.com
 */

class RecyclerTrashHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(notes: NotesTrash, listener: RecyclerTrashListener) {

        itemView.notes_title.text = notes.title
        itemView.note_category.setBackgroundResource(R.color.colorPrimary)
        itemView.lock_status.visibility = View.INVISIBLE
        itemView.note_update.text = notes.deletedAt.toString()
        //onClick
        itemView.recycler_click.setOnClickListener{ listener.onItemClick(notes) }
    }

}