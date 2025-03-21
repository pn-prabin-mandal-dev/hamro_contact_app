package com.example.hamrocontactsapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Contact(
    val FirstName: String,
    val LastName: String,
    val PhoneNumber: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
