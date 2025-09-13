package com.example.noteapp.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.R
import com.example.noteapp.model.Notes
import com.example.noteapp.repository.NotesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

open class NotesViewModel(
    private val repository: NotesRepository
): ViewModel() {

    open val allNotes = repository.allNotes


    fun insert(note: Notes){
        viewModelScope.launch {
            repository.insert(note)
        }
    }

    fun update(note: Notes){
        viewModelScope.launch {
            Log.d("NotesViewModel", "Updating note ${note.id} isSave=${note.isSave}")
            repository.update(note)
        }
    }

    fun delete(allNotes: List<Notes>){
        viewModelScope.launch {
            repository.delete(allNotes)
        }
    }


}