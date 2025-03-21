package com.example.hamrocontactsapp.second_recall

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [RecallContactEntity::class], version = 1, exportSchema = false)
abstract class RecallContactDB(): RoomDatabase(){
    abstract fun recallContactDao(): RecallContactDao

    companion object{
        @Volatile
        private var INSTANCE: RecallContactDB? = null

        fun getRecallDatabaseInstance(context: Context): RecallContactDB{
            return INSTANCE?: synchronized(this){
                val instance: RecallContactDB = Room.databaseBuilder(
                    context.applicationContext,
                    RecallContactDB::class.java,
                    "Recall Database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}