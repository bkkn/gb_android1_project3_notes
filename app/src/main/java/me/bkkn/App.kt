package me.bkkn

import android.app.Application
import android.content.Context
import me.bkkn.data.dummy.DummyNotes
import me.bkkn.domain.repository.Notes

class App : Application() {
    val notes: Notes = DummyNotes()
    override fun onCreate() {
        super.onCreate()
    }

    companion object {
        operator fun get(context: Context): App {
            return context.applicationContext as App
        }
    }
}