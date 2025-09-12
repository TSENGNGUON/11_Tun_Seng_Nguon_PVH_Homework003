package com.example.noteapp.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.noteapp.R
import com.example.noteapp.model.Notes

class NotesViewModel: ViewModel() {
    var notes = mutableStateListOf<Notes>()
        private set

    init {
        notes.addAll(
            listOf(
                Notes(
                    1, "Notes App",
                    "adipiscing elit. Nullam mattis fringilla",
                    R.drawable.tanchiro,
                    isSave = true
                ),
                Notes(
                    2, "Notes App",
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam mattis fringilla",
                    R.drawable.demon_slayer_fire,
                    isSave = false
                ),
                Notes(
                    3, "Notes App",
                    "Lorem ipsum dolor sit amet, consectetur ",
                    R.drawable.tomioka,
                    isSave = false
                ),
            )
        )
    }

    fun toggleSave(note: Notes) {
        // Step 1: Find where this note is inside the list
        val index = notes.indexOfFirst { it.id == note.id }

        // Step 2: If we found it
        if (index != -1) {
            // Step 3: Copy the note, but flip the "isSaved" value
            val updatedNote = notes[index].copy(
                isSave = !notes[index].isSave
            )

            // Step 4: Replace the old note with the new one
            notes[index] = updatedNote
        }
    }
}