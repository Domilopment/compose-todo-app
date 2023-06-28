package domilopment.composetodo.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import domilopment.composetodo.data.Todo

@Composable
fun TodoScreen(
    onNavigate: (Todo?) -> Unit,
    appBarState: (String, Boolean) -> Unit,
    showSnackbar: (String) -> Unit
) {
    val viewModel = hiltViewModel<TodosViewModel>()
    val uiState by viewModel.uiState.collectAsState()

    appBarState("Todo App", false)

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { onNavigate(null) }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        },
    ) { paddingValues ->
        if (uiState.todos.isEmpty()) Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .wrapContentSize(Alignment.Center),
        ) {
            Text(
                text = "Add Todos",
                textAlign = TextAlign.Center,
                style = typography.titleLarge
            )
        }
        else Column(modifier = Modifier.padding(paddingValues)) {
            val done = uiState.todos.filter { it.done }
            val undone = uiState.todos.filter { !it.done }

            Text(
                text = "Todos: ${undone.size}",
                style = typography.titleLarge,
                modifier = Modifier.padding(8.dp)
            )

            LazyColumn {
                items(undone) { todo: Todo ->
                    TodoItem(
                        todo = todo,
                        onValueChanged = { viewModel.updateTodo(todo, it) },
                        onDelete = { viewModel.deleteTodo(it) },
                        onNavigate = { onNavigate(it) },
                        showSnackbar = { showSnackbar(it) }
                    )
                }
            }

            Text(
                text = "Done: ${done.size}",
                style = typography.titleLarge,
                modifier = Modifier.padding(8.dp)
            )

            LazyColumn {
                items(done) { todo: Todo ->
                    TodoItem(
                        todo = todo,
                        onValueChanged = { viewModel.updateTodo(todo, it) },
                        onDelete = { viewModel.deleteTodo(it) },
                        onNavigate = { onNavigate(it) },
                        showSnackbar = { showSnackbar(it) }
                    )
                }
            }
        }
    }
}

@Composable
fun TodoItem(
    todo: Todo,
    onValueChanged: (Boolean) -> Unit,
    onDelete: (Todo) -> Unit,
    onNavigate: (Todo) -> Unit,
    showSnackbar: (String) -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.primaryContainer,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(8.dp, 4.dp)
            .clickable { onNavigate(todo) },
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Checkbox(checked = todo.done, onCheckedChange = { onValueChanged(it) })

            Spacer(modifier = Modifier.width(8.dp))

            Text(text = todo.title, modifier = Modifier, style = typography.bodyLarge)

            Spacer(
                modifier = Modifier
                    .width(8.dp)
                    .weight(1f)
            )

            IconButton(onClick = { onDelete(todo); showSnackbar("Todo ${todo.title} deleted") }) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = null)
            }
        }
    }
}