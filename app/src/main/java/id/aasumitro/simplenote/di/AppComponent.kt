package id.aasumitro.simplenote.di

import dagger.Component
import id.aasumitro.simplenote.SplashActivity
import id.aasumitro.simplenote.ui.main.MainActivity
import javax.inject.Singleton


/**
 * Created by Agus Adhi Sumitro on 23/01/2018.
 * https://asmith.my.id
 * aasumitro@gmail.com
 */

@Singleton
@Component(modules = [
    (AppModule::class)
])

interface AppComponent {

    fun inject(target: SplashActivity)
    fun inject(target: MainActivity)

}