package mate.pracainz.calendapp.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import mate.pracainz.calendapp.base.CalendAppNavDrawer
import mate.pracainz.calendapp.base.CalendAppTopAppBar
import mate.pracainz.calendapp.base.menuItems
import mate.pracainz.calendapp.calendar.CalendarView
import mate.pracainz.calendapp.calendar.model.CalendarViewModel
import mate.pracainz.calendapp.details.EventEditor
import mate.pracainz.calendapp.details.EventEditorViewModel
import mate.pracainz.calendapp.details.EventList
import mate.pracainz.calendapp.details.EventListViewModel

@Composable
fun CalendarScreen(
    calendarViewModel: CalendarViewModel,
    onNavigateToSettings: () -> Unit,
    onNavigateToProfile: () -> Unit
) {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    ModalNavigationDrawer(
        drawerContent = {
            CalendAppNavDrawer(
                menuItems = menuItems,
                selectedItemIndex = selectedItemIndex,
                onMenuItemClick = { index ->
                    when (index) {
                        0 -> {}
                        1 -> onNavigateToProfile()
                        2 -> onNavigateToSettings()
                    }
                },
                onDrawerStateChange = {
                    scope.launch {
                        drawerState.open()
                    }
                }
            )
        },
        drawerState = drawerState
    ) {
        Scaffold(
            topBar = {
                CalendAppTopAppBar {
                    scope.launch {
                        drawerState.open()
                    }
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = paddingValues.calculateTopPadding()
                    )
            ) {
                CalendarView(
                    viewModel = calendarViewModel
                )
                Divider(
                    modifier = Modifier
                        .padding(top = 8.dp)
                )

                when (calendarViewModel.selectedTabIndex.collectAsState().value) {
                    0 -> {
                        EventList(
                            calendarUiState = calendarViewModel.calendarUiState.collectAsState().value.copy(),
                            viewModel = EventListViewModel()
                        )
                    }

                    1 -> {
                        EventEditor(
                            viewModel = EventEditorViewModel()
                        )
                    }
                }
            }
        }
    }
}