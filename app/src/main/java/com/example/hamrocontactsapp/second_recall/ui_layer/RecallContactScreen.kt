package com.example.hamrocontactsapp.second_recall.ui_layer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hamrocontactsapp.second_recall.RecallContactEvent
import com.example.hamrocontactsapp.second_recall.RecallContactState
import com.example.hamrocontactsapp.second_recall.RecallSortType

@Composable
fun RecallContactScreen(
    state: RecallContactState,
    onEvent: (RecallContactEvent) -> Unit,
) {
    Scaffold(
        floatingActionButton = {
            IconButton(
                onClick = {
                    onEvent(RecallContactEvent.showDialog)
                }
            ) { Icon(Icons.Default.Add, contentDescription = "Add Icon") }
        }
    ) { paddingValues ->

        if (state.isAddingContact){
            RecallContactDialog(state = state, onEvent = onEvent)
        }
        LazyColumn(
            contentPadding = paddingValues,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)

                ) {
                    RecallSortType.entries.forEach { sortType ->
                        Row(
                            modifier = Modifier.clickable {
                                onEvent(RecallContactEvent.sortContacts(sortType = sortType))
                            },
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            RadioButton(
                                selected = state.sortType == sortType,  // don't forgot to call this
                                onClick = { onEvent(RecallContactEvent.sortContacts(sortType = sortType)) }
                            )
                            Text(text = sortType.name)
                        }
                    }
                }
            }

            items(state.contacts) { contact ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column (
                        modifier = Modifier.weight(8f)
                    ){
                        Text(
                            text = "${contact.firstName} ${contact.lastName}",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(text = "${contact.phoneNumber}", fontSize = 18.sp)
                    }

                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Delete Icon",
                        modifier = Modifier.clickable {
                            onEvent(RecallContactEvent.deleteContact(contact))
                        }
                    )
                }
            }
        }

    }
}