package id.aasumitro.simplenote.ui.main.fragment.list.trash.recycler

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import id.aasumitro.simplenote.R

/**
 * Created by Agus Adhi Sumitro on 31/01/2018.
 * https://asmith.my.id
 * aasumitro@gmail.com
 */
abstract class RecyclerSwipeDeleteTrash(context: Context) :
        ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

    private val deleteIcon = ContextCompat.getDrawable(context, R.drawable.ic_restore_white_24dp)
    private val intrinsicWidth = deleteIcon!!.intrinsicWidth
    private val intrinsicHeight = deleteIcon!!.intrinsicHeight
    private val background = ColorDrawable()
    private val backgroundColor = context.resources.getColor(R.color.colorPrimary)

    override fun onMove(recyclerView: RecyclerView?,
                        viewHolder: RecyclerView.ViewHolder?,
                        target: RecyclerView.ViewHolder?): Boolean {
        return false
    }

    override fun onChildDraw(c: Canvas?, recyclerView: RecyclerView?,
                             viewHolder: RecyclerView.ViewHolder,
                             dX: Float, dY: Float, actionState: Int,
                             isCurrentlyActive: Boolean) {

        val itemView = viewHolder.itemView
        val itemHeight = itemView.bottom - itemView.top

        background.color = backgroundColor
        background.setBounds(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
        background.draw(c)

        val deleteIconTop = itemView.top + (itemHeight - intrinsicHeight) / 2
        val deleteIconMargin = (itemHeight - intrinsicHeight) / 2
        val deleteIconLeft = itemView.right - deleteIconMargin - intrinsicWidth
        val deleteIconRight = itemView.right - deleteIconMargin
        val deleteIconBottom = deleteIconTop + intrinsicHeight

        deleteIcon!!.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom)
        deleteIcon.draw(c)

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

    }
}