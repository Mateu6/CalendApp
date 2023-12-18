
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import mate.pracainz.calendapp.details.BasicEvent
import mate.pracainz.calendapp.details.EventItem
import mate.pracainz.calendapp.details.ReminderEvent
import mate.pracainz.calendapp.details.TimerEvent
import mate.pracainz.calendapp.calendar.data.CalendarUiState
import java.time.LocalDate
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventEditor(
    onAddEvent: (EventItem) -> Unit,
    calendarUiState: CalendarUiState,
) {
    // State variables for event details
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var selectedEventType by remember { mutableStateOf("Basic") }

    // Column for EventEditor
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Set of FilterChips for selecting event type
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        ) {
            setOf("Basic", "Timer", "Reminder").forEach { eventType ->
                item {
                    var isSelected by remember { mutableStateOf(eventType == selectedEventType) }
                    FilterChip(
                        selected = isSelected,
                        onClick = {
                            isSelected = true
                            if (isSelected) {
                                selectedEventType = eventType
                            }
                        },
                        label = { Text(text = eventType) },
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }
            }
        }

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column {
                // Input fields for new event
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Event Title") }
                )
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Event Description") }
                )
            }
            val contextForToast = LocalContext.current.applicationContext

            // FloatingActionButton to add the new event
            FloatingActionButton(
                onClick = {
                    val newEvent = when (selectedEventType) {
                        "Basic" -> BasicEvent(
                            title = title,
                            description = description,
                            dateOfExecution = selectedDate,
                            isToday = selectedDate == LocalDate.now()
                        )
                        "Timer" -> TimerEvent(
                            title = title,
                            description = description,
                            dateOfExecution = selectedDate,
                            startTime = LocalDateTime.now(), // Provide appropriate values
                            endTime = LocalDateTime.now().plusHours(1), // Provide appropriate values
                            isToday = selectedDate == LocalDate.now()
                        )
                        "Reminder" -> ReminderEvent(
                            title = title,
                            description = description,
                            dateOfExecution = selectedDate,
                            reminderTime = LocalDateTime.now().plusMinutes(30), // Provide appropriate values
                            isToday = selectedDate == LocalDate.now()
                        )
                        else -> throw IllegalArgumentException("Unknown event type: $selectedEventType")
                    }

                    if (newEvent.title.isNotEmpty()) {
                        onAddEvent(newEvent)
                        Toast.makeText(contextForToast, "Event added!", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Toast.makeText(contextForToast, "Add title to event!", Toast.LENGTH_SHORT)
                            .show()
                    }
                },
                modifier = Modifier
                    .padding(16.dp)
                    .align(alignment = Alignment.BottomEnd),
                shape = CircleShape
            ) {
                Icon(Icons.Filled.Add, "Add Event")
            }
        }
    }
}
