package mate.pracainz.calendapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import mate.pracainz.calendapp.data.CalendarDataSource
import mate.pracainz.calendapp.ui.components.CalendarContent
import mate.pracainz.calendapp.ui.components.MenuItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendApp() {
    val dataSource = CalendarDataSource()
    var calendarUiModel by remember {
        mutableStateOf(
            dataSource.getMonthData(
                lastSelectedDate = dataSource.today
            )
        )
    }

    val items = listOf(
        MenuItem(
            id = "Home",
            title = "Home",
            contentDescription = "Go to the home page",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
        ),
        MenuItem(
            id = "Profile",
            title = "Profile",
            contentDescription = "Go to the profile page",
            selectedIcon = Icons.Filled.AccountCircle,
            unselectedIcon = Icons.Outlined.AccountCircle,
            badgeCount = 3
        ),
        MenuItem(
            id = "Settings",
            title = "Settings",
            contentDescription = "Go to the settings page",
            selectedIcon = Icons.Filled.Settings,
            unselectedIcon = Icons.Outlined.Settings,
            badgeCount = 1
        ),
    )

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                Spacer(modifier = Modifier.height(16.dp))
                items.forEachIndexed { index, item ->
                    NavigationDrawerItem(
                        label = {
                            Text(text = item.title)
                        },
                        selected = index == selectedItemIndex,
                        onClick = {
                            selectedItemIndex = index
                            scope.launch {
                                drawerState.close()
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = if (index == selectedItemIndex) {
                                    item.selectedIcon
                                } else item.unselectedIcon,
                                contentDescription = item.title
                            )
                        },
                        badge = {
                            item.badgeCount?.let {
                                Text(text = item.badgeCount.toString())
                            }
                        },
                        modifier = Modifier
                            .padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        },
        drawerState = drawerState
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "CalendApp")
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                drawerState.open()
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menu"
                            )
                        }
                    }
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = paddingValues.calculateTopPadding()
                    )
            ) {
                CalendarContent(
                    dataSource = dataSource,
                    calendarUiState = calendarUiModel,
                    onDateClickListener = { date ->
                        calendarUiModel = calendarUiModel.copy(
                            selectedDate = dataSource.toItemUiModel(date, true),
                            visibleDates = calendarUiModel.visibleDates.map {
                                it.copy(isSelected = it.date.isEqual(date))
                            }
                        )
                    },
                    onPrevClickListener = {
                        // Subtracting 1 month from the current selected date
                        val newSelectedDate = calendarUiModel.selectedDate.date.minusMonths(1)
                        calendarUiModel = dataSource.getMonthData(lastSelectedDate = newSelectedDate)
                    },
                    onNextClickListener = {
                        // Adding 1 month to the current selected date
                        val newSelectedDate = calendarUiModel.selectedDate.date.plusMonths(1)
                        calendarUiModel = dataSource.getMonthData(lastSelectedDate = newSelectedDate)
                    },
                    onResetClickListener = {
                        calendarUiModel = dataSource.getMonthData(lastSelectedDate = dataSource.today)
                    }
                )

            }
        }
    }
    val sheetState = rememberModalBottomSheetState()
    var isSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }
    val scaffoldState = rememberBottomSheetScaffoldState()
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {

        },
        sheetPeekHeight = 0.dp
    ) {

    }
}
