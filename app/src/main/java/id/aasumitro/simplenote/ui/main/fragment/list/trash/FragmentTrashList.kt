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
import id.aasumitro.simplenote.ui.main.fragment.list.main.recycler.RecyclerTrashListener
import id.aasumitro.simplenote.ui.main.fragment.list.trash.recycler.RecyclerSwipeDeleteTrash
import id.aasumitro.simplenote.ui.main.fragment.list.trash.recycler.RecyclerTrashAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_note_list.*
import kotlinx.android.synthetic.main.fragment_note_list.view.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
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
        initList(view)
        view.fab.visibility = View.GONE
        return view

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
        mainActivity().getToolbar().apply {
            this.title = "EZNote trash"
            this.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
            this.setNavigationOnClickListener {
                activity!!.startActivity<MainActivity>()
                activity!!.finish()
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
            R.id.menu_clear -> NotesLocalRepository.clearThisTrash()
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
        activity!!.toast("Error ${error.localizedMessage}")

    }

    private fun handleResponse(list: List<NotesTrash>) {

        val noteList = ArrayList(list)
        mAdapter = RecyclerTrashAdapter(noteList, this@FragmentTrashList)
        itemsRecyclerView.adapter = mAdapter

        val swipeHandler = object : RecyclerSwipeDeleteTrash(activity!!) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                mAdapter!!.removeAt(viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(itemsRecyclerView)

    }


    override fun onItemClick(notes: NotesTrash) {
        activity!!.toast("id: ${notes.id} title: ${notes.title} createdAt: ${notes.createdAt} deletedAt: ${notes.deletedAt}")
    }

}