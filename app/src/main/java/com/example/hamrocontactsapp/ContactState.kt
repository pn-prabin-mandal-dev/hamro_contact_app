package com.example.hamrocontactsapp

data class ContactState (
    val contacts: List<Contact> = emptyList<Contact>(),
    val firstName: String = "",
    val lastName: String = "",
    val phoneNumber: String = "",
    val isAddingContact: Boolean = false,
    val sortType: SortType = SortType.FIRST_NAME
)