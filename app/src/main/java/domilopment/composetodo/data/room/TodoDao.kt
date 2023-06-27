package domilopment.composetodo.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import domilopment.composetodo.data.Todo
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Query("SELECT * FROM todo")
    fun getTodos(): Flow<List<Todo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: Todo)

    @Query("DELETE FROM todo WHERE id = :id")
    suspend fun deleteTodo(id: Long)

    @Query("UPDATE todo SET done = :done WHERE id = :id")
    suspend fun updateTodo(id: Long, done: Boolean)
}