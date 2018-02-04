package id.aasumitro.simplenote.ui.main.fragment.detail

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import id.aasumitro.simplenote.R
import id.aasumitro.simplenote.data.NotesLocalRepository.postNote
import id.aasumitro.simplenote.ui.main.MainActivity
import jp.wasabeef.richeditor.RichEditor
import kotlinx.android.synthetic.main.fragment_note_detail.*
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.toast
import java.util.*

/**
 * Created by Agus Adhi Sumitro on 25/01/2018.
 * https://asmith.my.id
 * aasumitro@gmail.com
 */

class FragmentNoteDetail : Fragment() {

    private fun mainActivity() = activity as MainActivity

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_note_detail,
                             container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
        editorDetail()
        mainActivity().getToolbar().apply {
            this.title = "EZNote created"
            this.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
            this.setNavigationOnClickListener {
                fragmentManager!!.popBackStack()
                hideKeyboard()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_detail, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId) {
            R.id.menu_done -> actionDone()
        }
        return true
    }

    private fun actionDone() {
        val shake: Animation = AnimationUtils.loadAnimation(activity!!, R.anim.shake)
        val title = et_detail_title.text.toString()
        val desc = textDetail.text.toString()
        val lock = textLock.text.toString()
        if (title.isEmpty()) et_detail_title.error = "Require"
        if (desc.isEmpty()) card_desc.startAnimation(shake)
        if (!desc.isEmpty() && !title.isEmpty()) {
            postNote(title, desc, lock.toBoolean(), Date(), Date())
            fragmentManager!!.popBackStack()
            hideKeyboard()
        }
    }

    private fun editorDetail() {
        val mEditor = et_detail_desc as RichEditor
        mEditor.apply {
            setEditorHeight(200)
            setEditorFontSize(20)
            setEditorFontColor(Color.BLACK)
            setPlaceholder("Insert text here...")
        }
        mEditor.setOnTextChangeListener { text -> textDetail.text = text }
        action_unlock.setOnClickListener {
            action_lock.visibility = View.VISIBLE
            action_unlock.visibility = View.GONE
            textLock.text = true.toString()
            activity!!.toast("Note lock : ${textLock.text}")
        }
        action_lock.setOnClickListener {
            action_lock.visibility = View.GONE
            action_unlock.visibility = View.VISIBLE
            textLock.text = false.toString()
            activity!!.toast("Note lock : ${textLock.text}")
        }
        action_undo.setOnClickListener {
            mEditor.undo()
            activity!!.toast("Undo")
        }
        action_redo.setOnClickListener {
            mEditor.redo()
            activity!!.toast("Redo")
        }
        action_bold.setOnClickListener { mEditor.setBold() }
        action_italic.setOnClickListener { mEditor.setItalic() }
        action_underline.setOnClickListener { mEditor.setUnderline() }
        action_strikethrough.setOnClickListener { mEditor.setStrikeThrough() }
        action_align_left.setOnClickListener { mEditor.setAlignLeft() }
        action_align_center.setOnClickListener { mEditor.setAlignCenter() }
        action_align_right.setOnClickListener { mEditor.setAlignRight() }
        action_insert_link.setOnClickListener {
            activity!!.alert {
                customView {
                    verticalLayout  {
                        val insertLink = editText {
                            hint = "url://"
                            singleLine = true
                            imeOptions = EditorInfo.IME_ACTION_DONE
                        }.lparams(width = matchParent) {
                            topMargin = dip(20)
                            leftMargin = dip(20)
                            rightMargin = dip(20)
                        }
                        positiveButton("Insert link") {
                            val link = insertLink.text.toString()
                            if (!link.isEmpty())
                                mEditor.insertLink(link, link)
                        }
                    }
                }
            }.show()
        }
        action_insert_image.setOnClickListener {
            activity!!.alert {
                customView {
                    verticalLayout {
                        padding = dip(20)
                        relativeLayout {
                            imageView(R.drawable.ic_photo_camera_black_24dp)
                            textView {
                                text = "Add from camera"
                                textSize = 20F
                                leftPadding = dip(30)
                                topPadding = dip(2)
                            }
                            setOnClickListener {
                                toast("add from camera")
                            }
                        }
                        relativeLayout {
                            topPadding = dip(20)
                            imageView(R.drawable.ic_image_black_24dp)
                            textView {
                                text = "Add from gallery"
                                textSize = 20F
                                leftPadding = dip(30)
                                topPadding = dip(2)
                            }
                            setOnClickListener {
                                toast("add from gallery")
                            }
                        }
                    }
                }
            }.show()
        }

    }

    private fun hideKeyboard() {
        val view = activity!!.currentFocus
        if (view != null) {
            val inputManager = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE)
                    as InputMethodManager
            inputManager.hideSoftInputFromWindow(view.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

}