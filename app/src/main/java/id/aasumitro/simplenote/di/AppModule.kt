package id.aasumitro.simplenote.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import id.aasumitro.simplenote.NoteApp
import id.aasumitro.simplenote.utils.SharedPrefs
import javax.inject.Singleton


/**
 * Created by Agus Adhi Sumitro on 23/01/2018.
 * https://asmith.my.id
 * aasumitro@gmail.com
 */

@Module
class AppModule(private val noteApp: NoteApp) {

    @Provides
    @Singleton
    fun provideApplication() = noteApp

    @Provides
    @Singleton
    fun providePreferencesUtil(sharedPreferences: SharedPreferences):
            SharedPrefs = SharedPrefs(sharedPreferences)

    @Provides
    @Singleton
    fun provideSharedPreferences(): SharedPreferences = provideApplication()
            .getSharedPreferences("", Context.MODE_PRIVATE)


}