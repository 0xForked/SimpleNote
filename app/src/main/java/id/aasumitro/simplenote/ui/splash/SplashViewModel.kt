package id.aasumitro.simplenote.ui.splash

import android.arch.lifecycle.ViewModel
import id.aasumitro.simplenote.utils.AppCons.APP_FIRST_LAUNCH
import id.aasumitro.simplenote.utils.SharedPrefs
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


/**
 * Created by Agus Adhi Sumitro on 25/01/2018.
 * https://asmith.my.id
 * aasumitro@gmail.com
 */
class SplashViewModel : ViewModel() {

    private var mNavigator: SplashNavigation? = null
    private var mPrefsUtil: SharedPrefs? = null

    fun initViewModel(navigation: SplashNavigation,
                      prefsUtil: SharedPrefs) {
        this.mNavigator = navigation
        this.mPrefsUtil = prefsUtil
    }

    fun startTask() {
        Observable.interval(3, TimeUnit.SECONDS)
                .take(1)
                .observeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.newThread())
                .subscribe { onNextActivity() }
    }

    //change to true if on release mode
    private fun isFirstLaunch() = mPrefsUtil!!.getBoolean(APP_FIRST_LAUNCH, false)

    private fun onNextActivity() {
        if (isFirstLaunch())
            mNavigator!!.startIntro()
        else
            mNavigator!!.startMain()
    }

}