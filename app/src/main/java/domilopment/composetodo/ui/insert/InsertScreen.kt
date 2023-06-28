package domilopment.composetodo.ui.insert

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import domilopment.composetodo.data.Todo
import domilopment.composetodo.ui.TodosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertScreen(onNavigate: () -> Unit, showSnackbar: (String) -> Unit) {
    val viewModel = hiltViewModel<TodosViewModel>()
    val uiState by viewModel.insertUiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Todo") },
                navigationIcon = {
                    IconButton(onClick = { onNavigate() }) {
                        Icon(Icons.Filled.ArrowBack, null, tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(value = uiState.todoTitle,
                onValueChange = { viewModel.onInsertTextChanges(it) },
                label = { Text(text = "Todo") })
            Button(onClick = {
                viewModel.addTodo(Todo(title = uiState.todoTitle))
                showSnackbar("Item ${uiState.todoTitle} added")
                onNavigate()
            }) {
                Text(text = "Save")
            }
        }
    }
}