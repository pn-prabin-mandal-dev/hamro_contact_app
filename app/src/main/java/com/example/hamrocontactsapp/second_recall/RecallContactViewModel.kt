package com.example.hamrocontactsapp.second_recall

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecallContactViewModel(private val recallContactRepo: RecallContactRepo) : ViewModel(){
    private val _state = MutableStateFlow(RecallContactState())
    private val _sortType = MutableStateFlow(RecallSortType.FIRST_NAME)
    private val _contact = _sortType
        .flatMapLatest { sortType->
            when(sortType){
            RecallSortType.FIRST_NAME -> recallContactRepo.getAllContactByFirstName()
            RecallSortType.LAST_NAME -> recallContactRepo.getAllContactByLastName()
            RecallSortType.PHONE_NUMBER -> recallContactRepo.getAllContactByPhoneNumber()
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList()) // emptyList() is default value

    val state = combine(_state,_sortType,_contact){state,sortType,contacts->
        state.copy(
            contacts = contacts,
            sortType = sortType

        )
    }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), RecallContactState())

    fun onRecallEvent(event: RecallContactEvent){
        when(event){
            is RecallContactEvent.deleteContact -> {
                viewModelScope.launch{
                    recallContactRepo.deleteContact(event.contact)
                }
            }
            RecallContactEvent.hideDialog -> {
                _state.update { it.copy(isAddingContact = false) }
            }
            RecallContactEvent.saveContact -> {
                val firstName = _state.value.firstName
                val lastName = _state.value.lastName
                val phoneNumber = _state.value.lastName

                if(firstName.isBlank() || lastName.isBlank() || phoneNumber.isBlank()){
                    return
                }
                val contact = RecallContactEntity(
                    firstName = firstName,
                    lastName = lastName,
                    phoneNumber = phoneNumber
                )
                viewModelScope.launch{
                    recallContactRepo.upsertContact(contact)
                }
                _state.update {
                    it.copy(
                        isAddingContact = false,
                        firstName = "",
                        lastName = "",
                        phoneNumber = ""
                    )
                }
            }
            is RecallContactEvent.setFirstName -> {
                _state.update { it.copy(
                    firstName = event.firstName
                ) }
            }
            is RecallContactEvent.setLastName -> {
                _state.update { it.copy(
                    lastName = event.lastName
                ) }
            }
            is RecallContactEvent.setPhoneNumber -> {
                _state.update { it.copy(
                    phoneNumber = event.phoneNumber
                ) }
            }
            RecallContactEvent.showDialog -> {
                _state.update { it.copy(
                    isAddingContact = true
                ) }
            }
            is RecallContactEvent.sortContacts -> {
                _sortType.value = event.sortType
            }
        }
    }

}