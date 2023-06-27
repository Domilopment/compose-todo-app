package domilopment.composetodo

import android.app.Application
import domilopment.composetodo.data.TodoRepository
import domilopment.composetodo.data.room.TodoRoomDatabase

class TodoApplication : Application() {
    val database by lazy { TodoRoomDatabase.getDatabase(this) }
    val repository by lazy { TodoRepository(database.todoDao()) }
}