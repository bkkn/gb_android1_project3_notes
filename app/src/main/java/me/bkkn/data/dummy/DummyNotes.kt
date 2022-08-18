package me.bkkn.data.dummy

import me.bkkn.domain.entity.Note
import me.bkkn.domain.repository.Notes

class DummyNotes : Notes {
    private var list: MutableList<Note> = mutableListOf()
    override fun getList(): MutableList<Note> {
        return list
    }

    override fun deleteNote(note: Note) {
        list.remove(note)
    }

    override fun changeTitle(note: Note, newTitle: String) {
        val index = list.indexOf(note)
        list[index].title = newTitle
    }

    override fun editNote(note: Note, newTitle: String, newContent: String) {
        val index = list.indexOf(note)
        list[index].title = newTitle
        list[index].text = newContent
    }

    override fun addNewNote() {
        val i = list.size
        list.add(Note("new$i", "new$i", 0))
    }

    init {
        for (i in 0..9) {
            list.add(Note("title$i", "text$i", 0))
        }
    }
}