package com.example.hamrocontactsapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {
    @Upsert
    suspend fun UpsertContact(contact: Contact)

    @Delete
    suspend fun DeleteContact(contact: Contact)


    @Query("SELECT * FROM CONTACT ORDER BY FirstName ASC")
    fun getAllContactsOrderedByFirstName() : Flow<List<Contact>>

    @Query("SELECT * FROM CONTACT ORDER BY LastName ASC")
    fun getAllContactsOrderedByLastName() : Flow<List<Contact>>

    @Query("SELECT * FROM CONTACT ORDER BY PhoneNumber ASC")
    fun getAllContactsOrderedByPhoneNumber() : Flow<List<Contact>>
}