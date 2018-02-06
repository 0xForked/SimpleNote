package id.aasumitro.simplenote.ui.main.fragment.list.trash

import android.content.ContentValues
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.*
import id.aasumitro.simplenote.R
import id.aasumitro.simplenote.data.NotesLocalRepository
import id.aasumitro.simplenote.data.local.model.NotesTrash
import id.aasumitro.simplenote.ui.main.MainActivity
import id.aasumitro.simplenote.ui.main.fragment.list.trash.rvtrash.RecyclerTrashAdapter
import id.aasumitro.simplenote.ui.main.fragment.list.trash.rvtrash.RecyclerTrashListener
import id.aasumitro.simplenote.ui.main.fragment.list.trash.rvtrash.SwipeHelper
import id.aasumitro.simplenote.ui.main.fragment.list.trash.rvtrash.SwipeHelper.UnderlayButtonClickListener
import id.aasumitro.simplenote.utils.dsl.action
import id.aasumitro.simplenote.utils.dsl.snack
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_note_list.*
import kotlinx.android.synthetic.main.fragment_note_list.view.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.appcompat.v7.Appcompat
import java.util.*

/**
 * Created by Agus Adhi Sumitro on 02/02/2018.
 * https://asmith.my.id
 * aasumitro@gmail.com
 */

class FragmentTrashList : Fragment(), RecyclerTrashListener {

    private var mAdapter: RecyclerTrashAdapter? = null
    private fun mainActivity() = activity as MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_note_list, container, false)
        itemsRecyclerView.let { initList(view) }
        view.fab.visibility = View.GONE
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
        mainActivity().getToolbar().apply {
            this.title = resources.getString(R.string.trash_title)
            this.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
            this.setNavigationOnClickListener {
                mainActivity().onBackPressed()
            }
        }
        getTrashList()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_trash, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId) {
            R.id.menu_clear -> {
                activity!!.alert(Appcompat) {
                    title = resources.getString(R.string.alert_delete_title)
                    message = resources.getString(R.string.alert_delete_msg_all)
                    positiveButton(resources.getString(R.string.delete)) {
                        NotesLocalRepository.clearThisTrash()
                        view!!.snack("Deleted success") {
                            action("OK") { mainActivity().onBackPressed() }
                        }
                    }
                    negativeButton(resources.getString(R.string.cancel)) { }
                }.show().setCancelable(false)
            }
        }
        return true
    }
    
    private fun initList(view: View){
        view.itemsRecyclerView.setHasFixedSize(true)
        val layoutManager : RecyclerView.LayoutManager =
                LinearLayoutManager(activity)
        view.itemsRecyclerView.layoutManager = layoutManager
        view.itemsRecyclerView.itemAnimator = DefaultItemAnimator()
        view.swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            getTrashList()
        }
    }

    private fun getTrashList() {
        NotesLocalRepository.getAllTrashList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this@FragmentTrashList::handleResponse,
                           this@FragmentTrashList::handleError)
    }

    private fun handleError(error: Throwable) {
        Log.d(ContentValues.TAG, error.localizedMessage)
    }

    private fun handleResponse(list: List<NotesTrash>) {
        val noteList = ArrayList(list)
        mAdapter = RecyclerTrashAdapter(noteList, this@FragmentTrashList)
        itemsRecyclerView.adapter = mAdapter

        @Suppress("DEPRECATION")
        val swipeHelper = object : SwipeHelper(activity!!, itemsRecyclerView) {
            override fun instantiateUnderlayButton(viewHolder: RecyclerView.ViewHolder,
                                                   underlayButtons: MutableList<UnderlayButton>) {
                underlayButtons.add(SwipeHelper.UnderlayButton(
                        resources.getString(R.string.delete), resources.getColor(R.color.colorAccent),
                        UnderlayButtonClickListener {
                            activity!!.alert(Appcompat) {
                                title = resources.getString(R.string.alert_delete_title)
                                message = resources.getString(R.string.alert_delete_msg)
                                positiveButton(resources.getString(R.string.delete)) {
                                    mAdapter!!.deleteTrashItem(viewHolder.adapterPosition)
                                    view!!.snack("Deleted success") {
                                        action("OK") { mainActivity().onBackPressed() }
                                    }
                                }
                                negativeButton(resources.getString(R.string.cancel)) { }
                            }.show().setCancelable(false)
                        }
                ))
                underlayButtons.add(SwipeHelper.UnderlayButton(
                        "Restore", resources.getColor(R.color.colorPrimary),
                        UnderlayButtonClickListener {
                            mAdapter!!.restoreBack(viewHolder.adapterPosition)
                            mainActivity().onBackPressed()
                        }
                ))
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeHelper)
        itemTouchHelper.attachToRecyclerView(itemsRecyclerView)

    }

    override fun onItemClick(notes: NotesTrash) {
        view!!.snack(resources.getString(R.string.detail_available)) { action("") { } }
    }

}