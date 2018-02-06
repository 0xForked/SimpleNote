package id.aasumitro.simplenote.ui.main.fragment.list.main.rvmain

import id.aasumitro.simplenote.data.local.model.Notes


/**
 * Created by Agus Adhi Sumitro on 25/01/2018.
 * https://asmith.my.id
 * aasumitro@gmail.com
 */
interface RecyclerMainListener {

    fun onItemClick(notes: Notes)

}