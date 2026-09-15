package com.tp.taskflow.feature.note.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tp.taskflow.core.ui.EnvironmentBanner
import com.tp.taskflow.feature.note.domain.Note

@Composable
fun NotesScreen(
    viewModel: NotesViewModel,
    onBack: () -> Unit
) {
    val notes by viewModel.notes.collectAsStateWithLifecycle()
    val query by viewModel.query.collectAsStateWithLifecycle()
    val title by viewModel.draftTitle.collectAsStateWithLifecycle()
    val body by viewModel.draftBody.collectAsStateWithLifecycle()

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Notes", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                    Text(
                        "Chapter 7 • Room + DataStore",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                    EnvironmentBanner()
                }
                OutlinedButton(onClick = onBack) { Text("Back") }
            }
            OutlinedTextField(
                value = query,
                onValueChange = viewModel::onQueryChange,
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Search notes") },
                singleLine = true
            )
            OutlinedTextField(
                value = title,
                onValueChange = viewModel::onDraftTitleChange,
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Title") },
                singleLine = true
            )
            OutlinedTextField(
                value = body,
                onValueChange = viewModel::onDraftBodyChange,
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Body") }
            )
            Button(onClick = viewModel::saveDraft, modifier = Modifier.fillMaxWidth()) {
                Text("Save note")
            }
            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.weight(1f)) {
                items(notes, key = { it.id }) { note ->
                    NoteRow(note = note, onDelete = { viewModel.delete(note.id) })
                }
            }
        }
    }
}

@Composable
private fun NoteRow(note: Note, onDelete: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(note.title, fontWeight = FontWeight.SemiBold)
            Text(note.body, style = MaterialTheme.typography.bodyMedium)
            OutlinedButton(onClick = onDelete) { Text("Delete") }
        }
    }
}
