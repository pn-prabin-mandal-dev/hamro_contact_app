package com.example.hamrocontactsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.hamrocontactsapp.ui.theme.HamroContactsAppTheme

class MainActivity : ComponentActivity() {

    /*// Initialize the database lazily
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            ContactDatabase::class.java,
            "contacts.db"
        ).build()
    }
    private val viewModel by viewModels<ContactViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return ContactViewModel(db.Dao) as T
                }
            }
        }
    )*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val contactDb: ContactDatabase = ContactDatabase.getDatabaseInstance(applicationContext)
        val contactRepo: ContactRepository = ContactRepository(contactDb.contactDao())
        val notesViewModel = ContactViewModel(contactRepo)
        setContent {
            HamroContactsAppTheme {
                // by phillip
//                val state by notesViewModel.state.collectAsState()
//                val state = notesViewModel.state.collectAsState().value  // ✅ Works
                val state = notesViewModel.state.collectAsState()
                val onEvent = notesViewModel::onEvent
                ContactScreen(state = state.value, onEvent = onEvent)
            }
        }
    }
}

