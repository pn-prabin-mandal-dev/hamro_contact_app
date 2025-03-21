package com.example.hamrocontactsapp.second_recall.ui_layer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hamrocontactsapp.second_recall.RecallContactEvent
import com.example.hamrocontactsapp.second_recall.RecallContactState

@Composable
fun RecallContactDialog(
    state: RecallContactState,
    onEvent: (RecallContactEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    AlertDialog(
        onDismissRequest = {
            onEvent(RecallContactEvent.hideDialog)
        },
        title = {Text(text = "Add Contact")},
        text = {
            Column (
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ){
                TextField(
                    value = state.firstName,
                    onValueChange = {onEvent(RecallContactEvent.setFirstName(it))},
                    placeholder = {Text(text = "First Name")}
                )
                TextField(
                    value = state.lastName,
                    onValueChange = {onEvent(RecallContactEvent.setLastName(it))},
                    placeholder = {Text(text = "Last Name")}
                )
                TextField(
                    value = state.phoneNumber,
                    onValueChange = {onEvent(RecallContactEvent.setPhoneNumber(it))},
                    placeholder = {Text(text = "Phone Number")}
                )
            }
        },

        confirmButton = {
            Box(
                contentAlignment = Alignment.BottomEnd
            ) {
                Button(
                    onClick = {
                        onEvent(RecallContactEvent.saveContact)
                    }
                ) {
                    Text(text = "Save")
                }
            }
        },
        /*dismissButton = {  // immediately hide dialog
            onEvent(RecallContactEvent.hideDialog)
        }*/
    )
}

