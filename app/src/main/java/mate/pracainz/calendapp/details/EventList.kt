package mate.pracainz.calendapp.details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import mate.pracainz.calendapp.calendar.model.CalendarUiState
import java.time.format.DateTimeFormatter

@Composable
fun EventList(
    viewModel: EventListViewModel,
    calendarUiState: CalendarUiState
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
        ) {
            LazyRow(
                modifier = Modifier.align(CenterHorizontally),
            ) {
                item {
                    Text(
                        text = "Events for ${calendarUiState.selectedDate.date.format(DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy"))}",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(8.dp),
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
            }

            if (viewModel.events.isNotEmpty()) {
                LazyColumn {
                    items(viewModel.events) { event ->
                        EventItemCard(event = event, onClick = { viewModel.handleEventClick(event) })
                        Divider() // Add a divider between events
                    }
                }
            } else {
                Text(
                    text = "No events for this date.",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier
                        .align(CenterHorizontally)
                        .padding(top = 16.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}

@Composable
fun EventItemCard(event: EventItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(text = event.title, style = MaterialTheme.typography.labelSmall)
            Text(text = event.description ?: "", style = MaterialTheme.typography.labelSmall)
        }
    }
}
