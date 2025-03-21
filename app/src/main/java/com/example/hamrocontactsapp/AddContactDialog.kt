package com.example.hamrocontactsapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun AddContactDialog(
    state: ContactState,
    onEvent: (ContactEvents) -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = { onEvent(ContactEvents.HideDialog) },
        title = { Text(text = "Add Contact") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                TextField(
                    value = state.firstName,
                    onValueChange = { onEvent(ContactEvents.SetFirstName(it)) },
                    placeholder = { Text(text = "First Name") }
                )
                TextField(
                    value = state.lastName,
                    onValueChange = { onEvent(ContactEvents.SetLastName(it)) },
                    placeholder = { Text(text = "Last Name") }
                )
                TextField(
                    value = state.phoneNumber,
                    onValueChange = { onEvent(ContactEvents.SetPhoneNumber(it)) },
                    placeholder = { Text(text = "Phone Number") }
                )
            }
        },
        confirmButton = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                Button(onClick = { onEvent(ContactEvents.SaveContact) }) {
                    Text(text = "Save")
                }
            }
        }
    )
}
