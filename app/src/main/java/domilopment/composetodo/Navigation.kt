package domilopment.composetodo

import androidx.compose.runtime.Composable
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
fun TodoNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavRoute.Todos.route) {
        composable(NavRoute.Todos.route) {
            TodoScreen {
                navController.navigate(NavRoute.Input.route)
            }
        }
        composable(NavRoute.Input.route) {
            InsertScreen {
                navController.navigateUp()
            }
        }
    }
}