package domilopment.composetodo.data

import androidx.annotation.WorkerThread
import domilopment.composetodo.data.room.TodoDao

class TodoRepository(private val todoDao: TodoDao) {
    val todos = todoDao.getTodos()

    @WorkerThread
    suspend fun insertTodo(todo: Todo) {
        todoDao.insertTodo(todo)
    }

    @WorkerThread
    suspend fun deleteTodo(todo: Todo) {
        todoDao.deleteTodo(todo.id)
    }

    @WorkerThread
    suspend fun updateTodo(todo: Todo, done: Boolean) {
        todoDao.updateTodo(todo.id, done)
    }
}