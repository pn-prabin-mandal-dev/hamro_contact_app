package com.example.hamrocontactsapp.second_recall

import kotlinx.coroutines.flow.Flow

class RecallContactRepo(private val recallContactDao: RecallContactDao) {
    suspend fun upsertContact(contact: RecallContactEntity) {
        recallContactDao.upsertContact(contact)
    }

    suspend fun deleteContact(contact: RecallContactEntity){
        recallContactDao.deleteContact(contact)
    }

    fun getAllContactByFirstName(): Flow<List<RecallContactEntity>>{
        return recallContactDao.getAllContactByFirstName()
    }

    fun getAllContactByLastName(): Flow<List<RecallContactEntity>>{
        return recallContactDao.getAllContactByLastName()
    }

    fun getAllContactByPhoneNumber(): Flow<List<RecallContactEntity>>{
        return recallContactDao.getAllContactByPhoneNumber()
    }

}