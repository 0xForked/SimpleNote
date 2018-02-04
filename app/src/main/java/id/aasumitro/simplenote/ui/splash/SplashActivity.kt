package id.aasumitro.simplenote

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import id.aasumitro.simplenote.ui.intro.IntroActivity
import id.aasumitro.simplenote.ui.main.MainActivity
import id.aasumitro.simplenote.ui.splash.SplashNavigation
import id.aasumitro.simplenote.ui.splash.SplashViewModel
import id.aasumitro.simplenote.utils.SharedPrefs
import org.jetbrains.anko.startActivity
import javax.inject.Inject

class SplashActivity : AppCompatActivity(), SplashNavigation {

    @Inject lateinit var mPrefsUtil: SharedPrefs

    private val mViewModel: SplashViewModel by lazy {
        ViewModelProviders.of(this).get(SplashViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        NoteApp.mAppComponent.inject(this)

        mViewModel.initViewModel(this, mPrefsUtil)
        mViewModel.startTask()

    }

    override fun startMain() {
        startActivity<MainActivity>()
        finish()
    }

    override fun startIntro() {
        startActivity<IntroActivity>()
        finish()
    }

}
