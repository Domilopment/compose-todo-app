package domilopment.composetodo.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import domilopment.composetodo.TodoApplication
import domilopment.composetodo.data.InsertUiState
import domilopment.composetodo.data.Todo
import domilopment.composetodo.data.TodoRepository
import domilopment.composetodo.data.TodosUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TodosViewModel(private val todoRepository: TodoRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<TodosUIState> = MutableStateFlow(TodosUIState())
    val uiState: StateFlow<TodosUIState> = _uiState.asStateFlow()

    private val _insertUiState: MutableStateFlow<InsertUiState> = MutableStateFlow(InsertUiState())
    val insertUiState: StateFlow<InsertUiState> = _insertUiState.asStateFlow()

    init {
        viewModelScope.launch {
            todoRepository.todos.collect {
                _uiState.update { state ->
                    state.copy(
                        todos = it
                    )
                }
            }
        }
    }

    fun updateTodo(todo: Todo, done: Boolean) = viewModelScope.launch {
        todoRepository.updateTodo(todo, done)
    }

    fun deleteTodo(todo: Todo) = viewModelScope.launch {
        todoRepository.deleteTodo(todo)
    }

    fun addTodo(todo: Todo) = viewModelScope.launch {
        todoRepository.insertTodo(todo)
    }

    fun onInsertTextChanges(newTodoTitle: String) {
        _insertUiState.update { state ->
            state.copy(todoTitle = newTodoTitle)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val myRepository = (this[APPLICATION_KEY] as TodoApplication).repository
                TodosViewModel(myRepository)
            }
        }
    }
}
