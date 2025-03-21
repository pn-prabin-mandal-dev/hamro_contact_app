package com.example.hamrocontactsapp

import kotlinx.coroutines.flow.Flow

class ContactRepository(private val dao: ContactDao) {

    suspend fun upsertContact(contact: Contact) {
        dao.UpsertContact(contact)
    }

    suspend fun deleteContact(contact: Contact) {
        dao.DeleteContact(contact)
    }

    fun getAllContactsOrderByFirstName(): Flow<List<Contact>> {
        return dao.getAllContactsOrderedByFirstName()
    }

    fun getAllContactsOrderedByLastName(): Flow<List<Contact>> {
        return dao.getAllContactsOrderedByLastName()
    }

    fun getAllContactsOrderedByPhoneNumber(): Flow<List<Contact>> {
        return dao.getAllContactsOrderedByPhoneNumber()
    }

}