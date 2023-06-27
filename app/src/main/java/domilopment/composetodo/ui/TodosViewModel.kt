package domilopment.composetodo.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import domilopment.composetodo.data.InsertUiState
import domilopment.composetodo.data.Todo
import domilopment.composetodo.data.TodoRepository
import domilopment.composetodo.data.TodosUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodosViewModel @Inject constructor(private val todoRepository: TodoRepository) : ViewModel() {
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
}
