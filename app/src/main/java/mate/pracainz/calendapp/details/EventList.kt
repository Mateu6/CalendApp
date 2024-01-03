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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import mate.pracainz.calendapp.calendar.model.CalendarUiState
import mate.pracainz.calendapp.details.model.EventItem
import mate.pracainz.calendapp.details.model.EventListViewModel
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
                .padding(8.dp)
        ) {
            LazyRow(
                modifier = Modifier
                    .zIndex(5f)
                    .align(CenterHorizontally),
            ) {
                item {
                    Text(
                        text = "Events for ${calendarUiState.selectedDate.date.format(DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy"))}",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(bottom = 8.dp),
                        color = when {
                            calendarUiState.selectedDate.isToday -> MaterialTheme.colorScheme.onPrimaryContainer
                            calendarUiState.selectedDate.isSelected -> MaterialTheme.colorScheme.onTertiaryContainer
                            else -> MaterialTheme.colorScheme.tertiary
                        },
                    )
                }
            }

            if (viewModel.events.isNotEmpty()) {
                LazyColumn {
                    items(viewModel.events) { event ->
                        EventItemCard(event = event, onClick = { viewModel.handleEventClick(event) })
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
            .clickable { onClick() },
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.tertiaryContainer)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = event.title,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary
            )
            Text(text = event.description ?: "", style = MaterialTheme.typography.labelSmall)
        }
    }
}
