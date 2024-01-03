package mate.pracainz.calendapp.details

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import mate.pracainz.calendapp.details.model.BasicEvent
import mate.pracainz.calendapp.details.model.EventEditorViewModel
import mate.pracainz.calendapp.details.model.ReminderEvent
import mate.pracainz.calendapp.details.model.TimerEvent
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventEditor(viewModel: EventEditorViewModel) {
    // State variables for event details
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    // Additional state for TimerEvent
    var timerRunning by remember { mutableStateOf(false) }

    // Accessing the selectedEventType from the view model
    val selectedEventType by viewModel.selectedEventType.collectAsState()

    // Fetch the selectedTabIndex from the view model
    val selectedTabIndex by viewModel.selectedTabIndex.collectAsState()

    // LaunchedEffect to observe changes in selectedTabIndex and update selectedEventType
    LaunchedEffect(selectedTabIndex) {
        viewModel.onSelectedTabIndexChange(selectedTabIndex)
    }

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
                        selected = (eventType == selectedEventType),
                        onClick = {
                            viewModel.onSelectedEventTypeChange(eventType)
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
                when (val selectedEventType = viewModel.selectedEventType.collectAsState().value) {
                    "Timer" -> {
                        // Use calendar data to determine start and end time for TimerEvent
                        val startDate = viewModel.calendarUiState.collectAsState().value.startDate.date
                        val endDate = viewModel.calendarUiState.collectAsState().value.endDate.date
                        val startTime = startDate?.atStartOfDay()
                        val endTime = endDate?.atTime(23, 59, 59)
                        Text("Start Time: $startTime")
                        Text("End Time: $endTime")
                    }

                    "Reminder" -> {
                        // Use calendar data to determine reminder time for ReminderEvent
                        val selectedDate = viewModel.selectedDate.collectAsState().value
                        val reminderTime = selectedDate.atTime(9, 0) // Replace with actual reminder time
                        Text("Reminder Time: $reminderTime")
                    }
                }
            }

            val contextForToast = LocalContext.current.applicationContext

            // FloatingActionButton to add the new event
            FloatingActionButton(
                onClick = {
                    val newEvent = when (selectedEventType) {
                        "Basic" -> BasicEvent(
                            title = title,
                            description = description,
                            dateOfExecution = viewModel.selectedDate.value,
                            isToday = viewModel.selectedDate.value == LocalDate.now()
                        )

                        "Timer" -> {
                            // Use calendar data to determine start and end time for TimerEvent
                            val startTime = viewModel.calendarUiState.value.startDate.date.atStartOfDay()
                            val endTime = viewModel.calendarUiState.value.endDate.date.atTime(23, 59, 59)
                            TimerEvent(
                                title = title,
                                description = description,
                                dateOfExecution = viewModel.selectedDate.value,
                                startTime = startTime, // Replace with actual start time
                                endTime = endTime, // Replace with actual end time
                                isToday = viewModel.selectedDate.value == LocalDate.now()
                            )
                        }

                        "Reminder" -> {
                            // Use calendar data to determine reminder time for ReminderEvent
                            val reminderTime = viewModel.selectedDate.value.atTime(9, 0) // Replace with actual reminder time
                            ReminderEvent(
                                title = title,
                                description = description,
                                dateOfExecution = viewModel.selectedDate.value,
                                reminderTime = reminderTime, // Replace with actual reminder time
                                isToday = viewModel.selectedDate.value == LocalDate.now()
                            )
                        }

                        else -> throw IllegalArgumentException("Unknown event type: $selectedEventType")
                    }

                    if (newEvent.title.isNotEmpty()) {
                        viewModel.onAddEvent(newEvent)
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