package com.example.hamrocontactsapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Contact::class],
    version = 1
)
abstract class ContactDatabase(): RoomDatabase() {
    abstract fun contactDao() : ContactDao

    companion object{
        @Volatile
        private var INSTANCE: ContactDatabase? = null

        fun getDatabaseInstance(context: Context): ContactDatabase{
            return INSTANCE ?: synchronized(this){
                val instance: ContactDatabase = Room.databaseBuilder(
                    context.applicationContext,
                    ContactDatabase::class.java,
                    "notes_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}