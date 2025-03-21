package com.example.hamrocontactsapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.lazy.items  // Note to this line with items
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun ContactScreen(
    state: ContactState,
    onEvent: (ContactEvents) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEvent(ContactEvents.ShowDialog) }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Contact")
            }
        }
    ) { innerPadding ->
        if (state.isAddingContact) {
            AddContactDialog(state = state, onEvent = onEvent)
        }

        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    verticalAlignment = CenterVertically
                ) {
                    SortType.entries.forEach { sortType ->// Iterate over all possible SortType entries (like FIRST_NAME, LAST_NAME, etc.)
                        Row(
                            modifier = Modifier.clickable { // Make each Row clickable, triggering an event when clicked
                                onEvent(ContactEvents.SortContacts(sortType)) // // Call the onEvent function when clicked with the specific sortType
                            },
                            verticalAlignment = CenterVertically
                        ) {
                            RadioButton(
                                selected = state.sortType == sortType, // Mark the RadioButton as selected if the current sortType matches the state
                                onClick = {
                                    onEvent(ContactEvents.SortContacts(sortType))
                                }
                            )
                            Text(text = sortType.name)  // always called lamdas. here, sortType
                        }
                    }
                }
            }

            items(state.contacts) { contact ->  // if error with items, add correct import
                Row(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "${contact.FirstName} ${contact.LastName}",
                            fontSize = 20.sp
                        )
                        Text(text = contact.PhoneNumber, fontSize = 12.sp)
                    }
                    IconButton(onClick = { onEvent(ContactEvents.DeleteContact(contact)) }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete Contact"
                        )
                    }
                }
            }
        }
    }
}
