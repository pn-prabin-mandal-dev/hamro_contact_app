package com.example.hamrocontactsapp.second_recall

sealed class RecallContactEvent {
    object saveContact: RecallContactEvent()
    data class setFirstName(val firstName: String): RecallContactEvent()
    data class setLastName(val lastName: String): RecallContactEvent()
    data class setPhoneNumber(val phoneNumber: String): RecallContactEvent()
    data class deleteContact(val contact: RecallContactEntity): RecallContactEvent()
    data class sortContacts(val sortType: RecallSortType): RecallContactEvent()
    object showDialog: RecallContactEvent()
    object hideDialog: RecallContactEvent()
}