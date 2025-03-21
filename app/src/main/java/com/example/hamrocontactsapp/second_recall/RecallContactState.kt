package com.example.hamrocontactsapp.second_recall

data class RecallContactState(
    val contacts: List<RecallContactEntity> = emptyList(),
    val firstName: String = "",
    val lastName: String = "",
    val phoneNumber: String = "",
    val isAddingContact: Boolean = false,
    val sortType: RecallSortType = RecallSortType.FIRST_NAME
)

enum class RecallSortType {
    FIRST_NAME,
    LAST_NAME,
    PHONE_NUMBER
}
