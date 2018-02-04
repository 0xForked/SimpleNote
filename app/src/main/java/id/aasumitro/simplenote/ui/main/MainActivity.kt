package id.aasumitro.simplenote.ui.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import id.aasumitro.simplenote.NoteApp
import id.aasumitro.simplenote.R
import id.aasumitro.simplenote.ui.main.fragment.list.main.FragmentNoteList
import id.aasumitro.simplenote.utils.AppCons.USER_PASSWORD
import id.aasumitro.simplenote.utils.SharedPrefs
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject lateinit var mPrefs: SharedPrefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        NoteApp.mAppComponent.inject(this)
        isNotePasswordAvailable()
        savedInstanceState.let { replaceFragment(FragmentNoteList()) }
    }

    private fun isNotePasswordAvailable() {
        val password = mPrefs.getString(USER_PASSWORD, "isNotSet")
        if (password == "isNotSet") {
            toast("password is null")
        }
    }

    fun replaceFragment (fragment: Fragment, cleanStack: Boolean = false) {
        if (cleanStack) clearBackStack()
        val ft = supportFragmentManager.beginTransaction()
        ft.apply {
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            replace(R.id.fragment_container, fragment)
            addToBackStack(null)
        }.commit()
    }

    private fun clearBackStack() {
        val manager = supportFragmentManager
        if (manager.backStackEntryCount > 0) {
            val first = manager.getBackStackEntryAt(0)
            manager.popBackStack(first.id, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }

    override fun onBackPressed() {
        val fragmentManager = supportFragmentManager
        if (fragmentManager.backStackEntryCount > 1) {
            fragmentManager.popBackStack()
        } else {
            finish()
        }
    }

    fun getToolbar(): Toolbar = this.toolbar

}
