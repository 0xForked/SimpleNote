package id.aasumitro.simplenote

import android.app.Application
import id.aasumitro.simplenote.data.local.room.CreatorDatabase
import id.aasumitro.simplenote.di.AppComponent
import id.aasumitro.simplenote.di.AppModule
import id.aasumitro.simplenote.di.DaggerAppComponent


/**
 * Created by Agus Adhi Sumitro on 23/01/2018.
 * https://asmith.my.id
 * aasumitro@gmail.com
 */

class NoteApp : Application() {

    companion object {
        lateinit var mAppComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        CreatorDatabase.createDb(this)

        mAppComponent = DaggerAppComponent
                .builder()
                .appModule(AppModule(this))
                .build()

    }

}