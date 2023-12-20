package mate.pracainz.calendapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import mate.pracainz.calendapp.calendar.model.CalendarDataSource
import mate.pracainz.calendapp.calendar.model.CalendarViewModel
import mate.pracainz.calendapp.home.CalendarScreen
import mate.pracainz.calendapp.profile.ProfileScreen
import mate.pracainz.calendapp.settings.SettingsScreen
import mate.pracainz.calendapp.ui.CalendAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalendAppTheme {
                CalendAppNavigation()
            }
        }
    }

    @Composable
    fun CalendAppNavigation() {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = Routes.Home.route
        ) {
            composable(Routes.Home.route) {
                val viewModel = CalendarViewModel(dataSource = CalendarDataSource())
                CalendarScreen(
                    calendarViewModel = viewModel,
                    onNavigateToSettings = {
                        navController.navigate(Routes.Settings.route)
                    },
                    onNavigateToProfile = {
                        navController.navigate(Routes.Profile.route)
                    }
                )
            }
            composable(Routes.Settings.route) {
                SettingsScreen()
            }
            composable(Routes.Profile.route) {
                ProfileScreen()
            }
        }
    }

    sealed class Routes(val route: String) {
        object Home : Routes("home")
        object Settings : Routes("settings")
        object Profile : Routes("profile")
    }


    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        CalendAppTheme {
            Greeting("Android")
        }
    }
}