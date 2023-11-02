package mate.pracainz.calendapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch
import mate.pracainz.calendapp.ui.components.AppBar
import mate.pracainz.calendapp.ui.components.MenuItem
import mate.pracainz.calendapp.ui.drawer.SideDrawer
import mate.pracainz.calendapp.ui.drawer.SideDrawerBody
import mate.pracainz.calendapp.ui.theme.CalendAppTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalendAppTheme {
                val drawerState = rememberDrawerState(DrawerValue.Closed)
                val scope = rememberCoroutineScope()
                AppBar(
                    onMenuIconClick =
                    {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                )
                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        SideDrawer()
                        SideDrawerBody(
                            items = listOf(
                                MenuItem(
                                    id = "home",
                                    title = "Home",
                                    contentDescription = "Go to homescreen",
                                    icon = Icons.Default.Home
                                ),
                                MenuItem(
                                    id = "account",
                                    title = "Account",
                                    contentDescription = "Go to account screen",
                                    icon = Icons.Default.AccountCircle
                                ),
                                MenuItem(
                                    id = "settings",
                                    title = "Settings",
                                    contentDescription = "Go to settings",
                                    icon = Icons.Default.Settings
                                )
                            ),
                            onItemClick = {
                                println("Clicked on ${it.id}")
                            }
                        )
                    },
                    gesturesEnabled = true
                ) {

                }
            }
        }
    }
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