package domilopment.composetodo.data

data class TodosUIState(
    val todos: List<Todo> = emptyList(),
    val selected: Boolean = false
)
