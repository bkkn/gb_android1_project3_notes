package me.bkkn.domain.repository

import me.bkkn.domain.entity.Note

interface Notes {
    fun getList(): MutableList<Note>
    fun deleteNote(note: Note)
    fun changeTitle(note: Note, newTitle: String)
    fun editNote(note: Note, newTitle: String, newContent: String)
    fun addNewNote()
    fun index(note: Note): Int {
        return getList().indexOf(note)
    }
}