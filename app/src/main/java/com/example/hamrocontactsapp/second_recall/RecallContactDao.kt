package com.example.hamrocontactsapp.second_recall

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface RecallContactDao {
    @Upsert
    suspend fun upsertContact(contact: RecallContactEntity)

    @Delete
    suspend fun deleteContact(contact: RecallContactEntity)

    @Query("select * from `Recall Contact Table` order by firstName asc")
    fun getAllContactByFirstName(): Flow<List<RecallContactEntity>>

    @Query("select * from `Recall Contact Table` order by lastName asc")
    fun getAllContactByLastName(): Flow<List<RecallContactEntity>>

    @Query("select * from `Recall Contact Table` order by phoneNumber asc")
    fun getAllContactByPhoneNumber(): Flow<List<RecallContactEntity>>
}