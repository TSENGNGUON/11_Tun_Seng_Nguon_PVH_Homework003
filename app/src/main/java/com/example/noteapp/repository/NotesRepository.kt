package com.example.noteapp.repository

import android.util.Log
import com.example.noteapp.dao.NotesDao
import com.example.noteapp.model.Notes
import kotlinx.coroutines.flow.Flow

class NotesRepository(private val noteDao: NotesDao) {

    val allNotes: Flow<List<Notes>> = noteDao.getAllNotes()

    suspend fun insert(note: Notes){
        noteDao.insertNotes(note)
    }

    suspend fun update(note: Notes){
        Log.d("NotesRepository", "DAO update call for ${note.id} -> isSave=${note.isSave}")
        noteDao.update(note)
    }

    suspend fun delete(allNotes: List<Notes>){
        noteDao.delete(allNotes)
    }

}