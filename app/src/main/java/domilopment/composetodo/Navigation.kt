package domilopment.composetodo

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import domilopment.composetodo.ui.home.TodoScreen
import domilopment.composetodo.ui.insert.InsertScreen

sealed class NavRoute(val route: String) {
    object Todos : NavRoute("todos_route")
    object Input : NavRoute("input_route")
}

@Composable
fun TodoNavHost(modifier: Modifier = Modifier, showSnackbar: (String) -> Unit) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavRoute.Todos.route,
        modifier = modifier
    ) {
        composable(NavRoute.Todos.route) {
            TodoScreen(
                onNavigate = { navController.navigate(NavRoute.Input.route) },
                showSnackbar = { showSnackbar(it) }
            )
        }
        composable(NavRoute.Input.route) {
            InsertScreen(
                onNavigate = { navController.navigateUp() },
                showSnackbar = { showSnackbar(it) }
            )
        }
    }
}