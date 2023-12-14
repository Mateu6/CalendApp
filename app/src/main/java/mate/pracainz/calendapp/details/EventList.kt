package mate.pracainz.calendapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import mate.pracainz.calendapp.data.CalendarDataSource
import mate.pracainz.calendapp.ui.layout.CalendarUiState
import java.time.format.DateTimeFormatter

@Composable
fun EventList(
    calendarUiState: CalendarUiState,
    events: List<EventItem>
) {
    val dataSource = CalendarDataSource()
    var calendarUiModel by remember {
        mutableStateOf(
            dataSource.getMonthData(
                lastSelectedDate = dataSource.today
            )
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        LazyRow(
            modifier = Modifier
                .align(alignment = CenterHorizontally),
        ) {
            item {
                Text(
                    text = "Events for ${
                        calendarUiState.selectedDate.date.format(
                            DateTimeFormatter.ofPattern(
                                "EEEE, d MMMM yyyy"
                            )
                        )
                    }",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(8.dp),
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            if (events.isNotEmpty()) {
                LazyColumn {
                    items(events) { event ->
                        EventListItem(event = event)
                        Divider() // Add a divider between events
                    }
                }
            } else {
                Text(
                    text = "No events for this date.",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier
                        .align(Center)
                        .alpha(0.5f),
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}
    @Composable
    fun EventListItem(event: EventItem) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text(text = event.title, style = MaterialTheme.typography.labelSmall)
                Text(text = event.description ?: "", style = MaterialTheme.typography.labelSmall)

                // Handle different types of events
                when (event) {
                    is BasicEvent -> {
                        // Additional UI elements specific to BasicEvent
                    }

                    is TimerEvent -> {
                        // Additional UI elements specific to TimerEvent
                    }

                    is ReminderEvent -> {
                        // Additional UI elements specific to ReminderEvent
                    }
                    // Add more cases as needed for other event types
                    else -> {}
                }
            }
        }
    }