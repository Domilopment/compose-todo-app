package domilopment.composetodo.ui.insert

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import domilopment.composetodo.data.InsertUiState
import domilopment.composetodo.data.Todo
import domilopment.composetodo.data.TodoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InputViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val todoRepository: TodoRepository
) : ViewModel() {

    private val _insertUiState: MutableStateFlow<InsertUiState> = MutableStateFlow(InsertUiState())
    val insertUiState: StateFlow<InsertUiState> = _insertUiState.asStateFlow()

    init {
        viewModelScope.launch {
            todoRepository.todos.collect { todos ->
                todos.find { it.id == savedStateHandle["todoId"] }?.also {
                    _insertUiState.update { state ->
                        state.copy(
                            todoTitle = it.title,
                            todoId = it.id
                        )
                    }
                }
            }
        }
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