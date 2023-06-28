package domilopment.composetodo.ui.insert

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import domilopment.composetodo.data.Todo

@Composable
fun InsertScreen(
    onNavigate: () -> Unit,
    appBarState: (String, Boolean) -> Unit,
    showSnackbar: (String) -> Unit
) {
    val viewModel = hiltViewModel<InputViewModel>()
    val uiState by viewModel.insertUiState.collectAsState()
    val isEditMode = uiState.todoId != -1L

    appBarState("Add Todo", true)

    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier)
        Column(
            modifier = Modifier
                .padding(16.dp)
                .background(MaterialTheme.colorScheme.primaryContainer)
                .border(1.dp, MaterialTheme.colorScheme.outline, MaterialTheme.shapes.small)
                .padding(46.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = uiState.todoTitle,
                onValueChange = { viewModel.onInsertTextChanges(it) },
                label = { Text(text = "Todo") },
                modifier = Modifier.background(MaterialTheme.colorScheme.background)
            )

            Spacer(modifier = Modifier)

            Button(onClick = {
                val todo = if (isEditMode) Todo(
                    id = uiState.todoId, title = uiState.todoTitle
                ) else Todo(title = uiState.todoTitle)
                viewModel.addTodo(todo)
                showSnackbar("Item ${uiState.todoTitle} added")
                onNavigate()
            }) {
                Text(text = if (isEditMode) "Update" else "Save")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}