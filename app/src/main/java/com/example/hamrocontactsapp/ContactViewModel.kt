package com.example.hamrocontactsapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


@OptIn(ExperimentalCoroutinesApi::class) // Opt-in annotation to use experimental coroutines API.
class ContactViewModel(
    private val repo: ContactRepository
) : ViewModel() {  // don't forgot to call viewmodel else error with stateIn viewModelScope <<<<<<<<

    private val _sortType = MutableStateFlow(SortType.FIRST_NAME) // Controls the sorting type
    private val _state = MutableStateFlow(ContactState()) // Internal MutableStateFlow for managing UI state
    private val _contact = _sortType
        .flatMapLatest { sortType ->  // sometime may error here because of repo method return type Imp.
            // Reactively fetch contacts when sorting changes
            when (sortType) {
                SortType.FIRST_NAME -> repo.getAllContactsOrderByFirstName()
                SortType.LAST_NAME -> repo.getAllContactsOrderedByLastName()
                SortType.PHONE_NUMBER -> repo.getAllContactsOrderedByPhoneNumber()
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList()) // emptyList() is default value

    // Combined StateFlow for the UI
    val state = combine(_state, _sortType, _contact) { state, sortType, contacts ->
        state.copy(
            contacts = contacts,
            sortType = sortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), ContactState())  // default value from state

    fun onEvent(event: ContactEvents) {
        when (event) {
            is ContactEvents.DeleteContact -> {  // as this is data class in events thus is at first
                viewModelScope.launch {
                    repo.deleteContact(contact = event.contact)
                }
            }
            ContactEvents.HideDialog -> {  // as this is object in events thus directly
                _state.update { it.copy(isAddingContact = false) }
            }
            ContactEvents.SaveContact -> {
                val firstName = _state.value.firstName
                val lastName = _state.value.lastName
                val phoneNumber = _state.value.phoneNumber

                if (firstName.isBlank() || lastName.isBlank() || phoneNumber.isBlank()) {
                    return
                }

                val contact = Contact(
                    FirstName = firstName,
                    LastName = lastName,
                    PhoneNumber = phoneNumber
                )

                viewModelScope.launch {
                    repo.upsertContact(contact) // Save or update contact
                }

                // also update the some state value to default
                _state.update {
                    it.copy(
                        isAddingContact = false,
                        firstName = "",
                        lastName = "",
                        phoneNumber = ""
                    )
                }
            }
            is ContactEvents.SetFirstName -> {
                _state.update { it.copy(firstName = event.firstName) }  // from events and events takes from UI
            }
            is ContactEvents.SetLastName -> {
                _state.update { it.copy(lastName = event.lastName) }
            }
            is ContactEvents.SetPhoneNumber -> {
                _state.update { it.copy(phoneNumber = event.phoneNumber) }
            }
            ContactEvents.ShowDialog -> {
                _state.update { it.copy(isAddingContact = true) }
            }
            is ContactEvents.SortContacts -> {
                _sortType.value = event.sortType
            }
        }
    }
}


/*
 DOCS AFTER FIXING
 -------------------
Changes and Fixes:
Clear State Usage:

Replaced ambiguous state.value with _state.value when accessing the mutable state directly.
This ensures no ambiguity and helps track mutable versus immutable states.
Updated Documentation:

Comments added for better understanding of how _state, _sortType, and _contact work together.
Unambiguous val contact:

Consolidated the handling of user input (e.g., firstName, lastName) when saving a new contact to minimize redundant operations.
State Consistency:

The _state.update operations are streamlined for better consistency and reduced boilerplate.
* */