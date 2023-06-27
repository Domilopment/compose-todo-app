package domilopment.composetodo.ui.insert

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import domilopment.composetodo.data.Todo
import domilopment.composetodo.ui.TodosViewModel

@Composable
fun InsertScreen(onNavigate: () -> Unit) {
    val viewModel = hiltViewModel<TodosViewModel>()
    val uiState by viewModel.insertUiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(value = uiState.todoTitle,
            onValueChange = { viewModel.onInsertTextChanges(it) },
            label = { Text(text = "Todo") })
        Button(onClick = {
            viewModel.addTodo(Todo(title = uiState.todoTitle))
            onNavigate()
        }) {
            Text(text = "Save")
        }
    }
}