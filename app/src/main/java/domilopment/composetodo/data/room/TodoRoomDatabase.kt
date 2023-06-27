package domilopment.composetodo.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import domilopment.composetodo.data.Todo

@Database(entities = [Todo::class], version = 1, exportSchema = false)
abstract class TodoRoomDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}
