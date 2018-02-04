package id.aasumitro.simplenote.data.local.room

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.migration.Migration




/**
 * Created by Agus Adhi Sumitro on 02/02/2018.
 * https://asmith.my.id
 * aasumitro@gmail.com
 */
class MigrationDatabase {

//    Room.databaseBuilder(getApplicationContext(), MyDb.class, "database-name")
//    .addMigrations(MIGRATION_1_2, MIGRATION_2_3).build();

    val MIGRATION_1_2: Migration = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("CREATE TABLE `Bla` (`id` INTEGER, " + "`name` TEXT, PRIMARY KEY(`id`))")
        }
    }

}