package com.example.hamrocontactsapp

interface ContactEvents {
    object SaveContact: ContactEvents  // with this conteEvent.events  in VM
    data class SetFirstName(val firstName: String): ContactEvents
    data class SetLastName(val lastName: String): ContactEvents
    data class SetPhoneNumber(val phoneNumber: String): ContactEvents
    object ShowDialog: ContactEvents
    object HideDialog: ContactEvents
    data class SortContacts(val sortType: SortType): ContactEvents
    data class DeleteContact(val contact: Contact): ContactEvents
}

enum class SortType{
    FIRST_NAME,
    LAST_NAME,
    PHONE_NUMBER
}