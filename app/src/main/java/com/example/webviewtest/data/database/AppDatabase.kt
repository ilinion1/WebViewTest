package com.example.webviewtest.data.database


import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.webviewtest.data.network.model.LinkContainerDto

@Database(entities = [LinkContainerDto::class], version = 1, exportSchema = false)
abstract class AppDatabase() : RoomDatabase() {

    companion object {

        private var db: AppDatabase? = null
        private const val DB_NAME = "web_view_db"
        private val LOCK = Any()

        fun getInstance(application: Application): AppDatabase {
            synchronized(LOCK) {
                db?.let {
                    return it
                }
                val instance =
                    Room.databaseBuilder(application, AppDatabase::class.java, DB_NAME).build()
                db = instance
                return instance
            }
        }
    }

    abstract fun webViewDao(): WebViewDao
}