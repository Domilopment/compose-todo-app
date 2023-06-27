package domilopment.composetodo.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import domilopment.composetodo.ui.TodosViewModel
import domilopment.composetodo.data.Todo

@Composable
fun TodoScreen(onNavigate: () -> Unit) {
    val viewModel = hiltViewModel<TodosViewModel>()
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = { onNavigate() }) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null)
        }
    }) { paddingValues ->
        if (uiState.todos.isEmpty()) Text(
            text = "Add Todos", modifier = Modifier.padding(paddingValues)
        )
        else LazyColumn(modifier = Modifier.padding(paddingValues)) {
            items(uiState.todos) { todo: Todo ->
                TodoItem(todo = todo,
                    onValueChanged = { viewModel.updateTodo(todo, it) },
                    onDelete = { viewModel.deleteTodo(it) })
            }
        }
    }
}

@Composable
fun TodoItem(todo: Todo, onValueChanged: (Boolean) -> Unit, onDelete: (Todo) -> Unit) {
    Surface(
        color = MaterialTheme.colorScheme.primaryContainer,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        shape = MaterialTheme.shapes.small,
        modifier = Modifier.padding(8.dp, 4.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Checkbox(checked = todo.done, onCheckedChange = { onValueChanged(it) })
            Text(text = todo.title)
            IconButton(onClick = { onDelete(todo) }) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = null)
            }
        }
    }
}