package com.example.hiki.domain.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.hiki.domain.model.dao.UserDao
import com.example.hiki.domain.model.entity.User

@Database(
    entities = [User::class],
    version = 1,
    exportSchema = false
)
abstract class HikiDataBase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var Instance: HikiDataBase? = null

        fun getDatabase(context: Context): HikiDataBase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, HikiDataBase::class.java, "hiki_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}