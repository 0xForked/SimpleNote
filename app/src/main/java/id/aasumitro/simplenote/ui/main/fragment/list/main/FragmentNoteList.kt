package id.aasumitro.simplenote.ui.main.fragment.list.main

import android.content.ContentValues.TAG
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
import id.aasumitro.simplenote.data.local.model.Notes
import id.aasumitro.simplenote.ui.main.MainActivity
import id.aasumitro.simplenote.ui.main.fragment.detail.FragmentNoteDetail
import id.aasumitro.simplenote.ui.main.fragment.list.main.rvmain.RecyclerMainAdapter
import id.aasumitro.simplenote.ui.main.fragment.list.main.rvmain.RecyclerMainListener
import id.aasumitro.simplenote.ui.main.fragment.list.main.rvmain.RecyclerSwipeDeleteMain
import id.aasumitro.simplenote.ui.main.fragment.list.trash.FragmentTrashList
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_note_list.*
import kotlinx.android.synthetic.main.fragment_note_list.view.*
import org.jetbrains.anko.toast
import java.util.*


/**
 * Created by Agus Adhi Sumitro on 25/01/2018.
 * https://asmith.my.id
 * aasumitro@gmail.com
 */

class FragmentNoteList : Fragment(), RecyclerMainListener {

    private var mAdapter: RecyclerMainAdapter? = null
    private fun mainActivity() = activity as MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_note_list, container, false)
        itemsRecyclerView.let { initList(view) }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
        mainActivity().getToolbar().apply {
            this.title = getString(R.string.app_name)
            this.navigationIcon = null
        }
        fab.setOnClickListener {
            mainActivity().replaceFragment(FragmentNoteDetail())
        }
        getAllNote()
    }

    private fun initList(view: View){
        view.itemsRecyclerView.setHasFixedSize(true)
        val layoutManager : RecyclerView.LayoutManager =
                LinearLayoutManager(activity)
        view.itemsRecyclerView.layoutManager = layoutManager
        view.itemsRecyclerView.itemAnimator = DefaultItemAnimator()
        view.itemsRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                if (dy > 0 || dy < 0 && fab.isShown) fab.hide()
            }
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) fab.show()
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
        view.swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            getAllNote()
        }
    }

    private fun getAllNote() {
        NotesLocalRepository.getAllNotes()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this@FragmentNoteList::handleResponse,
                           this@FragmentNoteList::handleError)
    }

    private fun handleError(error: Throwable) {
        Log.d(TAG, error.localizedMessage)
    }

    private fun handleResponse(list: List<Notes>) {
        val noteList = ArrayList(list)
        mAdapter = RecyclerMainAdapter(noteList, this@FragmentNoteList)
        itemsRecyclerView.adapter = mAdapter
        val swipeHandler = object : RecyclerSwipeDeleteMain(activity!!) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                mAdapter!!.removeAt(viewHolder.adapterPosition, view!!)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(itemsRecyclerView)
    }

    override fun onItemClick(notes: Notes) {
        activity!!.toast("id: ${notes.id} createdAt: ${notes.createdAt} updatedAt: ${notes.updatedAt} isLocked: ${notes.isLocked}")
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_list, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId) {
            R.id.filter_by_title ->  activity!!.toast("title")
            R.id.filter_by_created -> activity!!.toast("Created_at")
            R.id.filter_by_updated -> activity!!.toast("updated_at")
            R.id.menu_trash -> mainActivity().replaceFragment(FragmentTrashList())
            R.id.menu_setting -> activity!!.toast("This is setting")
        }
        return true
    }

}