package uiScreen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import data.NoteDatabase
import data.NoteRepository
import domain.Note
import viewModel.NoteViewModel
import viewModel.NoteViewModelFactory
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun NoteScreen() {
    val context = LocalContext.current
    val db = remember { NoteDatabase.getDatabase(context) }
    val repo = remember { NoteRepository(db.noteDao()) }
    val factory = remember { NoteViewModelFactory(repo) }
    val viewModel: NoteViewModel = viewModel(factory = factory)

    val notes by viewModel.notes.collectAsState()
    var newNoteText by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {

        OutlinedTextField(
            value = newNoteText,
            onValueChange = { newNoteText = it },
            label = { Text("أدخل الملاحظة") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(onClick = {
            if (newNoteText.isNotBlank()) {
                viewModel.addNote(Note(title = newNoteText, timestamp = System.currentTimeMillis()))
                newNoteText = ""
            }
        }) {
            Text("إضافة ملاحظة")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(notes) { note ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable {
                            viewModel.deleteNote(note)
                            Toast
                                .makeText(context, "تم حذف الملاحظة", Toast.LENGTH_SHORT)
                                .show()
                        }
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = note.title)
                        Text(text = formatDate(note.timestamp))
                    }
                }
            }
        }
    }
}

fun formatDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
    return sdf.format(Date(timestamp))
}
