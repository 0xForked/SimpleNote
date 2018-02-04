package id.aasumitro.simplenote.ui.main.fragment.list.main.recycler

import android.support.v7.widget.RecyclerView
import android.view.View
import id.aasumitro.simplenote.R
import id.aasumitro.simplenote.data.local.model.Notes
import kotlinx.android.synthetic.main.item_note_list.view.*

/**
 * Created by Agus Adhi Sumitro on 25/01/2018.
 * https://asmith.my.id
 * aasumitro@gmail.com
 */

class RecyclerMainHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(notes: Notes, listener: RecyclerMainListener) {

        itemView.notes_title.text = notes.title
        if (notes.isLocked == false) {
            itemView.note_category.setBackgroundResource(R.color.colorPrimary)
            itemView.lock_status.setImageResource(R.drawable.ic_lock_open_black_24dp)
        }
        itemView.note_update.text = notes.updatedAt.toString()
        //onClick
        itemView.recycler_click.setOnClickListener{ listener.onItemClick(notes) }
    }

}