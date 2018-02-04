package id.aasumitro.simplenote.data.local.room

import android.arch.persistence.room.Room
import android.content.Context
import id.aasumitro.simplenote.utils.AppCons.DATABASE_NAME


/**
 * Created by Agus Adhi Sumitro on 25/01/2018.
 * https://asmith.my.id
 * aasumitro@gmail.com
 */

object CreatorDatabase {

    lateinit var database: AppDatabase

    fun createDb(context: Context): AppDatabase {

        database = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                DATABASE_NAME)
                .allowMainThreadQueries()
                .build()

        return database

    }


}