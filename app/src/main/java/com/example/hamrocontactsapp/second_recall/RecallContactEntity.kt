package com.example.hamrocontactsapp.second_recall

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Recall Contact Table")
data class RecallContactEntity (
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
)