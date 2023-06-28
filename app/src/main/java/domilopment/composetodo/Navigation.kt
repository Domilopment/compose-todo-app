package domilopment.composetodo

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import domilopment.composetodo.data.AppBarState
import domilopment.composetodo.ui.home.TodoScreen
import domilopment.composetodo.ui.insert.InsertScreen

sealed class NavRoute(val route: String) {
    object Todos : NavRoute("todos_route")
    object Input : NavRoute("input_route")
}

@Composable
fun TodoNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    appBarState: (String, Boolean) -> Unit,
    showSnackbar: (String) -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = NavRoute.Todos.route,
        modifier = modifier
    ) {
        composable(NavRoute.Todos.route) {
            TodoScreen(
                onNavigate = { navController.navigate(NavRoute.Input.route) },
                appBarState = { title, arrowBack -> appBarState(title, arrowBack) },
                showSnackbar = { showSnackbar(it) }
            )
        }
        composable(NavRoute.Input.route) {
            InsertScreen(
                onNavigate = { navController.navigateUp() },
                appBarState = { title, arrowBack -> appBarState(title, arrowBack) },
                showSnackbar = { showSnackbar(it) }
            )
        }
    }
}