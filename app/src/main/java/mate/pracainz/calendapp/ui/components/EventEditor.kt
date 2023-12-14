
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
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
import mate.pracainz.calendapp.ui.components.BasicEvent
import mate.pracainz.calendapp.ui.components.DateTimePicker
import mate.pracainz.calendapp.ui.components.EventItem
import mate.pracainz.calendapp.ui.components.ReminderEvent
import mate.pracainz.calendapp.ui.components.TimerEvent
import mate.pracainz.calendapp.ui.layout.CalendarUiState
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

    // Additional state for TimerEvent
    var timerRunning by remember { mutableStateOf(false) }
    var startTime by remember { mutableStateOf(LocalDateTime.now()) }
    var endTime by remember { mutableStateOf(LocalDateTime.now()) }

    // Column for EventEditor
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
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
                        selected = (eventType == selectedEventType),
                        onClick = {
                            selectedEventType = eventType
                            isSelected = true
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

            Column {

                // Additional controls based on selected event type
                when (selectedEventType) {
                    "Timer" -> {
                        DateTimePicker(
                            selectedDateTime = startTime,
                            onDateTimeChange = { startTime = it },
                            label = "Start Time"
                        )
                        DateTimePicker(
                            selectedDateTime = endTime,
                            onDateTimeChange = { endTime = it },
                            label = "End Time"
                        )
                    }

                    "Reminder" -> {
                        DateTimePicker(
                            selectedDateTime = LocalDateTime.now(),
                            onDateTimeChange = { selectedDate },
                            label = "Reminder Time"
                        )
                    }
                }
            }

            val contextForToast = LocalContext.current.applicationContext

            // FloatingActionButton to add the new event
            Button(
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
                            startTime = startTime,
                            endTime = endTime,
                            isToday = selectedDate == LocalDate.now()
                        )

                        "Reminder" -> ReminderEvent(
                            title = title,
                            description = description,
                            dateOfExecution = selectedDate,
                            reminderTime = LocalDateTime.now(),
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
